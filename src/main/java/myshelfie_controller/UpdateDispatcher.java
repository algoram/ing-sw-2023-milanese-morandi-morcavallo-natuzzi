package myshelfie_controller;

import myshelfie_network.rmi.RMIServer;
import myshelfie_network.socket.SocketServer;
import myshelfie_controller.response.Response;

public class UpdateDispatcher {

    private RMIServer rmiServer;
    private SocketServer socketServer;

    public void dispatchResponse(Response response) {
        String player = response.getTarget()[0];
        String game = response.getTarget()[1];

        if (rmiServer.hasClient(game, player)) {
            rmiServer.sendResponse(response);
        } else if (socketServer.hasClient(game, player)) {
            socketServer.sendResponse(response);
        }
    }

}
