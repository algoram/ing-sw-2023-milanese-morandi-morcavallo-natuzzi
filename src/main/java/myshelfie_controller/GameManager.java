package myshelfie_controller;
import myshelfie_controller.response.GameFinished;
import myshelfie_model.Game;
import myshelfie_model.GameState;
import myshelfie_model.Position;
import myshelfie_model.Tile;
import myshelfie_model.board.Board;
import myshelfie_model.goal.Token;
import myshelfie_model.goal.common_goal.CommonGoal;

import myshelfie_model.player.Player;
import myshelfie_network.rmi.RMIServer;
import myshelfie_network.socket.SocketServer;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class GameManager {

    private final ArrayList<Game> games = new ArrayList<>();
    private final HashMap<String, Integer> playerToGame = new HashMap<>();

    private final HashMap<Integer, Integer> gameToLastPing = new HashMap<>(); //count the number of players that have pinged for the last time

    private ArrayList TakeStoppedReceived = new ArrayList();
    private static GameManager instance = null;

    private GameManager() {
        // TODO: check for backups
        //games.addAll(BackupManager.getInstance().getBackup());
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }

        return instance;
    }

    public void removePlayer(String player) throws Exception {
        games.get(playerToGame.get(player)).removePlayer(player);
    }

    public List<String> getPlayers(String player) {
        return games.get(playerToGame.get(player)).getPlayersUsernames();
    }

    /**
     * Returns whether the player is already connected to a game
     * @param player to check
     * @return whether the player is already connected to a game
     */
    public boolean alreadyInGame(String player) {
        return playerToGame.containsKey(player);
    }

    /**
     * Checks if there's a game not full still
     * @return whether there's a game not full still
     */
    public boolean gameAvailable() {
        for (Game g : games) {
            if (g.getPlayers().size() < g.getNumberOfPlayers()) {
                return true;
            }
        }

        return false;
    }


    /**
     * This function is calls from event handler when a player connects to a game.
     * the function checks if there is a player with the same username
     * the server should not allow that 2 different players have the same username
     * it would mean that the player had lost connection
     * <p>
     * otherwise it checks if there is a game with a free spot
     * if there is it adds the player to the game
     * otherwise it creates a new game and adds the player to it
     *
     * @param newPlayer the player to add
     * @param players   the number of players in the game
     * @return true if the player was new, false if he was already in a game
     */
    public boolean addPlayer(String newPlayer, int players) throws Exception {

        //if he wasn't connected and just one player was or (turn was unset recalculate turn)
        //we could need something like backup turn to unset turn when everyone disconnected
        //without losing memory of the turn
        if (playerToGame.containsKey(newPlayer)) { // if the player is already in a game

            int numGame = playerToGame.get(newPlayer);
            int numPlayerInGame = games.get(numGame).findPlayer(newPlayer);

            Game.StateConnection state = games.get(numGame).getPlayerStates().get(numPlayerInGame);

            if (Settings.DEBUG) System.out.println("GameManager->addPlayer(): Player had state " + state + " in game ");

            if(state.equals(Game.StateConnection.DISCONNECTED)) {
                throw new Exception("Player already disconnected voluntarily");
            }else if(state.equals(Game.StateConnection.CONNECTED)) {
                throw new Exception("Player already connected in a game");
            }else{
                games.get(numGame).addPlayer(newPlayer);
            }
            return false;
        }


        //check if there is a game missing some players otherwise create a new one
        for (int i = 0; i < games.size(); i++) {

            if (games.get(i).getPlayers().size() < games.get(i).getNumberOfPlayers()) { // if the game is not full

                if (games.get(i).addPlayer(newPlayer)) {
                    playerToGame.put(newPlayer, i); // save which game the player is playing
                    return true;
                }

            }
        }

        Game game = new Game(); //otherwise create a new game
        game.startGame(players); // start it with the number of players specified

        if (game.addPlayer(newPlayer)) {
            games.add(game);
            playerToGame.put(newPlayer, games.size() - 1); // save which game the player is playing
        }
        return true;
    }


    /**
     * This function is called when a take tiles comes from a player
     * @param player the player that did the take tiles
     * @return true if the take tiles was successful
     * @throws Exception if the game is paused due to disconnection of all others players
     *                  or if the take tiles is not valid for any other reason (see game.takeTiles())
     */
    public boolean takeTiles(String player, int column, List<Position> tiles) throws Exception {
        int numGame = playerToGame.get(player);
        if (games.get(numGame).getTurn() == null) {
            TakeStoppedReceived.add(player);//the controller need to know if the player has noticed that the game was "stopped"
            throw new Exception("The game is Paused due to disconnection of another player");
        }
        return games.get(playerToGame.get(player)).takeTiles(player, tiles, column);
    }

    public boolean alreadyNotified(String player){
        return TakeStoppedReceived.contains(player);
    }

    public void resetNotified(String player){
        TakeStoppedReceived.remove(player);
    }

    public int getNumberOfPlayers(String player) {
        return games.get(playerToGame.get(player)).getNumberOfPlayers();
    }

    public String getTurnMemory(String player) {
        return games.get(playerToGame.get(player)).getTurnMemory();
    }


    /**
     * this function is called to know the state of the player
     * @param player the player to check
     * @return true if the player is connected, false otherwise
     */
    public boolean isConnected(String player){
        if (playerToGame.containsKey(player)) {
            int numGame = playerToGame.get(player);
            int numPlayer = games.get(numGame).findPlayer(player);
            return games.get(numGame).getPlayerStates().get(numPlayer).equals(Game.StateConnection.CONNECTED);
        }

        // if the player is not found then he should be in the waiting queue, assume he's connected
        return true;
    }
    /**
     * This function is called from event handler when a player disconnects from a game.
     * @param player the player that disconnected
     */
    public void setLostConnection(String player) {
        Integer game = playerToGame.get(player);
        games.get(game).setLostConnection(player);
    }

    /**
     * This function is called from event handler when a player voluntarily disconnects from a game.
     * @param player the player that disconnected
     */
    public void setStopConnection(String player){
        Integer game = playerToGame.get(player);
        games.get(game).setStopConnection(player);
    }

    /**
     * This function is called from event handler to check if a player has already lost connection
     * this avoids to send the same message multiple times
     * @param player the player that reconnected
     */
    public boolean alreadySetLostConnection(String player) {
        Integer game = playerToGame.get(player);

        if (game != null) {
            return games.get(game).alreadySetLostConnection(player);
        }

        return false;
    }


    //------------PLAYER STATE FUNCTIONS----------------


    //------------GAME STATE FUNCTIONS----------------

    /**
     * Called when there is a Connect to send the entire state of the game
     * @param player the player that connected is used to know which game to return
     * @return GameState
     */
    public GameState getGameState(String player) {
        Integer game = playerToGame.get(player);
        Board board = GameManager.getInstance().getBoard(game);
        CommonGoal[] commonGoals = GameManager.getInstance().getCommonGoals(game);
        String playerSeat = games.get(game).getPlayerSeat();
        String finishedFirst = games.get(game).getFinishedFirst();
        String playerTurn = GameManager.getInstance().getTurn(player);
        ArrayList<Player> players = GameManager.getInstance().getObjectPlayers(game);
        ArrayList<Tile> bag = GameManager.getInstance().getBag(game);

        Token[] topCommonGoals = new Token[2];

        topCommonGoals[0] = commonGoals[0].peekTokens();
        topCommonGoals[1] = commonGoals[1].peekTokens();

        return new GameState(game, board, commonGoals, playerSeat, playerTurn, finishedFirst, players, topCommonGoals, bag);
    }

    public void recalculateTurn(String player){
        int numGame = playerToGame.get(player);
        games.get(numGame).recalculateTurn();
    }

    //all the functions below are used to get the gameState and may become private later
    private Board getBoard(Integer game) {
        return games.get(game).getBoard();
    }

    private CommonGoal[] getCommonGoals(Integer game) {
        return games.get(game).getCommonGoals();
    }

    private ArrayList<Player> getObjectPlayers(Integer game) {
        return games.get(game).getPlayers();
    }

    public ArrayList<Tile> getBag(int game) {
        return games.get(game).getBag();
    }

    public String getTurn(String player){
        return games.get(playerToGame.get(player)).getTurn();
    }

    public boolean hasFinishedFirst(String player){
        String finishedFirst = games.get(playerToGame.get(player)).getFinishedFirst();
        if (player == null || finishedFirst == null) {
            return false;
        }
        else return (games.get(playerToGame.get(player)).getFinishedFirst().equals(player));
    }

    public boolean hasStartedLast(String player){
        if (player == null){
            System.out.println("GameManager-> hasStartedLast(): player is null IMPOSSIBLE in started last function");
            return false;
        }
        int startedFirst = games.get(playerToGame.get(player)).getPlayerSeatIndex();
        ArrayList<Player> players = games.get(playerToGame.get(player)).getPlayers();

        int playerIndex = games.get(playerToGame.get(player)).findPlayer(player);

        return playerIndex == (startedFirst + (players.size() - 1) % players.size());
    }

    public void unSetTurn(String player){
        games.get(playerToGame.get(player)).unSetTurn();
    }

    public boolean someoneElseFinished(String player){
        return (games.get(playerToGame.get(player)).getFinishedFirst() != null &&
                !games.get(playerToGame.get(player)).getFinishedFirst().equals(player));
    }

    public boolean someoneStillHasToPlay(String player){
        //check no error like calling this function but none of the players have finished
        if (games.get(playerToGame.get(player)).getFinishedFirst() == null) {
            System.out.println("GameManager-> someoneStillHasToPlay():No one has finished yet: ERROR IN LOGIC");
            return false;
        }
        return !hasStartedLast(player);
    }

    public String getWinner(String player) {
        if (games.get(playerToGame.get(player)).getFinishedFirst() == null) {
            System.out.println("GameManager-> getWinner(): No one has finished yet: ERROR IN LOGIC");
            return null;
        }

        ArrayList<Player> players = games.get(playerToGame.get(player)).getPlayers();

        int maxScore = -1;
        String winner = null;
        for (int i=0; i<players.size(); i++) {
            if (games.get(playerToGame.get(player)).getPoints(i) > maxScore) {
                maxScore = games.get(playerToGame.get(player)).getPoints(i);
                winner = players.get(i).getUsername();
            }
        }
        return winner;
    }

    public void closeGame(String player) {
        int game = playerToGame.get(player);

        EventHandler.getInstance().closeGame(games.get(game).getPlayersUsernames());

        for(String p: games.get(game).getPlayersUsernames()){

            //remove player from server
            if(SocketServer.getInstance().hasClient(p)) {
                SocketServer.getInstance().removeClient(p);
            }
            else if(RMIServer.getInstance().hasClient(p)){
                SocketServer.getInstance().removeClient(p);
            }
            else System.out.println("impossible error in close game");

            playerToGame.remove(p); //remove string player from player to game

            games.remove(game);  //remove integer game from arraylist

        }
    }

    public void lastPing(String player){
        int game = playerToGame.get(player);

        if (gameToLastPing.containsKey(game)){
            gameToLastPing.replace(game,gameToLastPing.get(game)+1);
        }
        else{
            gameToLastPing.put(game, 1);
        }
        if(gameToLastPing.get(game) == games.get(game).getPlayers().size()){
            closeGame(player);
        }
    }

    public void notifyWonForDisconnection(String winner){
        UpdateDispatcher.getInstance().dispatchResponse(new GameFinished(winner, winner));
    }
}
















