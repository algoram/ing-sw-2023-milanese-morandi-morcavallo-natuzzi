package myshelfie_controller;

import myshelfie_controller.event.MessageSend;
import myshelfie_network.Client;

public class EventDispatcher {

    private final Client client; // network client can be RMI or socket
    private final String player;
    private final String game;

    public EventDispatcher(Client client, String gameName, String username) {
        this.client = client;
        player = username;
        game = gameName;
    }

    public void chat(String to, String message) {
        client.dispatchEvent(new MessageSend(
                player,
                game,
                to,
                message
        ));
    }

}
