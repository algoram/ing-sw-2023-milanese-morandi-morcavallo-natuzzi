package myshelfie_model;

import myshelfie_model.goal.common_goal.CommonGoal;

import java.util.List;

public class Game {
    public final int MAX_PLAYERS = 4;

    private int playerSeat;
    private int turn;
    private int finishedFirst;

    private Board board;
    private Player[] players;
    private List<Tile> bag;
    private CommonGoal[] commonGoals;

    public void startGame(int numPlayers) {

    }

    public void takeTiles() {

    }

    public void checkGoals() {

    }

    public int getPoints(int playerNumber) {
        int points = 0;

        return points;
    }

    public void refillBoard() {

    }
}
