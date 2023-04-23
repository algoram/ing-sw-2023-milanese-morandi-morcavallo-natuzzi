package myshelfie_controller;

import myshelfie_controller.event.*;
import myshelfie_controller.response.PlayerConnectFailure;
import myshelfie_controller.response.PlayerConnectSuccess;

import java.util.LinkedList;
import java.util.Queue;

public class EventHandler {

    private final Queue<Event> eventQueue;
    private final UpdateDispatcher updateDispatcher;
    private final GameManager gameManager;
    private boolean threadRun;

    public EventHandler(UpdateDispatcher dispatcher,GameManager gameManager) {
        updateDispatcher = dispatcher;
        this.gameManager = gameManager;

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
            if (!gameManager.addGame(game, player, numberOfPlayers)) {
                updateDispatcher.dispatchResponse(new PlayerConnectFailure(player, game));
            } else {
                updateDispatcher.dispatchResponse(new PlayerConnectSuccess(player, game));
            }


        } else if (event instanceof PlayerDisconnect) {
            System.out.println("PlayerDisconnect");

            gameManager.removePlayer(player, game);

            String[] players = gameManager.getPlayers(game);
            for (String p : players) {
                updateDispatcher.dispatchResponse(new PlayerDisconnect(p, game, player));
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
