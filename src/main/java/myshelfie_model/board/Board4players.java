package myshelfie_model.board;

import myshelfie_model.Tile;
import myshelfie_model.Type;
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
        for (int i = 0; i < 9 ; i++) {
            for (int j = 0; j < 9; j++) {

                //refill all the spaces IV
                if (i == 0){
                    if(j == 4){
                        if( board[i][j].getType().equals(Type.EMPTY) ) board[i][j] = tiles.remove(0);
                    }
                }
                if (i == 1){
                    if(j == 5){
                        if( board[i][j].getType().equals(Type.EMPTY) ) board[i][j] = tiles.remove(0);
                    }
                }
                if (i == 3){
                    if(j == 1){
                        if( board[i][j].getType().equals(Type.EMPTY) ) board[i][j] = tiles.remove(0);
                    }
                }
                if (i == 4){
                    if(j == 0 || j == 8){
                        if( board[i][j].getType().equals(Type.EMPTY) ) board[i][j] = tiles.remove(0);
                    }
                }
                if (i == 5){
                    if(j == 7){
                        if( board[i][j].getType().equals(Type.EMPTY) ) board[i][j] = tiles.remove(0);
                    }
                }
                if (i == 7){
                    if(j == 3){
                        if( board[i][j].getType().equals(Type.EMPTY) ) board[i][j] = tiles.remove(0);
                    }
                }
                if (i == 8){
                    if(j == 4){
                        if( board[i][j].getType().equals(Type.EMPTY) ) board[i][j] = tiles.remove(0);
                    }
                }

                //refill all the spaces III
                if (i == 0){
                    if(j == 3){
                        if( board[i][j].getType().equals(Type.EMPTY) ) board[i][j] = tiles.remove(0);
                    }
                }
                if (i == 2){
                    if(j == 2 || j == 6){
                        if( board[i][j].getType().equals(Type.EMPTY) ) board[i][j] = tiles.remove(0);
                    }
                }
                if (i == 3){
                    if(j == 8){
                        if( board[i][j].getType().equals(Type.EMPTY) ) board[i][j] = tiles.remove(0);
                    }
                }
                if (i == 5){
                    if(j == 0){
                        if( board[i][j].getType().equals(Type.EMPTY) ) board[i][j] = tiles.remove(0);
                    }
                }
                if (i == 6){
                    if(j == 2 || j == 6){
                        if( board[i][j].getType().equals(Type.EMPTY) ) board[i][j] = tiles.remove(0);
                    }
                }
                if (i == 8){
                    if(j == 5){
                        if( board[i][j].getType().equals(Type.EMPTY) ) board[i][j] = tiles.remove(0);
                    }
                }

                //refill all the spaces II
                if (i == 1){
                    if(j == 3 || j == 4){
                        if( board[i][j].getType().equals(Type.EMPTY) ) board[i][j] = tiles.remove(0);
                    }
                }

                if (i == 2){
                    if(j == 3 || j == 4 || j == 5){
                        if( board[i][j].getType().equals(Type.EMPTY) )  board[i][j] = tiles.remove(0);
                    }
                }

                if (i == 3){
                    if(j == 2 ||j == 3 || j == 4 || j == 5|| j == 6|| j == 7) {
                        if( board[i][j].getType().equals(Type.EMPTY) )  board[i][j] = tiles.remove(0);
                    }
                }

                if (i == 4){
                    if(j == 1 ||j == 2 ||j == 3 || j == 4 || j == 5|| j == 6|| j == 7) {
                        if( board[i][j].getType().equals(Type.EMPTY) )  board[i][j] = tiles.remove(0);
                    }
                }

                if (i == 5) {
                    if (j == 1 || j == 2 || j == 3 || j == 4 || j == 5 || j == 6) {
                        if (board[i][j].getType().equals(Type.EMPTY)) board[i][j] = tiles.remove(0);
                    }
                }

                if (i == 6){
                    if(j == 3 || j == 4 || j == 5) {
                        if( board[i][j].getType().equals(Type.EMPTY) )  board[i][j] = tiles.remove(0);
                    }
                }

                if (i == 7){
                    if(j == 4 || j == 5) {
                        if( board[i][j].getType().equals(Type.EMPTY) )  board[i][j] = tiles.remove(0);
                    }
                }
            }
        }
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
