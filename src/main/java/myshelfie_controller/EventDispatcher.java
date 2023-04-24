package myshelfie_controller;

import myshelfie_controller.event.Event;
import myshelfie_controller.event.MessageSend;
import myshelfie_network.Client;
import myshelfie_network.rmi.RMIClient;

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
        Event e = new MessageSend(
                player,
                game,
                to,
                message
        );

        if (connectionType == ConnectionType.RMI) {
            RMIClient.getInstance().dispatchEvent(e);
        } else if (connectionType == ConnectionType.SOCKET) {

        }
    }

}
