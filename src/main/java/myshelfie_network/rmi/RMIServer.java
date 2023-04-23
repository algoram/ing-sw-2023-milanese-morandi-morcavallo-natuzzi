package myshelfie_network.rmi;

import myshelfie_controller.ClientID;
import myshelfie_controller.EventHandler;
import myshelfie_controller.GameManager;
import myshelfie_controller.event.Event;
import myshelfie_network.Server;
import myshelfie_controller.response.Response;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class RMIServer extends Server implements RMINetworkInterface {

    private HashMap<ClientID, RMIClient> clients;

    public RMIServer(EventHandler eventHandler, GameManager gameManager) {
        super(eventHandler, gameManager);

        try {
            LocateRegistry.createRegistry(1099);
            RMINetworkInterface stub = (RMINetworkInterface) UnicastRemoteObject.exportObject(this, 0);
            Naming.rebind("MyShelfieRMI", stub);
        } catch (RemoteException e) {
            System.err.println("Couldn't create the registry.");
        } catch (MalformedURLException e) {
            System.err.println("Bad url for the stub.");
        }
    }

    @Override
    public boolean hasClient(String game, String player) {
        return clients.containsKey(new ClientID(game, player));
    }

    @Override
    public void sendResponse(Response response) {
        String player = response.getTarget()[0];
        String game = response.getTarget()[1];

        RMIClient client = clients.get(new ClientID(game, player));

        int tries = 3;

        while (tries > 0) {
            try {
                client.receiveResponse(response);

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
        System.out.println("Event added to the queue");
        eventHandler.addToEventQueue(event);
    }
}
