package myshelfie_network.rmi;

import myshelfie_controller.EventHandler;
import myshelfie_controller.event.Event;
import myshelfie_controller.event.PlayerConnect;
import myshelfie_network.Server;
import myshelfie_controller.response.Response;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.UUID;

public class RMIServer implements Server, RMIServerInterface {

    private HashMap<String, RMIClientInterface> clients = new HashMap<>();
    private HashMap<UUID, RMIClientInterface> tempClients = new HashMap<>();

    private static RMIServer instance = null;

    private RMIServer() {}

    public static RMIServer getInstance() {
        if (instance == null) {
            instance = new RMIServer();
        }

        return instance;
    }

    public void start(int port) {
        try {
            LocateRegistry.createRegistry(port);
            RMIServerInterface stub = (RMIServerInterface) UnicastRemoteObject.exportObject(this, 0);
            Naming.rebind("MyShelfieRMI", stub);
        } catch (RemoteException e) {
            System.err.println("Couldn't create the registry.");
            System.err.println(e);
        } catch (MalformedURLException e) {
            System.err.println("Bad url for the stub.");
        }
    }

    @Override
    public boolean hasClient(String player) {
        return clients.containsKey(player);
    }

    public boolean hasTempClient(UUID uuid) {
        return tempClients.containsKey(uuid);
    }

    public void addClient(UUID uuid, String username) {
        clients.put(username, tempClients.get(uuid));

        tempClients.remove(uuid);
    }

    @Override
    public void sendResponse(Response response) {
        String player = response.getTarget();

        RMIClientInterface client = clients.get(player);

        int tries = 3;

        while (tries > 0) {
            try {
                client.dispatchResponse(response);

                break;
            } catch (RemoteException e) {
                tries--;
            }
        }

        if (tries == 0) {
            // TODO: connection error
        }
    }

    @Override
    public void dispatchEvent(Event event) throws RemoteException {
        EventHandler.getInstance().addToEventQueue(event);
    }

    @Override
    public void setRMIClient(UUID uuid, RMIClientInterface client) throws RemoteException {
        tempClients.put(uuid, client);
    }
}
