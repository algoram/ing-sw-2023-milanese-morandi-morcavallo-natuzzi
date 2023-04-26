package myshelfie_network.rmi;

import myshelfie_controller.response.Response;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIClientInterface extends Remote {

    void dispatchResponse(Response response) throws RemoteException;

}
