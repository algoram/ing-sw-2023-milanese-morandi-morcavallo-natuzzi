package myshelfie_controller.network.rmi;

import myshelfie_model.Position;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RMINetworkInterface extends Remote {

    public void connect(String gameName, String username) throws RemoteException;

    public void disconnect(String gameName, String username) throws RemoteException;

    public boolean take(String gameName, String username, int column, ArrayList<Position> positions) throws RemoteException;

    public void chat(String gameName, String username, String to, String message) throws RemoteException;

}
