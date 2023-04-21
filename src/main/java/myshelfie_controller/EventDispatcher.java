package myshelfie_controller;

import myshelfie_controller.event.MessageSend;
import myshelfie_controller.network.NetworkClient;

public class EventDispatcher {

    private final NetworkClient networkClient; // network client can be RMI or socket
    private final String player;
    private final String game;

    public EventDispatcher(NetworkClient client, String gameName, String username) {
        networkClient = client;
        player = username;
        game = gameName;
    }

    public void chat(String to, String message) {
        networkClient.dispatchEvent(new MessageSend(
                player,
                game,
                to,
                message
        ));
    }

}
