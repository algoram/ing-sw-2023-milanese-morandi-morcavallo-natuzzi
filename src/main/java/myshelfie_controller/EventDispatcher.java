package myshelfie_controller;

import myshelfie_controller.event.Event;
import myshelfie_controller.event.MessageSend;
import myshelfie_controller.event.Ping;
import myshelfie_controller.event.PlayerConnect;
import myshelfie_network.Client;
import myshelfie_network.rmi.RMIClient;
import myshelfie_network.socket.SocketClient;

import java.util.Random;
import java.util.UUID;

public class EventDispatcher {

    private static EventDispatcher instance = null;

    private String player;
    private ConnectionType connectionType;
    private UUID uuid = UUID.randomUUID();
    private boolean pingThreadRun;

    private EventDispatcher() {}

    public static EventDispatcher getInstance() {
        if (instance == null) {
            instance = new EventDispatcher();
        }

        return instance;
    }

    public void setPlayerCredentials(String player) {
        this.player = player;
    }

    public void setConnectionType(ConnectionType type) {
        connectionType = type;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void chat(String to, String message) {
        sendEvent(new MessageSend(
                player,
                to,
                message
        ));
    }

    public void connect(int backupNumPlayers) {
        sendEvent(new PlayerConnect(player, backupNumPlayers));
    }

    private void sendEvent(Event e) {
        e.setUuid(uuid);

        if (connectionType == ConnectionType.RMI) {
            RMIClient.getInstance().dispatchEvent(e);
        } else if (connectionType == ConnectionType.SOCKET) {
            SocketClient.getInstance().dispatchEvent(e);
        }
    }

    public void startPinging() {
        pingThreadRun = true;

        new Thread(() -> {
            while (pingThreadRun) {
                sendEvent(new Ping(player));

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    pingThreadRun = false;
                }
            }
        }).start();
    }

    public void stopPinging() {
        pingThreadRun = false;
    }

}
