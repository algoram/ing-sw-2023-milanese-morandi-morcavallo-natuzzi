package myshelfie_model;

import myshelfie_model.board.Board;
import myshelfie_model.board.Board2players;
import myshelfie_model.board.Board3players;
import myshelfie_model.board.Board4players;
import myshelfie_model.goal.PersonalGoal;
import myshelfie_model.goal.Token;
import myshelfie_model.goal.common_goal.*;
import myshelfie_model.player.Bookshelf;
import myshelfie_model.player.Player;

import java.util.*;

public class Game {
    public final int MAX_PLAYERS = 4;

    private int playerSeat;
    private int turn;
    private int finishedFirst;

    private Board board;
    private ArrayList<Player> players;
    private ArrayList<Integer> playerStates = new ArrayList<>(); // -1 = disconnected, 0 = lostConnection, 1 = connected

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
        // check that the game is not already full
        if (players.size() == numberOfPlayers) {
            return false;
        }

        // check that there's not someone with the same username
        // Todo what if the player disconnects and then reconnects with different username?
        for (Player player : players) {
            if (player.getUsername().equals(username)) {
                if (playerStates.get(findPlayer(username)) == 0) { //if the player has lost connection
                    playerStates.add(findPlayer(username), 1);
                    return true;
                }
                return false;
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

        playerStates.add(playerIndex, 1); //add state connection

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
                playerStates.add(findPlayer(username),-1); //remove state connection
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
    public boolean takeTiles(String player, List<Position> chosenTiles, int column) {
        // check if the game has finished
        int playerNumber = findPlayer(player);
        if (hasFinished()) {
            return false;
        }

        // check if it's the playerNumber's turn
        if (playerNumber != turn) {
            return false;
        }

        // check that the player has enough space in the bookshelf
        if (players.get(playerNumber).getBookshelf().emptyCol()[column] < chosenTiles.size()) {
            return false;
        }

        // take the tiles from the board
        List<Tile> tiles;

        try {
            tiles = board.remove(chosenTiles);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }

        // insert the tiles inside the bookshelf column
        players.get(playerNumber).getBookshelf().fill(column, tiles);

        // check if the player has filled the bookshelf first
        if (players.get(playerNumber).getBookshelf().isFull() && finishedFirst == -1) {
            finishedFirst = playerNumber;
        }

        checkGoals(playerNumber);

        // go to the next player
       do{
           turn = (turn + 1) % players.size();
       }while(!(playerStates.get(turn) == 1)); //check if new turn player is connected

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
    public void checkGoals(int playerNumber) {
        // check common goals, personal ones are automatically checked
        for (int i = 0; i < commonGoals.length; i++) {
            // if the player has not achieved the goal before
            if (players.get(playerNumber).getCommonGoalPoints(i) == null) {
                // if the player has achieved the goal now
                if (commonGoals[i].check(players.get(playerNumber).getBookshelf())) {
                    // give the player the token at the top of the corresponding common goal token stack
                    players.get(playerNumber).setCommonGoalToken(i, commonGoals[i].popTokens());
                }
            }
        }
    }

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
        for (int i = 0; i < COMMON_GOALS; i++) {
            commonGoalsPoints += players.get(playerNumber).getCommonGoalPoints(i).getPoints();
        }

        // points awarded for adjacent tiles on the bookshelf
        int bookshelfPoints = players.get(playerNumber).getBookshelf().getPoints();

        return personalGoalPoints
                + commonGoalsPoints
                + bookshelfPoints
                + (playerNumber == finishedFirst ? 1 : 0); // point awarded for finishing first
    }

    //-------------------- Game Manager METHODS --------------------

    // Taketilesupdate Response methods

    public Bookshelf getBookshelf(String player) {
        return players.get(findPlayer(player)).getBookshelf();
    }

    public Token[] getCommonGoalTokens(String player) {
        Token[] tokens = new Token[COMMON_GOALS];

        for (int i = 0; i < COMMON_GOALS; i++) {
            tokens[i] = players.get(findPlayer(player)).getCommonGoalPoints(i);
        }

        return tokens;
    }

    public int getPersonalScore(String player) {
        return players.get(findPlayer(player)).getPersonalGoalPoints();
    }

    public boolean getFinishPoint(String player) {
        return findPlayer(player) == finishedFirst;
    }

    public int getAdjacentScore(String player) {
        return players.get(findPlayer(player)).getBookshelf().getPoints();
    }


    // GameSTate Response methods

    public Board getBoard() {
        return board;
    }

    public CommonGoal[] getCommonGoals() {
        return commonGoals;
    }

    public int getFinishedFirst() {
        return finishedFirst;
    }

    public int getPlayerSeat() {
        return playerSeat;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public String getTurn() {return players.get(turn).toString();}


    public boolean lostConnection(String player) {
        if (playerStates.get(findPlayer(player)) != 1) {
            System.out.println("Player " + player + " had already lost connection"); //Debug messages
            return false;
        }
        playerStates.add(findPlayer(player), 0);
        return true;
    }

    private int findPlayer(String player) {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getUsername().equals(player)) {
                return i;
            }
        }

        return -1;
    }





    //-------------------- DEBUGGING METHODS --------------------

    public void debugBoard() {
        Tile[][] tiles = board.getBoard();

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
}
