package myshelfie_controller;

import myshelfie_controller.event.*;
import myshelfie_controller.response.*;
import myshelfie_model.GameState;
import myshelfie_model.GameUpdate;
import myshelfie_model.Position;
import myshelfie_network.rmi.RMIServer;

import java.util.*;

public class EventHandler {

    private static EventHandler instance = null;

    private final Queue<Event> eventQueue;
    private boolean threadRun;

    private final Map<String,Long> lastPingTimes;


    private EventHandler() {
        eventQueue = new LinkedList<>();
        threadRun = true;
        lastPingTimes = new HashMap<>();


        // here I need a way to get from the player to the game
        // I may start this thread after first connection
        new Thread(this::lastPingChecker).start();

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
        System.out.printf("Event from %s: ", event.getSource());
        String player = event.getSource();

        if (event instanceof MessageSend) {
            if (((MessageSend) event).getMessage() != null) {
                System.out.println("MessageSend");

                //Notify the success of the message send
                UpdateDispatcher.getInstance().dispatchResponse(new MessageSendSuccess(player));

                String message = ((MessageSend) event).getMessage();
                String to = ((MessageSend) event).getRecipient();

                if (to != null) {
                    //if to is not null send the message to the recipient
                    UpdateDispatcher.getInstance().dispatchResponse(new MessageSendResponse(player, message, player, false));
                } else {
                    List<String> players = GameManager.getInstance().getPlayers(player);
                    for (String p : players) {
                        if (!p.equals(player)) {
                            UpdateDispatcher.getInstance().dispatchResponse(new MessageSendResponse(player, message, player, true));
                        }
                    }
                }
            } else {
                System.out.println("MessageSendFailure");
                UpdateDispatcher.getInstance().dispatchResponse(new MessageSendFailure(player, "Message is null"));
            }


        } else if (event instanceof PlayerConnect) {
            System.out.println("PlayerConnect");

            int numberOfPlayers = ((PlayerConnect) event).getNumberOfPlayers();

            if (!GameManager.getInstance().addPlayer(player, numberOfPlayers)) {

                //TODO fix messages to distinguish between different errors
                UpdateDispatcher.getInstance().dispatchResponse(new PlayerConnectFailure(player,"Player name not available"));

            } else {

                // tell the network stack to memorize the player's client
                UUID uuid = event.getUuid();

                if ( RMIServer.getInstance().hasTempClient(uuid) ) {
                    RMIServer.getInstance().addClient(uuid, player);
                }

                UpdateDispatcher.getInstance().dispatchResponse(new PlayerConnectSuccess(player));

                synchronized (lastPingTimes) {
                    lastPingTimes.put(player, System.currentTimeMillis());
                }
                //if everyone is connected send the connection update to all players
                if(GameManager.getInstance().getPlayers(player).size() == GameManager.getInstance().getNumberOfPlayers(player)){


                    GameState gameState = GameManager.getInstance().getGameState(player);

                    List<String> players = GameManager.getInstance().getPlayers(player);
                    for(String p : players){
                        UpdateDispatcher.getInstance().dispatchResponse(new ConnectUpdate(p, gameState));
                    }

                }
            }



        } else if (event instanceof PlayerDisconnect) {
            System.out.println("PlayerDisconnect");

            GameManager.getInstance().removePlayer(player);

            List<String> players = GameManager.getInstance().getPlayers(player);
            for (String p : players) {
                UpdateDispatcher.getInstance().dispatchResponse(new PlayerDisconnectSuccess(p, player, GameManager.getInstance().getTurn(player)));
            }


        } else if (event instanceof Ping){
            synchronized (lastPingTimes) {
                if (!lastPingTimes.containsKey(player)) {
                    System.out.println("PingFailure player not found: " + player);
                }
                lastPingTimes.replace(player, System.currentTimeMillis());
            }
            UpdateDispatcher.getInstance().dispatchResponse(new PingAck(player));

        } else if (event instanceof TakeTiles){
            System.out.println("TakeTiles");

            int column = ((TakeTiles) event).getColumn();
            List<Position> tiles = ((TakeTiles) event).getTiles();

            if (tiles==null || tiles.size()==0){
                UpdateDispatcher.getInstance().dispatchResponse(new TakeTilesFailure(player, "No tiles selected"));
            }  else if (column < 0 || column > 4){
                UpdateDispatcher.getInstance().dispatchResponse(new TakeTilesFailure(player, "Column out of bounds"));

            } else {

                if (GameManager.getInstance().takeTiles(player, column, tiles)) {
                    List<String> players = GameManager.getInstance().getPlayers(player);

                    //Notify the success of the take tiles and update the view for all players
                    GameUpdate gameUpdate = GameManager.getInstance().getGameUpdate(player);

                    UpdateDispatcher.getInstance().dispatchResponse(new TakeTilesSuccess(player, gameUpdate));

                    gameUpdate.removePersonalData(); //remove of personal data for other players update

                    for (String p : players) {
                        if (!p.equals(player)) {

                            UpdateDispatcher.getInstance().dispatchResponse(new TakeTilesUpdate(p, gameUpdate, player));
                        }
                    }

                } else {
                    UpdateDispatcher.getInstance().dispatchResponse(new TakeTilesFailure(player, "Error in taking tiles"));
                }
            }

        } else {
            throw new RuntimeException("Event not implemented");
        }
    }


    private void lastPingChecker() {
        while(threadRun){
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (String player : lastPingTimes.keySet()){
                synchronized (lastPingTimes){
                    if (System.currentTimeMillis() - lastPingTimes.get(player) > 5000){

                        GameManager.getInstance().lostConnection(player);

                        List<String> players = GameManager.getInstance().getPlayers(player);
                        for (String p : players) {
                            UpdateDispatcher.getInstance().dispatchResponse(new PlayerDisconnectSuccess(p,
                                                                                                        player,
                                                                                                        GameManager.getInstance().getTurn(player)));
                        }
                    }
                }
            }
        }
    }

    public void closeHandler() {
        threadRun = false;
    }


}
