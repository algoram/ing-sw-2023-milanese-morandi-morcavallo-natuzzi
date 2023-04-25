package myshelfie_controller;

import myshelfie_controller.event.*;
import myshelfie_controller.response.*;
import myshelfie_model.GameState;
import myshelfie_model.GameUpdate;
import myshelfie_model.Position;
import myshelfie_model.board.Board;
import myshelfie_model.goal.Token;
import myshelfie_model.goal.common_goal.CommonGoal;
import myshelfie_model.player.Bookshelf;
import myshelfie_model.player.Player;

import java.util.*;

public class EventHandler {

    private static EventHandler instance = null;

    private final Queue<Event> eventQueue;
    private boolean threadRun;

    Map<String,Long> lastPingTimes;


    private EventHandler() {
        eventQueue = new LinkedList<>();
        threadRun = true;
        lastPingTimes = new HashMap<>();


        // here I need a way to get from the player to the game
        // I may start this thread after first connection
        new Thread(() -> { lastPingChecker();}).start();

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
        //String game = event.getSource()[1];
        String player = event.getSource()[0];

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
                    String[] players = GameManager.getInstance().getPlayers(game);
                    for (String p : players) {
                        if (!p.equals(player)) {
                            UpdateDispatcher.getInstance().dispatchResponse(new MessageSendResponse(player, game, message, player, true));
                        }
                    }
                }
            } else {
                System.out.println("MessageSendFailure");
                UpdateDispatcher.getInstance().dispatchResponse(new MessageSendFailure(player, game, "Message is null"));
            }


        } else if (event instanceof PlayerConnect) {
            System.out.println("PlayerConnect");

            int numberOfPlayers = ((PlayerConnect) event).getNumberOfPlayers();

            if (!GameManager.getInstance().addPlayer(player, numberOfPlayers)) {

                //TODO fix messages to distinguish between different errors
                UpdateDispatcher.getInstance().dispatchResponse(new PlayerConnectFailure(player, game,"Error in connection"));

            } else {

                UpdateDispatcher.getInstance().dispatchResponse(new PlayerConnectSuccess(player, game));

                synchronized (lastPingTimes.get(player)) {
                    lastPingTimes.put(player, System.currentTimeMillis());
                }
                //if everyone is connected send the connection update to all players
                if(GameManager.getInstance().getPlayers(game).length == GameManager.getInstance().getNumberOfPlayers(game)){

                    ConnectUpdate connectUpdate = new ConnectUpdate(player,game, GameManager.getInstance().getGameState(game));

                    String[] players = GameManager.getInstance().getPlayers(game);
                    for(String p : players){
                        UpdateDispatcher.getInstance().dispatchResponse(connectUpdate);
                    }

                }
            }



        } else if (event instanceof PlayerDisconnect) {
            System.out.println("PlayerDisconnect");

            GameManager.getInstance().removePlayer(player, game);

            String[] players = GameManager.getInstance().getPlayers(game);
            for (String p : players) {
                UpdateDispatcher.getInstance().dispatchResponse(new PlayerDisconnectSuccess(p, game, player));
            }


        } else if (event instanceof Ping){
            synchronized (lastPingTimes.get(player)) {
                if (!lastPingTimes.containsKey(player)) {
                    System.out.println("PingFailure player not found: " + player);
                }
                lastPingTimes.replace(player, System.currentTimeMillis());
            }
            UpdateDispatcher.getInstance().dispatchResponse(new PingAck(player, game));

        } else if (event instanceof TakeTiles){
            System.out.println("TakeTiles");

            int column = ((TakeTiles) event).getColumn();
            List<Position> tiles = ((TakeTiles) event).getTiles();

            if (tiles==null || tiles.size()==0){
                UpdateDispatcher.getInstance().dispatchResponse(new TakeTilesFailure(player, game, "No tiles selected"));
            }  else if (column < 0 || column > 4){
                UpdateDispatcher.getInstance().dispatchResponse(new TakeTilesFailure(player, game, "Column out of bounds"));

            } else {

                if (GameManager.getInstance().takeTiles(game, player, column, tiles)) {
                    String[] players = GameManager.getInstance().getPlayers(game);

                    //Notify the success of the take tiles and update the view for all players
                    //TODO: write getgamestateupdate
                    GameUpdate gameUpdate = GameManager.getInstance().getGameUpdate(game,player);

                    UpdateDispatcher.getInstance().dispatchResponse(new TakeTilesSuccess(player, gameUpdate));

                    gameUpdate.removePersonalData(); //remove of personal data for other players update

                    for (String p : players) {
                        if (!p.equals(player)) {

                            UpdateDispatcher.getInstance().dispatchResponse(new TakeTilesUpdate(p, gameUpdate, player));
                        }
                    }

                } else {
                    UpdateDispatcher.getInstance().dispatchResponse(new TakeTilesFailure(player, game, "Error in taking tiles"));
                }
            }

        } else {
            throw new RuntimeException("Event not implemented");
        }
    }


    private void lastPingChecker() {
        while(threadRun){
            try {
                wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (String player : lastPingTimes.keySet()){
                synchronized (lastPingTimes.get(player)){
                    if (System.currentTimeMillis() - lastPingTimes.get(player) > 5000){

                        Integer game = GameManager.getInstance().lostConnection(player);
                        String[] players = GameManager.getInstance().getPlayers(game);
                        for (String p : players) {
                            UpdateDispatcher.getInstance().dispatchResponse(new PlayerDisconnectSuccess(p, player));
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
