package myshelfie_controller.network;

import myshelfie_controller.EventHandler;

public class NetworkServer {

    protected final EventHandler eventHandler;

    public NetworkServer(EventHandler handler) {
        eventHandler = handler;
    }
}
