package myshelfie_network;

import myshelfie_controller.EventHandler;
import myshelfie_controller.GameManager;
import myshelfie_controller.response.Response;

public abstract class Server {

    protected final EventHandler eventHandler;
    protected final GameManager gameManager;

    public Server(EventHandler handler, GameManager manager) {
        eventHandler = handler;
        gameManager = manager;

    }

    public abstract boolean hasClient(String game, String player);

    public abstract void sendResponse(Response response);
}
