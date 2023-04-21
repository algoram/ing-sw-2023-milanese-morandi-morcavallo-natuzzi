package myshelfie_controller.network.socket;

import myshelfie_controller.UpdateHandler;
import myshelfie_controller.event.Event;
import myshelfie_controller.network.NetworkClient;
import myshelfie_controller.response.Response;

import java.rmi.RemoteException;

public class SocketClient extends NetworkClient {
    public SocketClient(UpdateHandler handler) {
        super(handler);
    }

    @Override
    public void dispatchEvent(Event event) {

    }

    @Override
    public void receiveResponse(Response response) throws RemoteException {

    }
}
