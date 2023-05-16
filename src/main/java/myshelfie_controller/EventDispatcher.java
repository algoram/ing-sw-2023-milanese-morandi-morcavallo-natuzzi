package myshelfie_controller;

import myshelfie_controller.event.*;
import myshelfie_model.Position;
import myshelfie_network.Client;
import myshelfie_network.rmi.RMIClient;
import myshelfie_network.socket.SocketClient;

import java.util.List;
import java.util.UUID;

public class EventDispatcher {

    private static EventDispatcher instance = null;

    private UUID uuid = UUID.randomUUID();
    private boolean pingThreadRun;

    private EventDispatcher() {}

    public static EventDispatcher getInstance() {
        if (instance == null) {
            instance = new EventDispatcher();
        }

        return instance;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void startPinging() {
        pingThreadRun = true;

        new Thread(() -> {
            while (pingThreadRun) {
                sendEvent(new Ping(Settings.getInstance().getUsername()));

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    if (Settings.DEBUG) System.err.println("EventDispatcher ERROR - Thread interrupted while sleeping");
                    pingThreadRun = false;
                }
            }
        }).start();
    }

    public void stopPinging() {
        pingThreadRun = false;
    }

    private void sendEvent(Event e) {
        e.setUuid(uuid);

        if (Settings.getInstance().getConnectionType() == ConnectionType.RMI) {
            RMIClient.getInstance().dispatchEvent(e);
        } else if (Settings.getInstance().getConnectionType() == ConnectionType.SOCKET) {
            SocketClient.getInstance().dispatchEvent(e);
        }
    }

    public void chat(String to, String message) {
        sendEvent(new MessageSend(
                Settings.getInstance().getUsername(),
                to,
                message
        ));
    }

    public void connect(int backupNumPlayers) {
        sendEvent(new PlayerConnect(Settings.getInstance().getUsername(), backupNumPlayers));
    }

    public void playerDisconnect(){
        Event e = new PlayerDisconnect(Settings.getInstance().getUsername());
        sendEvent(e);
    }

    public void takeTiles(List<Position> tiles, int column){
        Event e = new TakeTiles(Settings.getInstance().getUsername(), tiles,column-1); //column-1 because the column start from 0
        sendEvent(e);
    }

}
