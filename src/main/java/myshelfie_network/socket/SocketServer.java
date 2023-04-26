package myshelfie_network.socket;

import myshelfie_controller.EventHandler;
import myshelfie_controller.event.Event;
import myshelfie_network.Server;
import myshelfie_controller.response.Response;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SocketServer implements Server {

    private static SocketServer instance = null;

    private ServerSocket serverSocket;
    private int port;
    private List<ClientHandler> clients = new ArrayList<>();


    private SocketServer() {}

    public static SocketServer getInstance() {
        if (instance == null) {
            instance = new SocketServer();
        }

        return instance;
    }

    public void start(int port) throws IOException {
        this.port = port;

        serverSocket = new ServerSocket(port);
        while (true) {
            try {
                // Accept client connections
                Socket clientSocket = serverSocket.accept();
                ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                outputStream.flush();
                ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
                String username = (String) inputStream.readObject();
                System.out.println("New client connected: " + clientSocket.getInetAddress().getHostName() + " username: " + username );

                // Create new thread to handle the client
                ClientHandler clientHandler = new ClientHandler(clientSocket, username);
                clientHandler.setStreams(outputStream, inputStream);
                clients.add(clientHandler);
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void stop() throws IOException {
        serverSocket.close();

        // Chiude tutti i client handler
        for (ClientHandler client : clients) {
            client.stop();
        }
    }
    /***
     * Inner class implementing the Runnable interface.
     * Each ClientHandler manages a separate client and
     * handles reading data from the client via the ObjectInputStream
     * */
    private class ClientHandler implements Runnable {
        private Socket clientSocket;
        private String player;

        private ObjectOutputStream outputStream;
        private ObjectInputStream inputStream;

        public ClientHandler(Socket clientSocket, String player) {
            this.clientSocket = clientSocket;
            this.player = player;
        }

        public void setStreams(ObjectOutputStream o, ObjectInputStream i) {
            outputStream = o;
            inputStream = i;
        }

        public void run() {
            try {
                while (true) {
                    // Read data from client
                    Object data = inputStream.readObject();
                    System.out.println("Dati ricevuti dal client: " + data);

                    // Manage data
                    handleData(data);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    inputStream.close();
                    outputStream.close();
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        /***
         * shut down the server and all client handlers
         * */
        public void stop() throws IOException {
            inputStream.close();
            outputStream.close();
            clientSocket.close();
        }
        /***
         * called whenever the ClientHandler receives data from the client
         * */
        private void handleData(Object data) {
            if(data instanceof Event) {
                Event event = (Event) data;
                EventHandler.getInstance().addToEventQueue(event);
            } else throw new RuntimeException("Invalid data type from client");
        }
    }

    @Override
    public boolean hasClient(String player) {
        for (ClientHandler client : clients) {
            if (client.player.equals(player)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void sendResponse(Response response) {
        for (ClientHandler client : clients) {
            if (client.player.equals(response.getTarget())) {
                try {
                    client.outputStream.writeObject(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
