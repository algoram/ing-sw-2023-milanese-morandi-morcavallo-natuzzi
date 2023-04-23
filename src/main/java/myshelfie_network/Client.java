package myshelfie_network;

import myshelfie_controller.UpdateHandler;
import myshelfie_controller.event.Event;
import myshelfie_controller.response.Response;

import java.rmi.RemoteException;

public abstract class Client {

    protected UpdateHandler updateHandler;

    public Client(UpdateHandler handler) {
        updateHandler = handler;
    }

    public abstract void dispatchEvent(Event event);

    public abstract void receiveResponse(Response response) throws RemoteException;

}
