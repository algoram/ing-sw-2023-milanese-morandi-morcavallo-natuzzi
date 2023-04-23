package myshelfie_network.rmi;

import myshelfie_controller.event.Event;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMINetworkInterface extends Remote {

    void dispatchEvent(Event event) throws RemoteException;

}
