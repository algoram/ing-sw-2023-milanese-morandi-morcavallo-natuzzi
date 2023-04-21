package myshelfie_controller.network.rmi;

import myshelfie_controller.UpdateHandler;
import myshelfie_controller.event.Event;
import myshelfie_controller.network.NetworkClient;
import myshelfie_controller.response.Response;
import myshelfie_model.Position;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class RMIClient extends NetworkClient {

    private RMINetworkInterface server;

    public RMIClient(UpdateHandler handler, String host) throws RemoteException, NotBoundException {
        super(handler);

        Registry registry = LocateRegistry.getRegistry(host);
        server = (RMINetworkInterface) registry.lookup("MyShelfieRMI");
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
    public void receiveResponse(Response response) throws RemoteException {

    }
}
