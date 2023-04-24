package myshelfie_controller.response;


import myshelfie_model.board.Board;
import myshelfie_model.goal.Token;
import myshelfie_model.player.Bookshelf;

public class TakeTilesSuccess extends Response {

    private Board board;
    private Bookshelf bookshelf;

    private Token[] commontokens;
    private Token finishToken;
    private int adjacentScore;
    private int personalScore;

    public TakeTilesSuccess(String player, String game, Board board, Bookshelf bookshelf, Token[] commontokens, Token finishToken, int adjacentScore, int personalScore) {
        super(player, game);

        this.board = board;
        this.bookshelf = bookshelf;
        this.commontokens = commontokens;
        this.finishToken = finishToken;
        this.adjacentScore = adjacentScore;
        this.personalScore = personalScore;
    }

    public Board getBoard() {
        return board;
    }

    public Bookshelf getBookshelf() {
        return bookshelf;
    }

    public Token[] getCommonTokens() {
        return commontokens;
    }

    public Token getFinishToken() {
        return finishToken;
    }

    public int getAdjacentScore() {
        return adjacentScore;
    }

    public int getPersonalScore() {
        return personalScore;
    }
}
