package myshelfie_controller;

import myshelfie_controller.event.Event;
import myshelfie_controller.event.MessageSend;
import myshelfie_network.Client;
import myshelfie_network.rmi.RMIClient;
import myshelfie_network.socket.SocketClient;

import java.util.Random;
import java.util.UUID;

public class EventDispatcher {

    private static EventDispatcher instance = null;

    private String player;
    private String game;
    private ConnectionType connectionType;

    private EventDispatcher() {}

    public static EventDispatcher getInstance() {
        if (instance == null) {
            instance = new EventDispatcher();
        }

        return instance;
    }

    public void setPlayerCredentials(String game, String player) {
        this.game = game;
        this.player = player;
    }

    public void setConnectionType(ConnectionType type) {
        connectionType = type;
    }

    public void chat(String to, String message) {
        sendEvent(new MessageSend(
                player,
                to,
                message
        ));
    }

    private void sendEvent(Event e) {
        if (connectionType == ConnectionType.RMI) {
            RMIClient.getInstance().dispatchEvent(e);
        } else if (connectionType == ConnectionType.SOCKET) {
            SocketClient.getInstance().dispatchEvent(e);
        }
    }

}
