package myshelfie_controller;

import myshelfie_network.rmi.RMIServer;
import myshelfie_network.socket.SocketServer;
import myshelfie_controller.response.Response;

public class UpdateDispatcher {

    private static UpdateDispatcher instance = null;

    private UpdateDispatcher() {}

    public static UpdateDispatcher getInstance() {
        if (instance == null) {
            instance = new UpdateDispatcher();
        }

        return instance;
    }

    public void dispatchResponse(Response response) {
        String player = response.getTarget();

        if (RMIServer.getInstance().hasClient(player)) {
            RMIServer.getInstance().sendResponse(response);
        } else if (SocketServer.getInstance().hasClient(player)) {
            SocketServer.getInstance().sendResponse(response);
        }
    }

}
