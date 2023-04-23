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


public class SocketClient extends Client {
    private String host;
    private int port;
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;


    public SocketClient(UpdateHandler handler, String host, int port) {
        super(handler);
        this.host = host;
        this.port = port;
    }
    /***
     * Connect client to the server and create the input/output streams.
     * A separate thread (`ResponseReader`) is also created to read responses from the server.
     *
     * */
    public void start() throws IOException {
        socket = new Socket(host, port);
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());

        // Start a thread to read server response
        Thread responseThread = new Thread(new receiveResponse());
        responseThread.start();
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


    /***
     * `receiveResponse` thread reads the responses from the server via the `ObjectInputStream` and
     *  calls the `handleResponse()` method to handle them.
     *
     * */
    @Override
    private class receiveResponse implements Runnable {
        public void run() {
            while (true) {
                try {
                    Response response = (Response) inputStream.readObject();
                    handleResponse(response);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
        private void handleResponse(Object data) {

            //TODO: implementare la gestione dei dati (eventi) ricevuti dal server
        }

    }
}



