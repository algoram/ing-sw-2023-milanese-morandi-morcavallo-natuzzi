package myshelfie_network;

import myshelfie_controller.UpdateHandler;
import myshelfie_controller.event.Event;
import myshelfie_controller.response.Response;

import java.rmi.RemoteException;

public interface Client {

    void dispatchEvent(Event event);

    void receiveResponse(Response response) throws RemoteException;

}
