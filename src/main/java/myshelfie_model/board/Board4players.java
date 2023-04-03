package myshelfie_model.board;

import myshelfie_model.BoardPosition;
import myshelfie_model.Tile;

import java.util.List;

public class Board4players extends Board {

    public Board4players() {
        this.board = new Tile[BOARD_LENGTH][BOARD_LENGTH];

        for (int row = 0; row < BOARD_LENGTH; row++) {
            for (int col = 0; col < BOARD_LENGTH; col++) {
                this.board[row][col] = null;
            }
        }
    }

    @Override
    public void refill(List<Tile> tiles) {


        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (BOARD_PRE_SET[row][col]>0 && BOARD_PRE_SET[row][col]<=4){
                    if( board[row][col]==null  ) {
                        this.board[row][col] = tiles.remove(0);
                        this.board[row][col].setPosition(row, col);
                    }
                }
            }
        }
    }

    protected boolean CheckBoardPosition(BoardPosition pos){
        if(BOARD_PRE_SET[pos.getRow()][pos.getColumn()]==0){
            return false;
        }
        return true;
    }


}
