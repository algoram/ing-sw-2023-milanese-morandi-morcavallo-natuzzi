package myshelfie_model.board;

import myshelfie_model.Tile;
import myshelfie_model.Type;

import java.util.List;

public class Board3players extends Board {

    public Board3players() {
        this.board = new Tile[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.board[i][j] = null;
            }
        }
    }

    @Override
    public void refill(List<Tile> tiles) {


        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (BOARD_PRE_SET[row][col]>0 && BOARD_PRE_SET[row][col]<=3){
                    if( board[row][col].getType().equals(Type.EMPTY) ) {
                        this.board[row][col] = tiles.remove(0);
                        this.board[row][col].setPosition(row, col);
                    }
                }
            }
        }
    }


}
