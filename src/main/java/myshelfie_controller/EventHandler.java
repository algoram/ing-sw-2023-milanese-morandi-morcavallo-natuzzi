package myshelfie_controller;

import myshelfie_controller.event.*;
import myshelfie_controller.response.PlayerConnectFailure;
import myshelfie_controller.response.PlayerConnectSuccess;
import myshelfie_controller.response.PlayerDisconnectSuccess;

import java.util.LinkedList;
import java.util.Queue;

public class EventHandler {

    private final Queue<Event> eventQueue;
    private boolean threadRun;

    private static EventHandler instance = null;

    private EventHandler() {
        eventQueue = new LinkedList<>();
        threadRun = true;

        new Thread(() -> {
            while (threadRun) {
                Event event;

                synchronized (eventQueue) {
                    event = eventQueue.poll();
                }

                if (event != null) {
                    handle(event);
                }
            }
        }).start();
    }

    public static EventHandler getInstance() {
        if (instance == null) {
            instance = new EventHandler();
        }

        return instance;
    }

    public void addToEventQueue(Event event) {
        synchronized (eventQueue) {
            eventQueue.add(event);
        }
    }

    public void handle(Event event) {
        System.out.printf("Event from %s/%s: ", event.getSource()[0], event.getSource()[1]);
        String game = event.getSource()[1];
        String player = event.getSource()[0];

        if (event instanceof MessageSend) {
            //TODO implement message send
            System.out.println("MessageSent");
            if (((MessageSend) event).getRecipient() != null) {
                System.out.printf("\tto: %s\n", ((MessageSend) event).getRecipient());
            }
            System.out.printf("\tmessage: %s\n", ((MessageSend) event).getMessage());


        } else if (event instanceof PlayerConnect) {
            System.out.println("PlayerConnect");
            int numberOfPlayers = ((PlayerConnect) event).getNumberOfPlayers();
            if (!GameManager.getInstance().addGame(game, player, numberOfPlayers)) {
                UpdateDispatcher.getInstance().dispatchResponse(new PlayerConnectFailure(player, game, "Couldn't add player to the game"));
            } else {
                UpdateDispatcher.getInstance().dispatchResponse(new PlayerConnectSuccess(player, game));
            }


        } else if (event instanceof PlayerDisconnect) {
            System.out.println("PlayerDisconnect");

            GameManager.getInstance().removePlayer(player, game);

            String[] players = GameManager.getInstance().getPlayers(game);
            for (String p : players) {
                UpdateDispatcher.getInstance().dispatchResponse(new PlayerDisconnectSuccess(p, game));
            }


        } else if (event instanceof Ping){
            //TODO implement ping


        } else if (event instanceof TakeTiles){
            //TODO implement take tiles

        } else {
            throw new RuntimeException("Event not implemented");
        }
    }

    public void closeHandler() {
        threadRun = false;
    }

}
