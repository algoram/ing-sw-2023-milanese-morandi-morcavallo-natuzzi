package myshelfie_model.board;

import myshelfie_model.Tile;
import myshelfie_model.board.Board;

import java.util.List;

public class Board4players implements Board {
    private Tile[][] board;

    public Board4players() {
        this.board = new Tile[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = null;
            }
        }
    }

    @Override
    public void refill(List<Tile> tiles) {

    }

    @Override
    public boolean refillNeeded() {
        return false;
    }

    @Override
    public List remove(List<Tile> chosen) {
        return null;
    }
}
