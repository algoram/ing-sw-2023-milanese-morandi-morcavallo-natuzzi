package myshelfie_network;

import myshelfie_controller.EventHandler;
import myshelfie_controller.GameManager;
import myshelfie_controller.response.Response;

public interface Server {

    boolean hasClient(String player);

    void sendResponse(Response response);
}
