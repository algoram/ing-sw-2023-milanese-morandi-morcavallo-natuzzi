package myshelfie_network.rmi;

import myshelfie_controller.event.Event;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

public interface RMIServerInterface extends Remote {

    void dispatchEvent(Event event) throws RemoteException;

    void setRMIClient(UUID uuid, RMIClientInterface client) throws RemoteException;

}
