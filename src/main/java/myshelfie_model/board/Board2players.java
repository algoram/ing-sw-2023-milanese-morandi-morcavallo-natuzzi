package myshelfie_model.board;

import myshelfie_model.Tile;
import myshelfie_model.Type;
import myshelfie_model.board.Board;

import java.util.List;

public class Board2players implements Board {
    private Tile[][] board;

    public Board2players() {
        this.board = new Tile[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.board[i][j] = null;
            }
        }
    }

    @Override
    public void refill(List<Tile> tiles) {
        for (int row = 0; row < 9 ; row++) {
            for (int col = 0; col < 9; col++) {

                //refill all the spaces II
                if (row == 1){
                    if(col == 3 || col == 4){
                        if( board[row][col].getType().equals(Type.EMPTY) ) this.board[row][col] = tiles.remove(0);
                    }
                }

                if (row == 2){
                    if(col == 3 || col == 4 || col == 5){
                        if( board[row][col].getType().equals(Type.EMPTY) )  this.board[row][col] = tiles.remove(0);
                    }
                }

                if (row == 3){
                    if(col == 2 ||col == 3 || col == 4 || col == 5|| col == 6|| col == 7) {
                        if( board[row][col].getType().equals(Type.EMPTY) )  this.board[row][col] = tiles.remove(0);
                    }
                }

                if (row == 4){
                    if(col == 1 ||col == 2 ||col == 3 || col == 4 || col == 5|| col == 6|| col == 7) {
                        if( board[row][col].getType().equals(Type.EMPTY) )  this.board[row][col] = tiles.remove(0);
                    }
                }

                if (row == 5) {
                    if (col == 1 || col == 2 || col == 3 || col == 4 || col == 5 || col == 6) {
                        if (board[row][col].getType().equals(Type.EMPTY)) this.board[row][col] = tiles.remove(0);
                    }
                }

                if (row == 6){
                    if(col == 3 || col == 4 || col == 5) {
                        if( board[row][col].getType().equals(Type.EMPTY) )  this.board[row][col] = tiles.remove(0);
                    }
                }

                if (row == 7){
                    if(col == 4 || col == 5) {
                        if( board[row][col].getType().equals(Type.EMPTY) )  this.board[row][col] = tiles.remove(0);
                    }
                }
            }
        }
    }

}
