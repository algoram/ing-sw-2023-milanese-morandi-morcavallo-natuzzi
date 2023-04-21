package myshelfie_controller.network;

import myshelfie_controller.EventHandler;
import myshelfie_controller.GameManager;
import myshelfie_controller.response.Response;

public abstract class NetworkServer {

    protected final EventHandler eventHandler;
    protected final GameManager gameManager;

    public NetworkServer(EventHandler handler, GameManager manager) {
        eventHandler = handler;
        gameManager = manager;

    }

    public abstract boolean hasClient(String game, String player);

    public abstract void sendResponse(Response response);
}
