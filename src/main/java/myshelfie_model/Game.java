package myshelfie_model;

import myshelfie_model.board.Board;
import myshelfie_model.goal.common_goal.CommonGoal;
import myshelfie_model.player.Player;

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
        // TODO: check personal goals

        // check both common goals
        for (int i = 0; i < commonGoals.length; i++) {
            for (Player p : players) {
                // if the player still hasn't achieved the common goal
                if (p.commonGoalsPoints[i] == 0) {
                    int points = commonGoals[i].check(p.getBookshelf());

                    // the player has achieved the goal
                    if (points != -1) {
                        p.commonGoalsPoints[i] = points;
                    }
                }
            }
        }
    }

    public int getPoints(int playerNumber) {
        // points awarded for achieving the personal goal
        int personalGoalPoints = players[playerNumber].getPersonalGoalPoints();

        // points awarded for achieving the common goals
        List<Integer> commonGoalsPointsList = players[playerNumber].getCommonGoalsPoints();
        int commonGoalsPoints = commonGoalsPointsList.stream().mapToInt(Integer::intValue).sum();

        // points awarded for adjacent tiles on the bookshelf
        int bookshelfPoints = players[playerNumber].getBookshelf().getPoints();

        return personalGoalPoints
                + commonGoalsPoints
                + bookshelfPoints
                + (playerNumber == finishedFirst ? 1 : 0); // point awarded for finishing first
    }

    public void refillBoard() {

    }
}
