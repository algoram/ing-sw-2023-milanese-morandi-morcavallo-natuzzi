package myshelfie_controller.network.rmi;

import myshelfie_model.Position;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class RMIClient {

    private final RMINetworkInterface server;
    private final String gameName;
    private final String username;

    public RMIClient(String host, String gameName, String username) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(host);

        server = (RMINetworkInterface) registry.lookup("MyShelfieRMI");

        server.connect(gameName, username);

        this.gameName = gameName;
        this.username = username;
    }

    public void chat(String to, String message) throws RemoteException {
        server.chat(gameName, username, to, message);
    }

    public boolean take(int column, ArrayList<Position> positions) throws RemoteException {
        return server.take(gameName, username, column, positions);
    }

    public void close() throws RemoteException {
        server.disconnect(gameName, username);
    }

}
