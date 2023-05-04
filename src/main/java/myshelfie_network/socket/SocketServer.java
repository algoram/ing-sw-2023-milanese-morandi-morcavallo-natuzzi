package myshelfie_network.socket;

import myshelfie_controller.EventHandler;
import myshelfie_controller.event.Event;
import myshelfie_network.Server;
import myshelfie_controller.response.Response;
import myshelfie_network.rmi.RMIClientInterface;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class SocketServer implements Server {

    private static SocketServer instance = null;

    private ServerSocket serverSocket;
    private int port;

    private HashMap<String, ClientStruct> clients = new HashMap<>();
    private HashMap<UUID, ClientStruct> tempClients = new HashMap<>();

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
            // Accept client connections
            Socket clientSocket = serverSocket.accept();

            System.out.println("Client connected " + clientSocket.getInetAddress());

            // Create new thread to handle the client
            new Thread(() -> {
                try {
                    ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                    outputStream.flush();
                    ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());

                    // TODO: create a flag for the thread to run
                    while (true) {
                        Object data = inputStream.readObject();

                        if (data instanceof UUID uuid) {
                            System.out.println(uuid);
                            tempClients.put(uuid, new ClientStruct(clientSocket, outputStream, inputStream));
                        } else if (data instanceof Event event) {
                            EventHandler.getInstance().addToEventQueue(event);
                        } else {
                            throw new RuntimeException("Invalid data type from client");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }

    }

    public void stop() throws IOException {
        serverSocket.close();

        for (ClientStruct c : clients.values()) {
            c.socket.close();
        }

        for (ClientStruct c : tempClients.values()) {
            c.socket.close();
        }
    }

    private class ClientStruct {
        private Socket socket;
        private ObjectOutputStream out;
        private ObjectInputStream in;

        public ClientStruct(Socket s, ObjectOutputStream o, ObjectInputStream i) {
            socket = s;
            out = o;
            in = i;
        }
    }

    @Override
    public boolean hasClient(String player) {
        return clients.containsKey(player);
    }

    @Override
    public void sendResponse(Response response) {
        String player = response.getTarget();

        ClientStruct client = clients.get(player);

        int tries = 3;

        while (tries > 0) {
            try {
                client.out.writeObject(response);
                break;
            } catch (IOException e) {
                e.printStackTrace();
                tries--;
            }
        }

        if (tries == 0) {
            // TODO: connection error
        }
    }

    @Override
    public boolean hasTempClient(UUID uuid) {
        return tempClients.containsKey(uuid);
    }

    @Override
    public void addClient(UUID uuid, String player) {
        clients.put(player, tempClients.get(uuid));

        tempClients.remove(uuid);
    }
}
