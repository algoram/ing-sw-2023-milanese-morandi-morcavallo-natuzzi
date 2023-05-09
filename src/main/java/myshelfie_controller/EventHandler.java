package myshelfie_controller;

import myshelfie_controller.event.*;
import myshelfie_controller.response.*;
import myshelfie_model.GameState;
import myshelfie_model.Position;
import myshelfie_network.rmi.RMIServer;
import myshelfie_network.socket.SocketServer;

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
                    //todo check new thread creation
                    new Thread(() -> handle(event)).start();
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

    /**
     * this function is called in the private constructor in a while(threadrun).
     * @param event is the event crated by the client that needs to be handled
     */
    public void handle(Event event) {
        if (!(event instanceof Ping)) {
            System.out.printf("EventHandler-> handle(): Event from %s: \n", event.getSource());
        }
        String player = event.getSource();

        if (event instanceof MessageSend) {
            if (((MessageSend) event).getMessage() != null) {

                String message = ((MessageSend) event).getMessage();
                String to = ((MessageSend) event).getRecipient();

                Settings.getInstance();
                if(Settings.DEBUG) {System.out.println("EventHandler-> handle(): MessageSend");}

                if (to != null) {
                    if(!GameManager.getInstance().getPlayers(player).contains(to)) {
                        StringBuilder errorMessage = new StringBuilder("there is no player with the name " + to + "\nThe available players are: ");
                        for(String p : GameManager.getInstance().getPlayers(player)) {
                            if (!p.equals(event.getSource())) errorMessage.append(p).append(" ");
                        }
                        UpdateDispatcher.getInstance().dispatchResponse(new MessageSendFailure(player, errorMessage.toString()));
                        return;
                    }
                }

                //Notify the success of the message send
                UpdateDispatcher.getInstance().dispatchResponse(new MessageSendSuccess(player));



                if (to != null) {
                    //if to is not null send the message to the recipient
                    UpdateDispatcher.getInstance().dispatchResponse(new MessageSendResponse(to, message, player, false));
                } else {
                    List<String> players = GameManager.getInstance().getPlayers(player);
                    for (String p : players) {
                        if (!p.equals(player)) {
                            UpdateDispatcher.getInstance().dispatchResponse(new MessageSendResponse(p, message, player, true));
                        }
                    }
                }
            } else {
                System.out.println("EventHandler-> handle(): MessageSendFailure");
                UpdateDispatcher.getInstance().dispatchResponse(new MessageSendFailure(player, "EventHandler-> handle(): Message is null"));
            }


        } else if (event instanceof PlayerConnect) {
            System.out.println("EventHandler -> handle():PlayerConnect");

            int numberOfPlayers = ((PlayerConnect) event).getNumberOfPlayers();

            if (!GameManager.getInstance().addPlayer(player, numberOfPlayers)) {

                //TODO fix messages to distinguish between different errors
                UpdateDispatcher.getInstance().dispatchResponse(new PlayerConnectFailure(player,"EventHandler-> handle(): Player name not available"));

            } else {

                // tell the network stack to memorize the player's client
                UUID uuid = event.getUuid();

                if ( RMIServer.getInstance().hasTempClient(uuid) ) {
                    RMIServer.getInstance().addClient(uuid, player);
                } else if (SocketServer.getInstance().hasTempClient(uuid)) {
                    SocketServer.getInstance().addClient(uuid, player);
                }

                UpdateDispatcher.getInstance().dispatchResponse(new PlayerConnectSuccess(player));

                synchronized (lastPingTimes) {
                    lastPingTimes.put(player, System.currentTimeMillis());
                }
                //if everyone is connected send the connection update to all players
                if(GameManager.getInstance().getPlayers(player).size() == GameManager.getInstance().getNumberOfPlayers(player)){


                    GameState gameState = GameManager.getInstance().getGameState(player);
                    String nextPlayer = gameState.getPlayerTurn();
                    System.out.println("EventHandler-> handle(): The next Player is: : " + nextPlayer);


                    List<String> players = GameManager.getInstance().getPlayers(player);
                    for(String p : players){
                        UpdateDispatcher.getInstance().dispatchResponse(new ConnectUpdate(p, gameState));
                    }

                }
            }



        } else if (event instanceof PlayerDisconnect) {
            System.out.println("EventHandler-> handle(): PlayerDisconnect");

            GameManager.getInstance().removePlayer(player);

            List<String> players = GameManager.getInstance().getPlayers(player);
            for (String p : players) {
                UpdateDispatcher.getInstance().dispatchResponse(new PlayerDisconnectSuccess(p, player, GameManager.getInstance().getTurn(player)));
            }


        } else if (event instanceof Ping){
            synchronized (lastPingTimes) {
                if (!lastPingTimes.containsKey(player)) {
                    //System.out.println("PingFailure player not found: " + player);
                }
                lastPingTimes.replace(player, System.currentTimeMillis());
            }
            UpdateDispatcher.getInstance().dispatchResponse(new PingAck(player));

        } else if (event instanceof TakeTiles){
            System.out.println("EventHandler-> handle(): TakeTiles");

            int column = ((TakeTiles) event).getColumn();
            List<Position> tiles = ((TakeTiles) event).getTiles();

            if (tiles==null || tiles.size()==0){
                UpdateDispatcher.getInstance().dispatchResponse(new TakeTilesFailure(player, "No tiles selected"));
            }  else if (column < 0 || column > 4){
                UpdateDispatcher.getInstance().dispatchResponse(new TakeTilesFailure(player, "Column out of bounds"));

            } else {
                try{
                    if(!GameManager.getInstance().takeTiles(player, column, tiles)){
                        System.out.println("EventHAndler handle() takeTiles returned false IMPOSSIBLE CASE");
                    }
                    List<String> players = GameManager.getInstance().getPlayers(player);

                    System.out.print("EventHandler-> handle(): TakeTilesSuccess from " + player +": ");
                    for (Position p : tiles) {
                        System.out.print(" "+ p.toString() + " ");
                    }
                    System.out.println();

                    //Notify the success of the take tiles and update the view for all players
                    GameState gameState = GameManager.getInstance().getGameState(player);
                    System.out.println("EventHandler-> handle(): GameState has been updated" + gameState.toString());

                    UpdateDispatcher.getInstance().dispatchResponse(new TakeTilesSuccess(player, gameState));

                    for (String p : players) {
                        if (!p.equals(player)) {
                            UpdateDispatcher.getInstance().dispatchResponse(new TakeTilesUpdate(p, gameState, player));
                        }
                    }

                    //if the player has finished the game
                    if (GameManager.getInstance().hasFinishedFirst(player)) {

                        //the player who finished first will not be able anymore to do the takeTiles
                        //so this part of the code will not be entered anymore from anyone

                        //let's see which one of the players has finished the game
                        //if he has started last the game must end for everyone
                        if (GameManager.getInstance().hasStartedLast(player)) {
                            for (String p : players) {
                                String winner = GameManager.getInstance().getWinner(player);
                                UpdateDispatcher.getInstance().dispatchResponse(new GameFinished(p, winner));
                            }
                            GameManager.getInstance().closeGame(player);
                        }
                        else {
                            //only the player and all the ones that already did the last
                            //turn must receive the game finished update
                            UpdateDispatcher.getInstance().dispatchResponse(new GameFinishedForYou(player));

                            //the players that didn't do the last turn must do it
                            //todo we could implement a kind of message here -> "wait for the other players to finish"
                        }
                    }
                    //here is the case where the player has finished the game, but he is not the first
                    else if(GameManager.getInstance().someoneElseFinished(player)){

                        //if someone else has finished the game
                        //the player who finished did his last take tiles so:

                        if (GameManager.getInstance().someoneStillHasToPlay(player)){ //if someone still has to play
                            UpdateDispatcher.getInstance().dispatchResponse(new GameFinishedForYou(player));
                        }
                        else { //if everyone has finished
                            for (String p : players) {
                                String winner = GameManager.getInstance().getWinner(player);
                                UpdateDispatcher.getInstance().dispatchResponse(new GameFinished(p, winner));
                            }
                            GameManager.getInstance().closeGame(player);
                        }
                    }

                } catch (Exception e){
                    UpdateDispatcher.getInstance().dispatchResponse(new TakeTilesFailure(player, e.getMessage()));
                    e.printStackTrace();
                }
            }

        } else {
            throw new RuntimeException("EventHandler-> handle():  Event not implemented");
        }
    }

    /**
     * this function is called in the private constructor in a while(threadrun).
     * it checks if the last ping time of a player is greater than 5 seconds
     * and if it is it disconnects the player
     * so that the others can continue playing
     */
    private void lastPingChecker() {
        while(threadRun){
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Map<String,Long> lastPingTimescopy;
            synchronized (lastPingTimes) {
                lastPingTimescopy = new HashMap<>(lastPingTimes);
            }
            for (String player : lastPingTimes.keySet()){

                if (System.currentTimeMillis() - lastPingTimescopy.get(player) > 10000){

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

    public void closeHandler() {
        threadRun = false;
    }

}
