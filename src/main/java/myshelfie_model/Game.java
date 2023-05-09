package myshelfie_model;

import myshelfie_controller.Settings;
import myshelfie_model.board.Board;
import myshelfie_model.board.Board2players;
import myshelfie_model.board.Board3players;
import myshelfie_model.board.Board4players;
import myshelfie_model.goal.PersonalGoal;
import myshelfie_model.goal.common_goal.*;
import myshelfie_model.player.Player;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;

public class Game {
    public final int MAX_PLAYERS = 4;

    private int playerSeat;
    private int turn;
    private int finishedFirst;

    private Board board;
    private ArrayList<Player> players;
    private ArrayList<StateConnection> playerStates = new ArrayList<>(); // -1 = disconnected, 0 = lostConnection, 1 = connected

    private List<Tile> bag;
    //private final int TILES = (Type.values().length - 1) * 22; // correct formula
    private final int TILES_PER_TYPE = 22;

    private CommonGoal[] commonGoals;
    private final int COMMON_GOALS = 2;

    private ArrayList<Integer> possiblePersonalGoals;
    private int numberOfPlayers;

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
    public boolean addPlayer(String username) {
        // should not be possible to enter this if
        if (players.size() == numberOfPlayers) {
            System.out.println("The game is full error in calling addPlayer()");
            return false;
        }

        // check that there's not someone with the same username
        for (Player player : players) {
            if (player.getUsername().equals(username)) {

                if (playerStates.get(findPlayer(username)) == StateConnection.LOST_CONNECTION) { //if the player has lost connection
                    playerStates.add(findPlayer(username), StateConnection.CONNECTED);
                    return true;
                }
                System.out.println("There's already someone with the same username that did not lose connection -> error");
                return false; //should not be possible to enter this if
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
     * @param username the username of the player to remove
     * @return whether the player has been removed or not
     */
    public boolean removePlayer(String username){
        for (Player player : players) {
            if (player.getUsername().equals(username)) {
                playerStates.add(findPlayer(username),StateConnection.DISCONNECTED); //remove state connection
                return true;
            }
        }
        return false;
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
        if (hasFinished()) {
            if (Settings.DEBUG)System.out.println("GAME -> take HAS FINISHED BUT TAKE TILES IMPOSSIBLE");
            return false;
        }

        // check if it's the playerNumber's turn
        if (playerNumber != turn) {
            if (Settings.DEBUG)System.out.println("GAME -> take TILES !=playerNUm turn IMPOSSIBLE");
            return false;
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


        // go to the next player
        int i = 0;
       do{
           turn = (turn + 1) % players.size();
           i++;
           if (i == players.size()) {
               System.out.println("All players have lost connection");
               //todo: manage the case in which all players have lost connection
               throw new Exception("Take Tiles Failure: All players lost connection");
           }
       }while(!(playerStates.get(turn) == StateConnection.CONNECTED)); //check if new turn player is connected

        return true;
    }

    /**
     * Returns whether the game has finished yet or not
     * @return whether tha game has finished yet or not
     */
    public boolean hasFinished() {
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

    public String getTurn() {return players.get(turn).getUsername();}


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
        return true;
    }

    public boolean alreadySetLostConnection(String player){
        if (playerStates.get(findPlayer(player)) != StateConnection.CONNECTED){
            return true;//the client was already set to disconnected in game
        }
        return false;
    }

    /**
     * returns the index of the player in the players arraylist
     * @param player
     * @return
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



