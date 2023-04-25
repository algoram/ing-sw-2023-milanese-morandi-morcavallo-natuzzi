package myshelfie_model;

import myshelfie_model.board.Board;
import myshelfie_model.goal.common_goal.CommonGoal;
import myshelfie_model.player.Player;

import java.util.ArrayList;

public class GameState {

    private final Integer gameNumber;
    private final Board board;
    private final CommonGoal[] commonGoals;

    private final int playerSeat;
    private final int turn;
    private final int finishedFirst;

    private ArrayList<Player> players;

    public GameState(Integer gameNumber, Board board, CommonGoal[] commonGoals, int playerSeat,int turn, int finishedFirst, ArrayList<Player> players) {
        //TODO deep clone objects by serializing and deserializing

        this.gameNumber = gameNumber;
        this.board = board;
        this.commonGoals = commonGoals;
        this.playerSeat = playerSeat;
        this.turn = turn;
        this.finishedFirst = finishedFirst;
        this.players = players;
    }

    public Integer getGameNumber() {
        return gameNumber;
    }

    public Board getBoard() {
        return board;
    }

    public CommonGoal[] getCommonGoals() {
        return commonGoals;
    }

    public int getPlayerSeat() {
        return playerSeat;
    }

    public int getTurn() {
        return turn;
    }

    public int getFinishedFirst() {
        return finishedFirst;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
