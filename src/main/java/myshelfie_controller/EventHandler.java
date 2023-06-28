package myshelfie_controller;

import myshelfie_controller.event.*;
import myshelfie_controller.response.*;
import myshelfie_model.Game;
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

    private final LinkedList<String> waitingQueue;

    private EventHandler() {
        eventQueue = new LinkedList<>();
        threadRun = true;
        lastPingTimes = new HashMap<>();
        waitingQueue = new LinkedList<>();


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
     * this function is called in the private constructor in a while(thread run).
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

                    if (to.equals(player)) { //has sent a message to himself
                        String errormessage = buildMirrorMessageReason(player,event);
                        UpdateDispatcher.getInstance().dispatchResponse(new MessageSendFailure(player, errormessage));
                        return;
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

            // tell the network stack to memorize the player's network client
            UUID uuid = event.getUuid();

            if (RMIServer.getInstance().hasTempClient(uuid)) {
                RMIServer.getInstance().addClient(uuid, player);
            } else if (SocketServer.getInstance().hasTempClient(uuid)) {
                SocketServer.getInstance().addClient(uuid, player);
            }

            boolean previouslyConnected = GameManager.getInstance().alreadyInGame(player);

            // if the player is not reconnecting to a game
            if (!previouslyConnected) {
                System.out.println("Not previously connected");
                // if there's a game available
                if (GameManager.getInstance().gameAvailable()) {
                    System.out.println("Game available");
                    try {
                        // TODO: the number of players is actually not needed here
                        GameManager.getInstance().addPlayer(player, 4);
                    } catch (Exception e) {
                        // this catch should never happen, we checked for it with the 2 IFs before
                        UpdateDispatcher.getInstance().dispatchResponse(new PlayerConnectFailure(player, e.getMessage(), event.getUuid()));
                        return;
                    }

                    UpdateDispatcher.getInstance().dispatchResponse(new PlayerConnectSuccess(player));
                    if (Settings.DEBUG) System.out.println("EventHandler -> handle(): Adding "+ player +" to lastPingTimes");
                    lastPingTimes.put(player, System.currentTimeMillis());

                    // if everyone is connected send the connection update to all players
                    if (GameManager.getInstance().getPlayers(player).size() == GameManager.getInstance().getNumberOfPlayers(player)) {
                        GameState gameState = GameManager.getInstance().getGameState(player);

                        System.out.println("EventHandler-> handle(): Sending connect update to all:");

                        List<String> players = GameManager.getInstance().getPlayers(player);
                        for(String p : players){
                            UpdateDispatcher.getInstance().dispatchResponse(new ConnectUpdate(p, gameState));
                        }
                    }
                } else {
                    System.out.println("Creating new game");
                    // else ask the player to create a game if he's the first
                    UpdateDispatcher.getInstance().dispatchResponse(new PlayerConnectSuccess(player));
                    if (Settings.DEBUG) System.out.println("EventHandler -> handle(): Adding "+ player +" to lastPingTimes");
                    lastPingTimes.put(player, System.currentTimeMillis());

                    // add the player to the waiting queue
                    waitingQueue.add(player);

                    UpdateDispatcher.getInstance().dispatchResponse(new GameCreateUpdate(player, waitingQueue.indexOf(player)));
                }
            } else { // if the player is reconnecting to a game

                // the number of players may be removed
                try {
                    GameManager.getInstance().addPlayer(player, 4);
                } catch (Exception e) {
                    if(Settings.DEBUG) {
                        System.out.println("EventHandler-> handle(): PlayerConnectFailure IMPOSSIBLE");
                    }
                        e.printStackTrace();
                }

                //notify the success of reconnection is needed to have the player start pinging
                UpdateDispatcher.getInstance().dispatchResponse(new PlayerConnectSuccess(player));
                if (Settings.DEBUG) System.out.println("EventHandler -> handle(): Adding "+ player +" to lastPingTimes");
                lastPingTimes.put(player, System.currentTimeMillis());

                System.out.println("Previously connected");
                // the player was already in a game
                if (GameManager.getInstance().getTurnMemory(player) != null) { // if the game was blocked due to a disconnection
                    System.out.println("Previous disconnection");
                    // I have to update both players of the start of the game

                    System.out.println("EventHandler ->handle ->addPlayer(): Game stopped due to disconnection, restarting it");
                    String turnMemoryOld = GameManager.getInstance().getTurnMemory(player);
                    GameManager.getInstance().recalculateTurn(player);

                    //we don't want to notify anything to the player who was of turn if he did not even know of disconnection
                    boolean playerOfTurnWasNotified;
                    playerOfTurnWasNotified = GameManager.getInstance().alreadyNotified(turnMemoryOld);

                    //let's update the player of restart
                    GameState gameState = GameManager.getInstance().getGameState(player);
                    for (String p : GameManager.getInstance().getPlayers(player)) {
                        if (!GameManager.getInstance().alreadySetLostConnection(p)) {
                            if (p.equals(player) || !p.equals(turnMemoryOld) || playerOfTurnWasNotified) {
                                System.out.println("EventHandler ->handle ->addPlayer(): Sending connect update to: " + p);
                                UpdateDispatcher.getInstance().dispatchResponse(new ConnectUpdate(p, gameState));
                            }
                        }
                    }
                    GameManager.getInstance().resetNotified(turnMemoryOld);
                } else {
                    System.out.println("No previous disconnection");
                    //the game was not blocked, so I have to update only the player who's doing to Connect
                    GameState gameState = GameManager.getInstance().getGameState(player);
                    UpdateDispatcher.getInstance().dispatchResponse(new ConnectUpdate(player, gameState));
                }
            }


            /*int numberOfPlayers = ((PlayerConnect) event).getNumberOfPlayers();

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
                            String turnMemoryOld = GameManager.getInstance().getTurnMemory(player);
                            GameManager.getInstance().recalculateTurn(player);

                            //we don't want to notify anything to the player who was of turn if he did not even know of disconnection
                            boolean playerOfTurnWasNotified;
                            playerOfTurnWasNotified = GameManager.getInstance().alreadyNotified(turnMemoryOld);

                            //let's update the player of restart
                            GameState gameState = GameManager.getInstance().getGameState(player);
                            for (String p : GameManager.getInstance().getPlayers(player)) {
                                if (!GameManager.getInstance().alreadySetLostConnection(p)) {
                                    if (p.equals(player) || !p.equals(turnMemoryOld) || playerOfTurnWasNotified) {
                                        System.out.println("EventHandler ->handle ->addPlayer(): Sending connect update to: " + p);
                                        UpdateDispatcher.getInstance().dispatchResponse(new ConnectUpdate(p, gameState));
                                    }
                                }
                            }
                            GameManager.getInstance().resetNotified(turnMemoryOld);
                        }
                        else{
                            //the game was not blocked, so I have to update only the player who's doing to Connect
                            GameState gameState = GameManager.getInstance().getGameState(player);
                            UpdateDispatcher.getInstance().dispatchResponse(new ConnectUpdate(player, gameState));
                        }
                    }
                    else{
                        GameState gameState = GameManager.getInstance().getGameState(player);

                        System.out.println("EventHandler-> handle(): Sending connect update to all:");

                        List<String> players = GameManager.getInstance().getPlayers(player);
                        for(String p : players){
                            UpdateDispatcher.getInstance().dispatchResponse(new ConnectUpdate(p, gameState));
                        }

                    }

                }


            }catch (Exception e){
                UpdateDispatcher.getInstance().dispatchResponse(new PlayerConnectFailure(player, e.getMessage(), event.getUuid()));
            }*/



        } else if (event instanceof GameCreate) {
            System.out.println("EventHandler-> handle(): GameCreate");

            int numberOfPlayers = ((GameCreate) event).getNumberOfPlayers();

            // if the player who sent the GameCreate event is not the actual first one in the queue
            if (!player.equals(waitingQueue.peek())) {
                UpdateDispatcher.getInstance().dispatchResponse(new GameCreateFailure(player, "You're not authorized to create a game"));
                return;
            }

            // try to fill the game with players in the waiting queue
            for (int i = 0; i < numberOfPlayers; i++) {
                String waitingPlayer = waitingQueue.poll();

                if (waitingPlayer == null) {
                    // not enough players, need to wait
                    return;
                }

                try {
                    // add the player to the newly created game
                    GameManager.getInstance().addPlayer(waitingPlayer, numberOfPlayers);
                    // notify the player that the game has been created
                    UpdateDispatcher.getInstance().dispatchResponse(new GameCreateSuccess(waitingPlayer));
                } catch (Exception e) {
                    // this catch should never happen, if the player was in the queue he wasn't in any prior games
                    UpdateDispatcher.getInstance().dispatchResponse(new GameCreateFailure(player, e.getMessage()));
                }
            }

            GameState gameState = GameManager.getInstance().getGameState(player);
            for (String p : GameManager.getInstance().getPlayers(player)) {
                UpdateDispatcher.getInstance().dispatchResponse(new ConnectUpdate(p, gameState));
            }

            // update all remaining players in the waiting queue on their new queue positions
            for (int i = 0; i < waitingQueue.size(); i++) {
                UpdateDispatcher.getInstance().dispatchResponse(new GameCreateUpdate(waitingQueue.get(i), i));
            }

        } else if (event instanceof PlayerDisconnect) {
            System.out.println("EventHandler-> handle(): PlayerDisconnect");

            //the function removePlayer recalculates the player turn, and
            //then it is updated in playerDisconnectSuccess
            try{
                GameManager.getInstance().removePlayer(player);
            }catch (Exception e){
                //if everyone disconnected
                System.out.println("EventHandler-> handle(): PlayerDisconnect: " + e.getMessage());
            }

            List<String> players = GameManager.getInstance().getPlayers(player);

            GameManager.getInstance().setStopConnection(player);

            for (String p : players) {
                if (!p.equals(player)) UpdateDispatcher.getInstance().dispatchResponse(new PlayerDisconnectSuccess(p, player, GameManager.getInstance().getTurn(player)));
            }


        } else if (event instanceof Ping){

            if (((Ping) event).getLastPing()) {//if it's the last ping
                System.out.println("EventHandler-> handle(): LastPing");
                GameManager.getInstance().lastPing(player);
            }

            synchronized (lastPingTimes) {
                if (!lastPingTimes.containsKey(player)) {
                    System.out.println("EventHandler-> handle-> ping PLAYER NOT FOUND: " + player);
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
                    if(!GameManager.getInstance().takeTiles(player, column, tiles)){ //try to take the tiles
                        System.out.println("EventHAndler handle() takeTiles returned false IMPOSSIBLE CASE");
                    }


                    System.out.print("EventHandler-> handle(): TakeTilesSuccess from " + player +": ");
                    for (Position p : tiles) System.out.print(" "+ p.toString() + " \n");


                    if (GameManager.getInstance().hasFinishedFirst(player)) {  //if the player has finished the game
                        System.out.println("EventHandler-> handle()->End of the game: Player " + player + " has finished the game");
                        //the player who finished first will not be able anymore to do the takeTiles
                        //so this part of the code will not be entered anymore from anyone

                        //let's see which one of the players has finished the game
                        //if he has started last the game must end for everyone

                        if (GameManager.getInstance().hasStartedLast(player)) {
                            System.out.println("EventHandler-> handle(): Player " + player + " has started last ");
                            System.out.println("So the Game finished for everyone");
                            gameFinishedUpdate(player);
                            //here we don't close the game, we do when everyone stops pinging
                        }
                        else {
                            System.out.println("EventHandler-> handle(): Player " + player + " has not started last ");
                            System.out.println("So the Game is finished just for him and the ones that already did the last turn");
                            //only the player and all the ones that already did the last
                            //turn must receive the game finished update
                            UpdateDispatcher.getInstance().dispatchResponse(new GameFinishedForYou(player));

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
                            gameFinishedUpdate(player);
                            //here we don't close the game, we do when everyone stops pinging
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

    /**
     * this function is called when a player does the takeTiles
     * it sends TakeTilesSuccess to the player who did the takeTiles
     * and TakeTilesUpdate to all the other players except the ones that lost connection
     * @param player the player who did the takeTiles
     */
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
     * this function is called when the game is finished for everyone
     * it sends GameFinished to all the players
     * @param player the player who played the last turn
     */
    private void gameFinishedUpdate(String player){
        List<String> players = GameManager.getInstance().getPlayers(player);
        for (String p : players) {
            String winner = GameManager.getInstance().getWinner(player);
            GameManager.getInstance().unSetTurn(p);//noOne has the turn anymore
            UpdateDispatcher.getInstance().dispatchResponse(new GameFinished(p, winner));
        }
    }

    private String buildMirrorMessageReason(String player, Event event){
        StringBuilder errormessage = new StringBuilder("Seems stupid to send a message to yourself... \n");

        if (GameManager.getInstance().getPlayers(player).size() == 2)
            errormessage.append("The only other player is: ");
        else errormessage.append("The other players are: ");

        for (String p : GameManager.getInstance().getPlayers(player)) {
            if (!p.equals(event.getSource())) errormessage.append(p).append(" ");
        }
        return errormessage.toString();
    }

    /**
     * this function is called in the private constructor in a while(thread run).
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

            Map<String,Long> lastPingTimesCopy;
            synchronized (lastPingTimes) {
                lastPingTimesCopy = new HashMap<>(lastPingTimes);
            }
            for (String player : lastPingTimesCopy.keySet()){

                if (System.currentTimeMillis() - lastPingTimesCopy.get(player) < 5000) continue;
                //if the lost of the connection is not New
                if(GameManager.getInstance().alreadySetLostConnection(player)) continue;

                GameManager.getInstance().setLostConnection(player); //set the lost in game

                List<String> players = GameManager.getInstance().getPlayers(player);
                for (String p : players) { //update the other players

                    String playerTurn = GameManager.getInstance().getTurn(player);
                    //should not notify the player on Turn to avoid event traffic after TakeTiles
                    if (!p.equals(player)) { //if (!p.equals(player) && !p.equals(playerTurn)) {

                        if (Settings.DEBUG)
                            System.out.println("EventHandler->lastPingChecker(): PlayerDisconnectSuccess of player " + player + " for "+ p);

                        UpdateDispatcher.getInstance().dispatchResponse(new PlayerDisconnectSuccess(p,
                                player, playerTurn));
                    }
                }
            }
        }
    }


    /**
     * this function is called when a game ends.
     * It removes the players from the lastPingTimes
     * @param players the players of the game
     */
    public void closeGame(List<String> players){
        for (String p : players){
            synchronized (lastPingTimes) {
                lastPingTimes.remove(p);
            }
        }
    }

    public void closeHandler() {
        threadRun = false;
    }

}
