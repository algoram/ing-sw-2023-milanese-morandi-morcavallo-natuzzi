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
                    //todo change this it could cause problems. otherwise synchronize is required
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
                if (Settings.DEBUG) {
                    System.out.println("EventHandler-> handle(): MessageSend");
                }

                if (to != null) {

                    //if to is not null check if the recipient is in the game
                    if (!GameManager.getInstance().getPlayers(player).contains(to)) {
                        //Notify the failure of the message send
                        StringBuilder errorMessage = new StringBuilder("there is no player with the name " + to + "\nThe available players are: ");
                        for (String p : GameManager.getInstance().getPlayers(player)) {
                            if (!p.equals(event.getSource())) errorMessage.append(p).append(" ");
                        }
                        UpdateDispatcher.getInstance().dispatchResponse(new MessageSendFailure(player, errorMessage.toString()));
                        return;
                    }

                    if (to.equals(player)) {
                        StringBuilder errormessage = new StringBuilder("Seems stupid to send a message to yourself... \n");

                        if (GameManager.getInstance().getPlayers(player).size() == 2)
                            errormessage.append("The only other player is: ");
                        else errormessage.append("The other players are: ");

                        for (String p : GameManager.getInstance().getPlayers(player)) {
                            if (!p.equals(event.getSource())) errormessage.append(p).append(" ");
                        }
                        UpdateDispatcher.getInstance().dispatchResponse(new MessageSendFailure(player, errormessage.toString()));

                    }
                    if(GameManager.getInstance().alreadySetLostConnection(to)){
                        UpdateDispatcher.getInstance().dispatchResponse(new MessageSendFailure(player,"The player " + to + " has lost the connection"));
                    }

                    else UpdateDispatcher.getInstance().dispatchResponse(new MessageSendResponse(to, message, player, false));

                } else {
                    List<String> players = GameManager.getInstance().getPlayers(player);
                    for (String p : players) {
                        if (!p.equals(player) && !GameManager.getInstance().alreadySetLostConnection(p)) {
                            UpdateDispatcher.getInstance().dispatchResponse(new MessageSendResponse(p, message, player, true));

                        }
                    }
                }
            }
                //Notify the success of the message send
                UpdateDispatcher.getInstance().dispatchResponse(new MessageSendSuccess(player));

        } else if (event instanceof PlayerConnect) {
            System.out.println("EventHandler -> handle():PlayerConnect");

            int numberOfPlayers = ((PlayerConnect) event).getNumberOfPlayers();

            try {
                boolean isPlayerNew = GameManager.getInstance().addPlayer(player, numberOfPlayers);

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

                    if (!isPlayerNew){ //if the player who's doing the Connect was already playing

                        if (GameManager.getInstance().getTurnMemory(player) != null) { //if the game was blocked due to a disconnection

                            //I have to update both players of the start of the game

                            System.out.println("EventHandler ->handle ->addPlayer(): Game stopped due to disconnection, restarting it");
                            //String turnMemoryOld = GameManager.getInstance().getTurnMemory(player);
                            GameManager.getInstance().recalculateTurn(player);

                            //let's update the player of restart
                            GameState gameState = GameManager.getInstance().getGameState(player);
                            for (String p : GameManager.getInstance().getPlayers(player)) {
                                if (!GameManager.getInstance().alreadySetLostConnection(p)) {
                                    System.out.println("EventHandler ->handle ->addPlayer(): Sending connect update to: " + p);
                                    UpdateDispatcher.getInstance().dispatchResponse(new ConnectUpdate(p, gameState));
                                }
                            }
                        }
                        else{
                            //the game was not blocked, so I have to update only the player who's doing the Connect
                            GameState gameState = GameManager.getInstance().getGameState(player);
                            UpdateDispatcher.getInstance().dispatchResponse(new ConnectUpdate(player, gameState));
                        }
                    }
                    else{
                        GameState gameState = GameManager.getInstance().getGameState(player);
                        String nextPlayer = gameState.getPlayerTurn();
                        System.out.println("EventHandler-> handle(): Sending connect update to all:");

                        List<String> players = GameManager.getInstance().getPlayers(player);
                        for(String p : players){
                            UpdateDispatcher.getInstance().dispatchResponse(new ConnectUpdate(p, gameState));
                        }

                    }

                }


            }catch (Exception e){
                UpdateDispatcher.getInstance().dispatchResponse(new PlayerConnectFailure(player, e.getMessage(), event.getUuid()));
            }



        } else if (event instanceof PlayerDisconnect) {
            System.out.println("EventHandler-> handle(): PlayerDisconnect");

            //the function removePlayer recalculates the player turn and
            //then it is updated in playerDisconnectSuccess
            try{
                GameManager.getInstance().removePlayer(player);
            }catch (Exception e){
                //if everyone disconnected
                if(e.getMessage().equals("All players lost connection")){
                    GameManager.getInstance().closeGame(player);
                }
            }

            List<String> players = GameManager.getInstance().getPlayers(player);

            //todo here we have to recalculate turn and send update

            for (String p : players) {
                if (!p.equals(player)) UpdateDispatcher.getInstance().dispatchResponse(new PlayerDisconnectSuccess(p, player, GameManager.getInstance().getTurn(player)));
            }


        } else if (event instanceof Ping){
            //todo the ping arrive after setLostDisconnection
            synchronized (lastPingTimes) {
                if (!lastPingTimes.containsKey(player)) {
                    System.out.println("PingFailure player not found: " + player);
                } else {

                    if (GameManager.getInstance().alreadySetLostConnection(player)) {//if the player was disconnected
                        try{
                            GameManager.getInstance().addPlayer(player,2); //the number doesn't count
                        }catch (Exception e){
                            if(Settings.DEBUG) System.out.println("EventHandler -> Ping: error in adding player after Ping");
                        }
                    }
                    lastPingTimes.replace(player, System.currentTimeMillis());
                }
            }
            if (GameManager.getInstance().isConnected(player)) UpdateDispatcher.getInstance().dispatchResponse(new PingAck(player));

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



                    //if the player has finished the game
                    if (GameManager.getInstance().hasFinishedFirst(player)) {
                        System.out.println("EventHandler-> handle()->End of the game: Player " + player + " has finished the game");
                        //the player who finished first will not be able anymore to do the takeTiles
                        //so this part of the code will not be entered anymore from anyone

                        //let's see which one of the players has finished the game
                        //if he has started last the game must end for everyone

                        if (GameManager.getInstance().hasStartedLast(player)) {
                            System.out.println("EventHandler-> handle(): Player " + player + " has started last ");
                            System.out.println("So the Game finished for everyone");
                            for (String p : players) {
                                String winner = GameManager.getInstance().getWinner(player);
                                GameManager.getInstance().unSetTurn(p);//noOne has the turn anymore
                                UpdateDispatcher.getInstance().dispatchResponse(new GameFinished(p, winner));
                            }
                            GameManager.getInstance().closeGame(player);
                        }
                        else {
                            System.out.println("EventHandler-> handle(): Player " + player + " has not started last ");
                            System.out.println("So the Game is finished just for him and the ones that already did the last turn");
                            //only the player and all the ones that already did the last
                            //turn must receive the game finished update
                            UpdateDispatcher.getInstance().dispatchResponse(new GameFinishedForYou(player));

                            //todo we could implement a way to send gamefinished for you to the ones that did the last turn
                        }
                    }
                    //here is the case where the player has finished the game, but he is not the first
                    else if(GameManager.getInstance().someoneElseFinished(player)){
                        System.out.println("EventHandler-> handle()->Someone else finished the game");
                        //if someone else has finished the game
                        //the player who finished did his last take tiles so:

                        if (GameManager.getInstance().someoneStillHasToPlay(player)){ //if someone still has to play
                            System.out.println("EventHandler-> handle()->But someone still has to play");
                            UpdateDispatcher.getInstance().dispatchResponse(new GameFinishedForYou(player));
                        }
                        else { //if everyone has finished
                            System.out.println("EventHandler-> handle()->And everyone has finished");
                            for (String p : players) {
                                String winner = GameManager.getInstance().getWinner(player);
                                GameManager.getInstance().unSetTurn(p);//noOne has the turn anymore
                                UpdateDispatcher.getInstance().dispatchResponse(new GameFinished(p, winner));
                            }
                            GameManager.getInstance().closeGame(player);
                        }
                    }

                    updatePlayers(player); //update with take tiles Success and Update


                } catch (Exception e){
                    UpdateDispatcher.getInstance().dispatchResponse(new TakeTilesFailure(player, e.getMessage()));
                }
            }

        } else {
            if (Settings.DEBUG) System.err.println("EventHandler ERROR - Handle not implemented for this event");
            //throw new RuntimeException("EventHandler-> handle():  Event not implemented");
        }
    }

    private void updatePlayers(String player){
        GameState gameState = GameManager.getInstance().getGameState(player);

        UpdateDispatcher.getInstance().dispatchResponse(new TakeTilesSuccess(player, gameState));

        for (String p : GameManager.getInstance().getPlayers(player)) {
            //send the update to all the players except the one who did the takeTiles
            // and the ones that lost connection
            if (!p.equals(player) && !GameManager.getInstance().alreadySetLostConnection(p)) {
                UpdateDispatcher.getInstance().dispatchResponse(new TakeTilesUpdate(p, gameState, player));
            }
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
                if (Settings.DEBUG) System.err.println("EventHandler ERROR - Thread interrupted while sleeping");
            }

            Map<String,Long> lastPingTimescopy;
            synchronized (lastPingTimes) {
                lastPingTimescopy = new HashMap<>(lastPingTimes);
            }
            for (String player : lastPingTimes.keySet()){

                if (System.currentTimeMillis() - lastPingTimescopy.get(player) > 5000){

                    //if the lost of the connection is New
                    if(!GameManager.getInstance().alreadySetLostConnection(player)) {

                        GameManager.getInstance().setLostConnection(player); //set the lost in game

                        List<String> players = GameManager.getInstance().getPlayers(player);
                        for (String p : players) { //update the other players
                            if (!p.equals(player)) {

                                if (Settings.DEBUG)
                                    System.out.println("EventHandler->lastPingChecker(): PlayerDisconnectSuccess of player " + player + " for "+ p);


                                UpdateDispatcher.getInstance().dispatchResponse(new PlayerDisconnectSuccess(p,
                                        player, GameManager.getInstance().getTurn(player)));
                            }
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
