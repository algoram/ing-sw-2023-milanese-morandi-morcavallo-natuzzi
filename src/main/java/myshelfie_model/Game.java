package myshelfie_model;

import myshelfie_controller.GameManager;
import myshelfie_controller.Settings;
import myshelfie_model.board.Board;
import myshelfie_model.board.Board2players;
import myshelfie_model.board.Board3players;
import myshelfie_model.board.Board4players;
import myshelfie_model.goal.PersonalGoal;
import myshelfie_model.goal.common_goal.*;
import myshelfie_model.player.Player;

import java.util.*;

public class Game {
    public final int MAX_PLAYERS = 4;

    private int playerSeat;
    private int turn;
    private Integer turnMemory = null; // used to remember the turn when just a player is still connected
    private int finishedFirst;

    private Board board;
    private ArrayList<Player> players;
    private ArrayList<StateConnection> playerStates = new ArrayList<>(); // -1 = disconnected, 0 = lostConnection, 1 = connected

    private ArrayList<Tile> bag;
    //private final int TILES = (Type.values().length - 1) * 22; // correct formula
    private final int TILES_PER_TYPE = 22;

    private CommonGoal[] commonGoals;
    private final int COMMON_GOALS = 2;

    private ArrayList<Integer> possiblePersonalGoals;
    private int numberOfPlayers;

    public static Game fromGameState(GameState state) {
        Game g = new Game();

        g.board = state.getBoard();
        g.players = state.getPlayers();

        g.playerStates = new ArrayList<>();
        for (int i = 0; i < g.players.size(); i++) {
            g.playerStates.add(StateConnection.LOST_CONNECTION);
        }

        g.bag = state.getBag();
        g.commonGoals = state.getCommonGoals();
        g.numberOfPlayers = g.players.size();

        g.finishedFirst = -1;
        for (int i = 0; i < g.numberOfPlayers; i++) {
            String username = g.players.get(i).getUsername();

            if (username.equals(state.getPlayerSeat())) {
                g.playerSeat = i;
            }

            if (username.equals(state.getPlayerTurn())) {
                g.turn = i;
            }

            if (username.equals(state.getFinishedFirst())) {
                g.finishedFirst = i;
            }
        }

        return g;
    }

    /**
     * Starts a new game with the given number of players (clamped between 2 and MAX_PLAYERS),
     * taking care to create the players, give them personal goals, create the board, create
     * common goals for the players to achieve and fill the board for the first turn
     * @param numPlayers the number of players that will be playing (will be clamped between 2 and MAX_PLAYERS)
     */
    public void startGame(int numPlayers) {
        Random random = new Random();

        // clamp the number of players
        if (numPlayers < 2) numPlayers = 2;
        if (numPlayers > MAX_PLAYERS) numPlayers = MAX_PLAYERS;

        numberOfPlayers = numPlayers;

        players = new ArrayList<>(numberOfPlayers);

        // create the different personal goals
        possiblePersonalGoals = new ArrayList<>();

        for (int i = 0; i < PersonalGoal.MAX_CARDS; i++) {
            possiblePersonalGoals.add(i + 1);
        }

        // create all the tiles in a random order
        int[] types = new int[Type.values().length];

        for (int i = 0; i < Type.values().length; i++) {
            types[i] = TILES_PER_TYPE;
        }

        bag = new ArrayList<>();

        for (int i = 0; i < types.length * TILES_PER_TYPE; i++) {
            int typeIndex = random.nextInt(types.length);

            while (types[typeIndex] == 0) {
                typeIndex = random.nextInt(types.length);
            }

            bag.add(new Tile(Type.values()[typeIndex]));
        }

        // create the board
        switch (numPlayers) {
            case 2 -> board = new Board2players();
            case 3 -> board = new Board3players();
            case 4 -> board = new Board4players();
        }

        // fill the board
        board.refill(bag);

        // list of all the common goals that can be used for the game
        CommonGoal[] possibleCommonGoals = {
                new Cross(numPlayers),
                new Diagonal5Tiles(numPlayers),
                new EightTiles(numPlayers),
                new FourCorners(numPlayers),
                new FourGroups4Tiles(numPlayers),
                new FourLinesMax3(numPlayers),
                new Pyramid(numPlayers),
                new SixGroups2Tiles(numPlayers),
                new ThreeColumnsMax3(numPlayers),
                new TwoColumns(numPlayers),
                new TwoLines(numPlayers),
                new TwoSquares(numPlayers),
        };

        // create the common goals
        commonGoals = new CommonGoal[COMMON_GOALS];
        int goalsCreated = 0;

        while (goalsCreated < COMMON_GOALS) {
            // generate a random index for the list of possible common goals
            int nextCommonGoal = random.nextInt(possibleCommonGoals.length);

            // check whether the common goal generated randomly is not already present
            boolean notTaken = true;
            for (int i = 0; i < goalsCreated && notTaken; i++) {
                if (commonGoals[i] == possibleCommonGoals[nextCommonGoal]) {
                    notTaken = false;
                }
            }

            // if it's not already present we can go to the next one
            if (notTaken) {
                commonGoals[goalsCreated] = possibleCommonGoals[nextCommonGoal];
                goalsCreated++;
            }
        }

        // choose a random player to start the game
        playerSeat = random.nextInt(numPlayers);

        // also set the turn to the first player
        // todo what if the first player disconnects before the game starts?
        turn = playerSeat;

        // no one has finished yet
        finishedFirst = -1;
    }

    /**
     * Returns all the tiles that could be picked by a player
     * @return all the tiles that could be picked by a player
     */
    public ArrayList<Position> getAvailableTiles() {
        return board.getAvailableTiles(players.size());
    }

    /**
     * Adds a player to the game if it's not full and if there's not already someone with the same username
     * @param username the username that the player will be identified with
     * @return whether the player has been added or not
     */
    public boolean addPlayer(String username) throws Exception{

        // check that there's not someone with the same username
        for (Player player : players) {
            if (player.getUsername().equals(username)) {

                if (playerStates.get(findPlayer(username)) == StateConnection.LOST_CONNECTION) { //if the player has lost connection
                    playerStates.add(findPlayer(username), StateConnection.CONNECTED);
                    return true;
                }
                System.out.println("There's already someone with the same username that did not lose connection -> error");
                throw new Exception("There's already someone with the same username that did not lose connection, should not be possible to enter this if");
            }
        }

        Random random = new Random();

        // generate a random personal goal from the above list
        int personalGoalIndex = random.nextInt(possiblePersonalGoals.size());

        // create the player and give them a unique personal goal
        players.add(new Player(
                username,
                new PersonalGoal(possiblePersonalGoals.get(personalGoalIndex))
        ));

        // find where the player is located
        int playerIndex = -1;

        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getUsername().equals(username)) {
                playerIndex = i;
            }
        }

        playerStates.add(playerIndex, StateConnection.CONNECTED); //add state connection

        // remove the goal from the list to avoid giving the same to another player
        possiblePersonalGoals.remove(personalGoalIndex);

        return true;
    }



    /**
     * Removes a player from the game when it disconnects
     *
     * @param username the username of the player to remove
     */
    public void removePlayer(String username) throws Exception{
        for (Player player : players) {
            if (player.getUsername().equals(username)) {
                playerStates.add(findPlayer(username),StateConnection.DISCONNECTED); //remove state connection

                if(players.get(turn).getUsername().equals(username)) //if the player is the active player
                    recalculateTurn();

                return;
            }
        }
        checkSomeOneStillConnected(username);
    }

    /**
     * Returns an array of usernames
     * @return array of usernames
     */
    public List<String> getPlayersUsernames() {
        return players.stream().map(Player::getUsername).toList();
    }

    /**
     * Returns the number of players the game can have
     * @return the number of players the game can have
     */
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    /**
     * Takes one to three tiles and gives them to the active player,
     * putting them in the column specified, checks whether the move is legal
     * (the chosen tiles all have at least one free side) and the column can take
     * all the tiles chosen, if the move is successful, returns true
     * @param player the player username
     * @param chosenTiles a list of positions that represent the tiles to take from the board
     * @param column the column in which the player wants to store the taken tiles
     * @return a boolean that indicates whether the move was successful or not
     */
    public boolean takeTiles(String player, List<Position> chosenTiles, int column) throws Exception {
        // check if the game has finished
        int playerNumber = findPlayer(player);
        if (isGameFinished()) {
            if (Settings.DEBUG)System.out.println("GAME -> take HAS FINISHED BUT TAKE TILES IMPOSSIBLE");
            return false;
        }

        // check if it's the playerNumber's turn
        if (playerNumber != turn) {
            if (Settings.DEBUG)System.out.println("GAME -> take TILES !=playerNUm turn IMPOSSIBLE");
            turn = playerNumber;
        }

        // check that the player has enough space in the bookshelf
        if (players.get(playerNumber).getBookshelf().emptyCol()[column] < chosenTiles.size()) {
            throw new Exception("Take Tiles Failed: No Enough space in Bookshelf column");
        }

        // take the tiles from the board
        List<Tile> tiles = null;

        tiles = board.remove(chosenTiles);

        if (tiles == null){
            if (Settings.DEBUG) System.out.println("SHOULD OT BE POSSIBLE TO ENTER HERE");
            throw new Exception("Take Tiles Failure: remove returned no Tiles to add in Bookshelf");
        }
        // insert the tiles inside the bookshelf column
        players.get(playerNumber).getBookshelf().fill(column, tiles);

        if(board.refillNeeded()){
            //let's take the tiles removing them from the bag
            board.refill(bag);
        }

        //SET POINTS
        // check if the player has filled the bookshelf first
        if (players.get(playerNumber).getBookshelf().isFull() && finishedFirst == -1) {
            players.get(playerNumber).setFinishedFirst(true);
            finishedFirst = playerNumber;
        }
        checkGoals(playerNumber);

        int numConnectedPlayers = 0;
        for(int i=0; i<players.size(); i++){ //check if there are still connected players
            if (playerStates.get(i).equals(StateConnection.CONNECTED)) {
                numConnectedPlayers++;
            }
        }

        if(numConnectedPlayers <= 1){ //if there is only one connected player

            if (Settings.DEBUG) System.out.println("GAME -> take TILES: only one connected player");

            //we don't modify turn the player goes on playing alone
            // until someone else reconnects
            return true;
        }

        //if the turn is unsetted during my take tiles, I should not recalculate it again but just
        // change the turn memory. (it was on me but now has to be no more)
        if (turn != -1) recalculateTurn();
        else {
            turnMemory =(turnMemory + 1) % players.size(); //no problem if next player is disconnected, we will check it later in recalculateTurn
        }

        return true;
    }

    /**
     * Returns whether the game has finished yet or not
     * @return whether tha game has finished yet or not
     */
    public boolean isGameFinished() {
        return finishedFirst != -1 &&   // someone has completed the bookshelf
                turn == playerSeat;     // the player to the left of the first player has completed his last move
    }

    /**
     * Checks if personal or common goals are achieved by the indicated player and
     * gives them the points according to the game rules
     * @param playerNumber player to award the points to
     */
    private void checkGoals(int playerNumber) {
        // check common goals, personal ones are automatically checked
        for (int i = 0; i < commonGoals.length; i++) {

            // if the player has not achieved the goal before
            if (players.get(playerNumber).getCommonTokens()[i] == null) {

                // if the player has achieved the goal now
                if (commonGoals[i].check(players.get(playerNumber).getBookshelf())) {

                    // give the player the token at the top of the corresponding common goal token stack
                    players.get(playerNumber).setCommonToken(i, commonGoals[i].popTokens());

                }
            }
        }
    }
/*
    /**
     * Returns the total number of points achieved by a player, including:
     * - points awarded for achieving common goals
     * - points awarded for achieving personal goals
     * - points awarded for having groups of adjacent tiles in the bookshelf
     * - point awarded for being first to finish
     * @param playerNumber index of the player of which we want the points
     * @return the total number of points achieved by a player
     */
    public int getPoints(int playerNumber) {
        // points awarded for achieving the personal goal
        int personalGoalPoints = players.get(playerNumber).getPersonalGoalPoints();

        // points awarded for achieving the common goals
        int commonGoalsPoints = 0;
        commonGoalsPoints += players.get(playerNumber).getCommonGoalPoints();


        // points awarded for adjacent tiles on the bookshelf
        int bookshelfPoints = players.get(playerNumber).getBookshelf().getPoints();

        return personalGoalPoints
                + commonGoalsPoints
                + bookshelfPoints
                + (playerNumber == finishedFirst ? 1 : 0); // point awarded for finishing first
    }


    // GameSTate Response methods

    public Board getBoard() {
        return board;
    }

    public CommonGoal[] getCommonGoals() {
        return commonGoals;
    }

    public String getFinishedFirst() {
        if (finishedFirst == -1) return null; //if no one has finished yet
        return players.get(finishedFirst).getUsername();
    }

    public String getPlayerSeat() {
        return players.get(playerSeat).getUsername();
    }

    public int getPlayerSeatIndex() {
        return playerSeat;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Tile> getBag() {
        return bag;
    }

    public String getTurn() {
        if(turn != -1)
            return players.get(turn).getUsername();
        else
            return null;
    }

    /**
     * Calculates the next turn.
     * If there is a turn memory it means that one player was left alone in the game.
     * In this case the turn is set to memory, and it may be non connected anymore:
     * player A,B,C are playing. C is of turn. B and C disconnect simultaneously. C is set to memory turn.
     * If B reconnects turnMemory will remember about C, but he is still disconnected, so we have to recalculate the turn.
     */
    public void recalculateTurn() throws Exception{

        if (turnMemory != null){ //if there is a turn memory

            turn = turnMemory; //set the turn to the turn memory
            turnMemory = null; //reset the turn memory
            if (!playerStates.get(turn).equals(StateConnection.CONNECTED)) //if the player that was on memory turn is not connected explanation in javadoc
                recalculateTurn();
            else
                return;
        }

        // go to the next player
        int saveTurn = turn;
        do{
            turn = (turn + 1) % players.size();
            if (turn == saveTurn) {
                System.out.println("just one player left connected...\nhe is going to wait for someone else to reconnect");
                unSetTurn();
            }
        }while(!(playerStates.get(turn) == StateConnection.CONNECTED)); //check if new turn player is connected
    }

    public void unSetTurn(){
        System.out.println("GAME -> UNSETTurn: Turn is now unset");
        if(turn == -1)
            System.out.println("GAME -> UNSETTurn: Turn is already unset, this is no error");
        turn = -1;
    }

    public void setTurnMemory(int turnMemory) {
        this.turnMemory = turnMemory;
    }

    public Integer getTurnMemory() {
        return turnMemory;
    }

    /**
     * set the player state to zero -> disconnected
     * @param player
     * @return
     */
    public boolean setLostConnection(String player) {
        if (playerStates.get(findPlayer(player)) != StateConnection.CONNECTED) {
            System.out.println("Player " + player + " had already lost connection or disconnected"); //Debug messages
            return false;
        }
        playerStates.add(findPlayer(player), StateConnection.LOST_CONNECTION);
        if (justOnePlayerConnected()) {
            System.out.println("GAME -> setLostConnection: Just one player connected, he is going to wait for someone else to reconnect");
            unSetTurn();
        }
        checkSomeOneStillConnected(player);
        return true;
    }

    private boolean justOnePlayerConnected(){
        int connectedPlayers = 0;
        for (StateConnection state: playerStates) {
            if (state == StateConnection.CONNECTED)
                connectedPlayers++;
        }
        return connectedPlayers == 1;
    }

    private void checkSomeOneStillConnected(String player){
        for (Player p: players) {
            if (playerStates.get(findPlayer(p.getUsername())) == StateConnection.CONNECTED){
                return;
            }
        }
        System.out.println("GAME -> checkSomeOneStillConnected: All players have lost connection");
        //let's close the game
        GameManager.getInstance().closeGame(player);
    }

    /**
     * check if the player has already lost connection
     * @param  player the player to check
     * @return true if the player has already lost connection
     */
    public boolean alreadySetLostConnection(String player){
        return playerStates.get(findPlayer(player)) != StateConnection.CONNECTED;
    }

    /**
     * returns the index of the player in the players arraylist
     * @param player
     * @return the index of the player in the players arraylist
     */
    public int findPlayer(String player) {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getUsername().equals(player)) {
                return i;
            }
        }
        return -1;
    }

    public ArrayList<StateConnection> getPlayerStates() {
        return playerStates;
    }




    //-------------------- DEBUGGING METHODS --------------------

    public void debugBoard() {
        Tile[][] tiles = board.getBoardTest();

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (board.isLegalPosition(players.size(), i, j)) {
                    if (tiles[i][j] == null) {
                        System.out.print("X");
                    } else {
                        for (int t = 0; t < Type.values().length; t++) {
                            if (tiles[i][j].getType() == Type.values()[t]) {
                                System.out.print(t);
                            }
                        }
                    }
                } else {
                    System.out.print(" ");
                }
            }

            System.out.println();
        }
    }

    public enum StateConnection{
        CONNECTED,
        DISCONNECTED,
        LOST_CONNECTION
    }
}



