package myshelfie_network.rmi;

import myshelfie_controller.EventDispatcher;
import myshelfie_controller.UpdateHandler;
import myshelfie_controller.event.Event;
import myshelfie_network.Client;
import myshelfie_controller.response.Response;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;

public class RMIClient extends UnicastRemoteObject implements Client, RMIClientInterface {

    private static RMIClient instance = null;

    private RMIServerInterface server;

    private RMIClient() throws RemoteException {}

    public static RMIClient getInstance() {
        if (instance == null) {
            try {
                instance = new RMIClient();
            } catch (RemoteException e) {
                // something went horribly wrong
                throw new RuntimeException(e);
            }
        }

        return instance;
    }

    public void connect(String host) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(host);
        server = (RMIServerInterface) registry.lookup("MyShelfieRMI");

        server.setRMIClient(EventDispatcher.getInstance().getUuid(), this);
    }

    @Override
    public void dispatchEvent(Event event) {
        try {
            server.dispatchEvent(event);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dispatchResponse(Response response) throws RemoteException {
        UpdateHandler.getInstance().handle(response);
    }

    @Override
    public void closeConnection() throws RemoteException {
        server = null;
    }
}
