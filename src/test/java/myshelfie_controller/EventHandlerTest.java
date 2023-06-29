package myshelfie_controller;

import com.sun.glass.ui.Window;
import myshelfie_controller.event.*;
import myshelfie_model.Game;
import myshelfie_model.Position;
import myshelfie_network.Client;
import myshelfie_network.socket.SocketClient;
import myshelfie_network.socket.SocketServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class EventHandlerTest {
    GameManager gameManager = null;
    UpdateHandler updateHandler = null;

    EventHandler eventHandler = null;
    Boolean pingThreadRun = true;

    @Before
    public void setUp() {
        updateHandler = UpdateHandler.getInstance();
        eventHandler = EventHandler.getInstance();
    }

    @After
    public void tearDown() {
        pingThreadRun = false;
    }

    @Test
    public void handle() {

        new Thread(() -> {
            startServerConnection();
        }).start();

        startClient("t1");
        EventHandler.getInstance().handle(new PlayerConnect("t1"));
        EventDispatcher.getInstance().createGame(2);

        startClient("t2");
        EventHandler.getInstance().handle(new PlayerConnect("t2"));


        List<Position> chosen = new ArrayList<>();
        chosen.add(new Position(4, 4));
        chosen.add(new Position(4, 5));
        EventHandler.getInstance().handle(new TakeTiles("t1", chosen, 3));

    }


    private void startClient(String player) {

        new Thread(() -> {
            startClientConnection();
        }).start();

        Settings.getInstance().setUsername("player");
        Settings.getInstance().setConnectionType(ConnectionType.SOCKET);
        EventDispatcher.getInstance().connect();
        startPinging(player);
    }


    private void startServerConnection() {
        try {
            SocketServer.getInstance().start(19736);
        } catch (Exception e) {
            System.out.println("SocketServer error");
        }
    }

    private void startClientConnection(){
        try{
            SocketClient.getInstance().start("localhost", 19736);
        }catch (Exception e){
            System.out.println("SocketClient error");
        }
    }

    private void startPinging(String player) {

        new Thread(() -> {
            while (pingThreadRun) {
                EventHandler.getInstance().addToEventQueue(new Ping(player, false));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    if (Settings.DEBUG) System.err.println("EventDispatcher ERROR - Thread interrupted while sleeping");
                    pingThreadRun = false;
                }
            }
        }).start();
    }
}
