package myshelfie_controller;

import myshelfie_controller.event.*;
import myshelfie_model.Game;
import myshelfie_model.Position;
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
        Event event = new PlayerConnect("1",3);
        eventHandler.handle(event);
        startPinging("1");

        event = new PlayerConnect("2",3);
        eventHandler.handle(event);
        startPinging("2");


        event = new PlayerConnect("3",3);
        eventHandler.handle(event);
        startPinging("3");


        List<Position> chosen = new ArrayList<>();
        chosen.add(new Position(4,4));
        chosen.add(new Position(4,5));
        chosen.add(new Position(4,6));

        System.out.println("Turn: " + GameManager.getInstance().getTurn("1"));
        event = new TakeTiles(GameManager.getInstance().getTurn("1"), chosen,2);
        try{
            eventHandler.handle(event);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }


    }

    @Test
    public void handle_PlayerDisConnect() {
        Event event = new PlayerConnect("t1",3);
        eventHandler.handle(event);
        startPinging("1");

        event = new PlayerConnect("t2",3);
        eventHandler.handle(event);
        startPinging("2");

        event = new PlayerConnect("t3",3);
        eventHandler.handle(event);
        startPinging("3");

        event = new PlayerDisconnect("t3");
        eventHandler.handle(event);

        event = new PlayerConnect("t3",3);
        eventHandler.handle(event);


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