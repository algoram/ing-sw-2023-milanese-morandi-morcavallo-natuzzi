package myshelfie_controller.network.rmi;

import myshelfie_controller.ServerController;
import myshelfie_model.Position;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class RMINetworkInterfaceImpl implements RMINetworkInterface {

    private final ServerController serverController;

    public RMINetworkInterfaceImpl(ServerController serverController) {
        this.serverController = serverController;
    }

    @Override
    public void connect(String gameName, String username) throws RemoteException {
        System.out.println(String.format("Player %s connected to game %s", username, gameName));
    }

    @Override
    public void disconnect(String gameName, String username) throws RemoteException {
        System.out.println(String.format("Player %s disconnected from game %s", username, gameName));
    }

    @Override
    public boolean take(String gameName, String username, int column, ArrayList<Position> positions) throws RemoteException {
        System.out.println(String.format("Player %s (game %s) tried to take %d tiles", username, gameName, positions.size()));

        return false;
    }

    @Override
    public void chat(String gameName, String username, String to, String message) throws RemoteException {
        System.out.println(String.format("Player %s (game %s) sent a message", username, gameName));
        System.out.println(message);
    }
}
