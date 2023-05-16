package myshelfie_controller;

import myshelfie_controller.response.PingAck;
import myshelfie_controller.response.PlayerConnectFailure;
import myshelfie_network.rmi.RMIServer;
import myshelfie_network.socket.SocketServer;
import myshelfie_controller.response.Response;

import java.net.SocketException;
import java.util.UUID;

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

        if (Settings.DEBUG && !(response instanceof PingAck)) {
            System.out.println("UpdateDispatcher -> dispatchResponse(): Sending response " + response.getClass().getSimpleName());
            System.out.println("UpdateDispatcher -> dispatchResponse(): Sending response to " + player);
        }

        if (response instanceof PlayerConnectFailure failure) {
            UUID uuid = failure.getTargetUUID();

            if (RMIServer.getInstance().hasTempClient(uuid)) {
                RMIServer.getInstance().sendResponse(failure);
            } else if (SocketServer.getInstance().hasTempClient(uuid)) {
                SocketServer.getInstance().sendResponse(failure);
            } else {
                System.out.println("UpdateDispatcher -> dispatchResponse(): Did not send");
            }

            return;
        }


        if (RMIServer.getInstance().hasClient(player)) {
            //System.out.println("Sending via RMI");
            RMIServer.getInstance().sendResponse(response);
        } else if (SocketServer.getInstance().hasClient(player)) {
            //System.out.println("Sending via socket");
            SocketServer.getInstance().sendResponse(response);
        } else {
            System.out.println("UpdateDispatcher -> dispatchResponse(): Did not send");
        }
    }

}
