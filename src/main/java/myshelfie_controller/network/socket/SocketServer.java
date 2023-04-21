package myshelfie_controller.network.socket;

import myshelfie_controller.EventHandler;
import myshelfie_controller.GameManager;
import myshelfie_controller.network.NetworkServer;
import myshelfie_controller.response.Response;

public class SocketServer extends NetworkServer {
    public SocketServer(EventHandler handler, GameManager manager) {
        super(handler, manager);
    }

    @Override
    public boolean hasClient(String game, String player) {
        return false;
    }

    @Override
    public void sendResponse(Response response) {

    }
}
