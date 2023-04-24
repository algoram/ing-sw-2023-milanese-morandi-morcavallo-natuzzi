package myshelfie_network.socket;
import myshelfie_controller.UpdateHandler;
import myshelfie_controller.event.Event;
import myshelfie_network.Client;
import myshelfie_controller.response.Response;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;


public class SocketClient implements Client {

    private static SocketClient instance = null;

    private String host;
    private int port;
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;


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
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());
    }

    /***
     * close resources when the client is no longer needed.
     *
     */
    public void stop() throws IOException {
        inputStream.close();
        outputStream.close();
        socket.close();
    }


    /***
     * send events to the server via the `ObjectOutputStream`
     *
     * */
    @Override
    public void dispatchEvent(Event event) {
        try {
            outputStream.writeObject(event);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receiveResponse(Response response) throws RemoteException {
        UpdateHandler.getInstance().handle(response);
    }
}



