package myshelfie_controller.network.rmi;

import myshelfie_controller.event.Event;
import myshelfie_model.Position;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RMINetworkInterface extends Remote {

    void dispatchEvent(Event event) throws RemoteException;

    void setClient(RMIClient client) throws RemoteException;

}
