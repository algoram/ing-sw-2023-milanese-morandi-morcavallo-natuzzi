package myshelfie_network.socket;

import myshelfie_controller.EventHandler;
import myshelfie_controller.Settings;
import myshelfie_controller.event.Event;
import myshelfie_controller.response.PlayerConnectFailure;
import myshelfie_network.Server;
import myshelfie_controller.response.Response;

import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.HashMap;
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

            System.out.println("SocketServer->start(): Client connected " + clientSocket.getInetAddress());

            // Create new thread to handle the client
            new Thread(() -> {
                try {
                    ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                    outputStream.flush();
                    ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());

                    // TODO: create a flag for the thread to run
                    while (true) {
                        Object data = null;
                        try{
                            data = inputStream.readObject();
                        }catch (EOFException e){
                            if(Settings.DEBUG)System.out.println("SocketServer -> EOF exception");
                        }

                        if (data instanceof UUID uuid) {
                            System.out.println(uuid);
                            tempClients.put(uuid, new ClientStruct(clientSocket, outputStream, inputStream));
                        } else if (data instanceof Event event) {
                            EventHandler.getInstance().addToEventQueue(event);
                        } else {
                            throw new RuntimeException("SocketServer->start(): Invalid data type from client");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (Exception e){
                    if(Settings.DEBUG)System.out.println("SocketServer -> start exception generic");
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
        ClientStruct client = null;

        if (response instanceof PlayerConnectFailure failure) {
            UUID uuid = failure.getTargetUUID();

            client = tempClients.get(uuid);
        } else {
            String player = response.getTarget();

            client = clients.get(player);
        }

        int tries = 3;

        while (tries > 0) {
            try {
                client.out.reset();
                client.out.writeObject(response);
                break;
            } catch (IOException e) {
                e.printStackTrace();
                tries--;
            } catch (Exception e){
                if(Settings.DEBUG) System.out.println("SocketServer -> sendResponse generic exception thrown");
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

    @Override
    public void removeClient(String player) {
        try {
            clients.get(player).socket.close();
        } catch (IOException e) {
            if (Settings.DEBUG) System.err.println("SocketServer ERROR - Couldn't close the connection");
        }
        clients.remove(player);
    }
}
