package myshelfie_controller.network.rmi;

import myshelfie_controller.ClientController;
import myshelfie_controller.network.NetworkClient;
import myshelfie_model.Position;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class RMIClient extends NetworkClient {

    private RMINetworkInterface server = null;
    private String gameName;
    private String username;

    public RMIClient(ClientController controller) {
        super(controller);
    }

    public void connect(String host) throws RemoteException, NotBoundException {
        if (server != null) {
            server.disconnect(gameName, username);
        }
        Registry registry = LocateRegistry.getRegistry(host);
        server = (RMINetworkInterface) registry.lookup("MyShelfieRMI");
    }

    public void join(String gameName, String username) throws RemoteException {
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
