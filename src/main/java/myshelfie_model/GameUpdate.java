package myshelfie_model;

import myshelfie_model.board.Board;
import myshelfie_model.goal.Token;
import myshelfie_model.player.Bookshelf;

public class GameUpdate {
    private Board board;
    private Bookshelf bookshelf;

    private Token[] commontokens;
    private int finishPoint;
    private int adjacentScore;
    private int personalScore;

    public GameUpdate (Board board, Bookshelf bookshelf, Token[] commontokens,int finishPoint, int adjacentScore, int personalScore) {
        //TODO clone gameUpdate
        this.board = board;
        this.bookshelf = bookshelf;
        this.commontokens = commontokens;
        this.finishPoint = finishPoint;
        this.adjacentScore = adjacentScore;
        this.personalScore = personalScore;
    }

    public Board getBoard() {
        return board;
    }

    public Bookshelf getBookshelf() {
        return bookshelf;
    }

    public Token[] getCommonTokens() {return commontokens;}

    public int getFinishPoint() {
        return finishPoint;
    }

    public int getAdjacentScore() {
        return adjacentScore;
    }

    public int getPersonalScore() {
        return personalScore;
    }

    public void removePersonalData() {
        this.personalScore = 0;
    }
}
