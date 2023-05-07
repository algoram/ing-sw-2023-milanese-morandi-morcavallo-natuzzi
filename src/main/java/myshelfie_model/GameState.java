package myshelfie_model;

import myshelfie_model.board.Board;
import myshelfie_model.goal.Token;
import myshelfie_model.goal.common_goal.CommonGoal;
import myshelfie_model.player.Player;

import java.io.*;
import java.util.ArrayList;

public class GameState implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private final Integer gameNumber;
    private final Board board;
    private final CommonGoal[] commonGoals;

    private final String playerSeat; //the number of the player who started first

    private final String finishedFirst;
    private final String playerTurn;
    private final Token[] topCommonGoal;

    private final ArrayList<Player> players;

    public GameState(Integer gameNumber,
                     Board board,
                     CommonGoal[] commonGoals,
                     String playerSeat,
                     String playerTurn,
                     String finishedFirst,
                     ArrayList<Player> players,
                     Token[] topCommonGoal) {
        this.gameNumber = gameNumber;
        this.board = board;
        this.commonGoals = commonGoals;
        this.playerSeat = playerSeat;
        this.finishedFirst = finishedFirst;
        this.players = players;
        this.playerTurn = playerTurn;
        this.topCommonGoal = topCommonGoal;
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

    public String getPlayerSeat() {
        return playerSeat;
    }

    public String getPlayerTurn() {
        return playerTurn;
    }

    public Token[] getTopCommonGoal() { return topCommonGoal; }
    public String getFinishedFirst() {
        return finishedFirst;
    }
    public ArrayList<Player> getPlayers() {
        return players;
    }

}