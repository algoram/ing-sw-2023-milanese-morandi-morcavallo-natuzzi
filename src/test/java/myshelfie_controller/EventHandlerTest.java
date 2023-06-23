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

    Game game = null;
    @Before
    public void setUp() {
        updateHandler = UpdateHandler.getInstance();
        eventHandler = EventHandler.getInstance();
    }

    @After
    public void tearDown() {

    }

    @Test
    public void handle() {
        Event event = new PlayerConnect("1",2);
        eventHandler.handle(event);

        event = new PlayerConnect("2",2);
        eventHandler.handle(event);

        event = new PlayerConnect("3",2);
        eventHandler.handle(event);


        event = new PlayerDisconnect("3");
        eventHandler.handle(event);

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
    public void handle_PlayerConnect() {



    }

    @Test
    public void handle_PlayerDisconnect() {
        // TODO
    }

    @Test
    public void handle_TakeTiles() {
        // TODO
    }
}
