package myshelfie_model;

import myshelfie_model.board.Board;
import myshelfie_model.goal.common_goal.CommonGoal;
import myshelfie_model.player.Player;

import java.util.ArrayList;

public class GameState {

    private final String gameName;
    private final Board board;
    private final CommonGoal[] commonGoals;

    private final int playerSeat;
    private int turn;
    private int finishedFirst;

    private ArrayList<Player> players;

    public GameState(String gameName, Board board, CommonGoal[] commonGoals, int playerSeat,int turn, int finishedFirst, ArrayList<Player> players) {
        this.gameName = gameName;
        this.board = board;
        this.commonGoals = commonGoals;
        this.playerSeat = playerSeat;
        this.turn = turn;
        this.finishedFirst = finishedFirst;
        this.players = players;
    }

    public void gameStateUpdate(int turn, int finishedFirst){
        this.turn = turn;
        this.finishedFirst = finishedFirst;

    }

    //TODO end Class GameState, save here game, use it to send update to client when connects


}
