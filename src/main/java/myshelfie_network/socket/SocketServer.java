package myshelfie_network.socket;

import myshelfie_controller.EventHandler;
import myshelfie_controller.GameManager;
import myshelfie_network.Server;
import myshelfie_controller.response.Response;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SocketServer extends Server{

    private ServerSocket serverSocket;
    private int port;
    private List<ClientHandler> clients = new ArrayList<>();


    public SocketServer(EventHandler handler, GameManager manager, int port)  throws IOException {
        super(handler, manager);
        this.port = port;
    }

    public void start() throws IOException {
        serverSocket = new ServerSocket(port);
        while (true) {
            try {
                // Accetta le connessioni dei client
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nuovo client connesso: " + clientSocket.getInetAddress().getHostName());

                // Crea un nuovo thread per gestire il client
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clients.add(clientHandler);
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();

            } catch (IOException e) {
                e.printStackTrace();
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
        private ObjectOutputStream outputStream;
        private ObjectInputStream inputStream;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        public void run() {
            try {
                outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                inputStream = new ObjectInputStream(clientSocket.getInputStream());

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

            //TODO: implementare la gestione dei dati (eventi) ricevuti dal client
        }
    }

    @Override
    public boolean hasClient(String game, String player) {
        if(clients.size()>0) {
            //TODO: implementare hasClient
        }
        return false;
    }

    @Override
    public void sendResponse(Response response) {
            //TODO: implementare sendResponse
    }


}
