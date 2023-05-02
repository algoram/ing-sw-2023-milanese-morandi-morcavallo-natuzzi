
package myshelfie_model;

import myshelfie_model.board.Board;
import myshelfie_model.goal.Token;
import myshelfie_model.player.Bookshelf;

import java.io.*;
@Deprecated
public class GameUpdate {

    private static final long serialVersionUID = 4532440671217154070L;

    private final String playerUpdated;
    private final Board board;
    private final Bookshelf bookshelf;
    private final Token[] commontokens;
    private final int finishPoint;
    private final int adjacentScore;
    private Integer personalScore = 0;//THIS SCORE IS SET TO NULL WHEN THE sent to a player
                                        // that has not done the takeTiles
    private final String playerTurn;

    public GameUpdate (String playerUpdated, Board board, Bookshelf bookshelf, Token[] commontokens,int finishPoint, int adjacentScore, int personalScore, String playerTurn) {
        this.playerUpdated = playerUpdated;
        this.board = board;
        this.bookshelf = bookshelf;
        this.commontokens = commontokens;
        this.finishPoint = finishPoint;
        this.adjacentScore = adjacentScore;
        this.personalScore = personalScore;
        this.playerTurn = playerTurn; //not used in view when opens the update
    }

    public String getPlayerUpdated(){return playerUpdated;}
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
        this.personalScore = null;
    }

    public String getPlayerTurn() {
        return playerTurn;
    }

    public GameUpdate deepClone() {
        try {
            // Serialize the original GameState object to a byte array
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.close();
            byte[] serializedGameState = byteArrayOutputStream.toByteArray();

            // Deserialize the byte array to create a new GameState object (deep clone)
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(serializedGameState);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            GameUpdate clonedGameUpdate = (GameUpdate) objectInputStream.readObject();
            objectInputStream.close();

            return clonedGameUpdate;
        } catch ( ClassNotFoundException | IOException e) {
            // Handle the exception as desired (e.g., log the error, rethrow it, or return null)
            return null;
        }
    }
}
