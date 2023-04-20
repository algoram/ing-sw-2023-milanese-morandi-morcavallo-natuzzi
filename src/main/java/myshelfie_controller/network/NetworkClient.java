package myshelfie_controller.network;

import myshelfie_controller.UpdateHandler;
import myshelfie_controller.event.Event;

public abstract class NetworkClient {

    protected UpdateHandler updateHandler;

    public NetworkClient(UpdateHandler handler) {
        updateHandler = handler;
    }

    public abstract void dispatchEvent(Event event);

}
