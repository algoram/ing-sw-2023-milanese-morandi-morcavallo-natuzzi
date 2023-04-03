package myshelfie_model;

import myshelfie_model.board.Board;
import myshelfie_model.board.Board2players;
import myshelfie_model.board.Board3players;
import myshelfie_model.board.Board4players;
import myshelfie_model.goal.common_goal.*;
import myshelfie_model.player.Player;

import javax.swing.text.Position;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class Game {
    public final int MAX_PLAYERS = 4;

    private int playerSeat;
    private int turn;
    private int finishedFirst;

    private Board board;
    private Player[] players;

    private List<Tile> bag;
    //private final int TILES = (Type.values().length - 1) * 22; // correct formula
    private final int TILES = 132;

    private CommonGoal[] commonGoals;
    private final int COMMON_GOALS = 2;

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

        // create the players
        for (int i = 0; i < numPlayers; i++) {
            players[i] = new Player();

            // TODO: give the players a personal goal from a fixed list (maybe read from a file ???)
        }

        // create all the tiles in a random order
        for (int i = 0; i < TILES; i++) {
            // TODO: we removed the empty Type
            //bag.add(new Tile(Type.EMPTY));

            // TODO: add types to the tiles
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
        // TODO: complete the list with all 12 common goals
        CommonGoal[] possibleCommonGoals = {
                new Cross(numPlayers),
                new Diagonal5Tiles(numPlayers),
                new EightTiles(numPlayers),
                new FourLinesMax3(numPlayers),
                new Pyramid(numPlayers),
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
        turn = playerSeat;

        // no one has finished yet
        finishedFirst = -1;
    }

    /**
     * Takes one to three tiles and gives them to the active player,
     * putting them in the column specified, checks whether the move is legal
     * (the chosen tiles all have at least one free side) and the column can take
     * all the tiles chosen, if the move is successful, returns true
     * @param chosenTiles a list of positions that represent the tiles to take from the board
     * @param column the column in which the player wants to store the taken tiles
     * @return a boolean that indicates whether the move was successful or not
     */
    public boolean takeTiles(List<Position> chosenTiles, int column) {
        // TODO: check if the move is legal

        // TODO: wait for the board method to accept a List<Position>

        return false;
    }

    /**
     * Checks if personal or common goals are achieved by a player and
     * gives them the points according to the game rules
     */
    public void checkGoals() {
        // TODO: check personal goals

        // check both common goals
        for (int i = 0; i < commonGoals.length; i++) {
            for (Player p : players) {
                // if the player still hasn't achieved the common goal
                if (p.getCommonGoalPoints(i) == null) {
                    // if the player has now achieved the common goal
                    if (commonGoals[i].check(p.getBookshelf())) {
                        p.setCommonGoalToken(i,commonGoals[i].popTokens());
                    }
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
        int personalGoalPoints = players[playerNumber].getPersonalGoalPoints();

        // points awarded for achieving the common goals
        int commonGoalsPoints = 0;
        for (int i = 0; i < COMMON_GOALS; i++) {
            commonGoalsPoints += players[playerNumber].getCommonGoalPoints(i).getPoints();
        }

        // points awarded for adjacent tiles on the bookshelf
        int bookshelfPoints = players[playerNumber].getBookshelf().getPoints();

        return personalGoalPoints
                + commonGoalsPoints
                + bookshelfPoints
                + (playerNumber == finishedFirst ? 1 : 0); // point awarded for finishing first
    }

    // TODO: check if method is still needed (i don't think so, it can be handled inside takeTiles)
    public void refillBoard() {

    }
}
