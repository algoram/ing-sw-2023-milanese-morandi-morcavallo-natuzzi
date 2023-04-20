package myshelfie_controller.network.rmi;

import myshelfie_controller.EventHandler;
import myshelfie_controller.network.NetworkServer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class RMIServer extends NetworkServer {

    public RMIServer(EventHandler eventHandler) {
        super(eventHandler);

        try {
            LocateRegistry.createRegistry(1099);

            RMINetworkInterfaceImpl impl = new RMINetworkInterfaceImpl(eventHandler);
            RMINetworkInterface stub = (RMINetworkInterface) UnicastRemoteObject.exportObject(impl, 0);

            Naming.rebind("MyShelfieRMI", stub);
        } catch (RemoteException e) {
            System.err.println("Couldn't create the registry.");
        } catch (MalformedURLException e) {
            System.err.println("Bad url for the stub.");
        }
    }

}
