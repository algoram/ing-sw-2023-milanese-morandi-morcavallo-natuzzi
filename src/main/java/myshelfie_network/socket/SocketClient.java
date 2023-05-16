package myshelfie_network.socket;
import myshelfie_controller.EventDispatcher;
import myshelfie_controller.Settings;
import myshelfie_controller.UpdateHandler;
import myshelfie_controller.event.Event;
import myshelfie_network.Client;
import myshelfie_controller.response.Response;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;


public class SocketClient implements Client {

    private static SocketClient instance = null;

    private String host;
    private String username;
    private int port;
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private boolean threadRun;


    private SocketClient() {}

    public static SocketClient getInstance() {
        if (instance == null) {
            instance = new SocketClient();
        }

        return instance;
    }

    /***
     * Connect client to the server and create the input/output streams.
     * A separate thread (`ResponseReader`) is also created to read responses from the server.
     *
     * */
    public void start(String host, int port) throws IOException {
        this.host = host;
        this.port = port;

        socket = new Socket(host, port);
        if (Settings.DEBUG) System.out.println("SocketClient-> start(): Connected to " + host + ":" + port);
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream.flush();
        try {
            outputStream.writeUnshared(EventDispatcher.getInstance().getUuid());
            outputStream.flush();
        } catch (IOException e) {
            if (Settings.DEBUG) System.err.println("SocketClient ERROR - Failed to send UUID");
        }
        inputStream = new ObjectInputStream(socket.getInputStream());

        threadRun = true;

        new Thread(() -> {
            while (threadRun) {
                try {
                    Response response = (Response) inputStream.readObject();
                    UpdateHandler.getInstance().handle(response);
                } catch (IOException | ClassNotFoundException e) {
                    if (Settings.DEBUG) System.err.println("SocketClient ERROR - Couldn't handle response");
                }
            }
        }).start();

    }


    /***
     * close resources when the client is no longer needed.
     *
     */
    public void stop() throws IOException {
        inputStream.close();
        outputStream.close();
        threadRun=false;
        socket.close();
    }


    /***
     * send events to the server via the `ObjectOutputStream`
     *
     * */
    @Override
    public void dispatchEvent(Event event) {
        try {
            outputStream.reset();
            outputStream.writeObject(event);
            outputStream.flush();
        } catch (IOException e) {
            if (Settings.DEBUG) System.err.println("SocketClient ERROR - Couldn't dispatch event");
        }
    }
}



