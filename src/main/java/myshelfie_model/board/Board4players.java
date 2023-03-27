package myshelfie_model.board;

import myshelfie_model.Tile;
import myshelfie_model.Type;

import java.util.List;

public class Board4players implements Board {
    private Tile[][] board;
    private Tile emptyTile;
    final int board_lenght=9;

    public Board4players() {
        this.board = new Tile[board_lenght][board_lenght];

        for (int row = 0; row < board_lenght; row++) {
            for (int col = 0; col < board_lenght; col++) {
                this.board[row][col] = null;
            }
        }
    }
    //TODO: inserire i nuovi metodi private aggiunti adesso nell'UML
    private boolean isOccupied(int row, int col){
        if(this.board[row][col]!= null && !this.board[row][col].getType().equals(Type.EMPTY) ) return true;
        else return false;
    };

    private boolean sideFree(int row,int col){

        if(!isOccupied(row+1,col)) return true;//Up
        else if(!isOccupied(row-1,col)) return true;//Down
        else if(!isOccupied(row,col+1)) return true;//Rx
        else if(!isOccupied(row,col-1)) return true;//Sx

        return false;
        };



    @Override
    public void refill(List<Tile> tiles) {
        for (int row = 0; row < board_lenght ; row++) {
            for (int col = 0; col < board_lenght; col++) {

                //refill all the spaces IV
                if (row == 0){
                    if(col == 4){
                        if( board[row][col].getType().equals(Type.EMPTY) ) this.board[row][col] = tiles.remove(0);
                    }
                }
                if (row == 1){
                    if(col == 5){
                        if( board[row][col].getType().equals(Type.EMPTY) ) this.board[row][col] = tiles.remove(0);
                    }
                }
                if (row == 3){
                    if(col == 1){
                        if( board[row][col].getType().equals(Type.EMPTY) ) this.board[row][col] = tiles.remove(0);
                    }
                }
                if (row == 4){
                    if(col == 0 || col == 8){
                        if( board[row][col].getType().equals(Type.EMPTY) ) this.board[row][col] = tiles.remove(0);
                    }
                }
                if (row == 5){
                    if(col == 7){
                        if( board[row][col].getType().equals(Type.EMPTY) ) this.board[row][col] = tiles.remove(0);
                    }
                }
                if (row == 7){
                    if(col == 3){
                        if( board[row][col].getType().equals(Type.EMPTY) ) this.board[row][col] = tiles.remove(0);
                    }
                }
                if (row == 8){
                    if(col == 4){
                        if( board[row][col].getType().equals(Type.EMPTY) ) this.board[row][col] = tiles.remove(0);
                    }
                }

                //refill all the spaces III
                if (row == 0){
                    if(col == 3){
                        if( board[row][col].getType().equals(Type.EMPTY) ) this.board[row][col] = tiles.remove(0);
                    }
                }
                if (row == 2){
                    if(col == 2 || col == 6){
                        if( board[row][col].getType().equals(Type.EMPTY) ) this.board[row][col] = tiles.remove(0);
                    }
                }
                if (row == 3){
                    if(col == 8){
                        if( board[row][col].getType().equals(Type.EMPTY) ) this.board[row][col] = tiles.remove(0);
                    }
                }
                if (row == 5){
                    if(col == 0){
                        if( board[row][col].getType().equals(Type.EMPTY) ) this.board[row][col] = tiles.remove(0);
                    }
                }
                if (row == 6){
                    if(col == 2 || col == 6){
                        if( board[row][col].getType().equals(Type.EMPTY) ) this.board[row][col] = tiles.remove(0);
                    }
                }
                if (row == 8){
                    if(col == 5){
                        if( board[row][col].getType().equals(Type.EMPTY) ) this.board[row][col] = tiles.remove(0);
                    }
                }

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


    @Override
    public boolean refillNeeded() {

        for (int i = 0; i < board_lenght; i++) {
            for (int j = 0; j < board_lenght; j++) {
                if(isOccupied(i,j)){
                    if(     (i > 0 && isOccupied(i-1,j) ) ||              // Down
                            (i < board_lenght-1 && isOccupied(i+1,j) ) || // Up
                            (j > 0 && isOccupied(i,j-1) ) ||               // Sx
                            (j < board_lenght-1 && isOccupied(i,j+1) ) ) { // Rx
                        // there is an adjacent tile, so they are not isolated
                        return false;
                    }
                }
            }
        }
        // All the tiles are isolated
        return true;
    }

    @Override
    public List remove(List<Tile> chosen)  {
        int flagDir = 0; //flagDir indica la direzione scelta (utile per ottimizzare la rimozione) 1:up , 2:down, 3:right, 4:left
        int flagStraightline = 0; //flagStraightline viene posto a 1, 2 o 3 in base a quante tessere allineate vengono trovate, il metodo giunge a corretta terminazione sse flagStraightline== chosen.size()
        int flagSideFree = 0; //flagSidefree viene posto a 1, 2 o 3 in base a quante tessere con lato libero vengono trovate, il metodo giunge a corretta terminazione sse flagStraightline== chosen.size()
        int firstRow=-1, firstCol=-1; //salvo la posizione del primo tile, poi con il flagDir e la size della list posso rimuovere i Tiles scelti dalla board.

        //cerco il primo Tile sulla board
        for (int row = 0; row < board_lenght ; row++) {
            for (int col = 0; col < board_lenght; col++) {
                if(this.board[row][col] == chosen.get(0)){
                    firstRow=row;
                    firstCol=col;
                    flagStraightline=1;

                    //verifico che la prima abbia un lato libero
                    if(sideFree(row,col)) flagSideFree=1;
                    else {
                        System.out.println("The first tile has no free side!");
                        break;
                    }

                    //verifico siano in linea retta
                    if (chosen.size()>1) {
                        if(      this.board[row + 1][col] == chosen.get(1)){//Up
                            flagDir=1;
                            flagStraightline=2;

                            //verifico che la seconda abbia un lato libero
                            if(sideFree(row+1,col)) flagSideFree=2;
                            else {
                                System.out.println("The second tile has no free side!");
                                break;
                            }

                            if(chosen.size()>2){
                                if(this.board[row+2][col] == chosen.get(2) ) {
                                    flagStraightline=3;
                                    //verifico che la terza abbia un lato libero
                                    if(sideFree(row+2,col)) flagSideFree=3;
                                    else {
                                        System.out.println("The third tile has no free side!");
                                        break;
                                    }
                                }
                            }
                        }
                        else if (this.board[row - 1][col] == chosen.get(1)) { //Down
                            flagDir=2;
                            flagStraightline=2;

                            //verifico che la seconda abbia un lato libero
                            if(sideFree(row-1,col)) flagSideFree=2;
                            else {
                                System.out.println("The second tile has no free side!");
                                break;
                            }
                            if(chosen.size()>2){
                                if(this.board[row-2][col] == chosen.get(2) ) {
                                    flagStraightline=3;
                                    //verifico che la terza abbia un lato libero
                                    if(sideFree(row-2,col)) flagSideFree=3;
                                    else {
                                        System.out.println("The third tile has no free side!");
                                        break;
                                    }
                                }
                            }

                        }
                        else if (this.board[row][col + 1] == chosen.get(1)) { //Rx
                            flagDir=3;
                            flagStraightline=2;

                            //verifico che la seconda abbia un lato libero
                            if(sideFree(row,col+1)) flagSideFree=2;
                            else {
                                System.out.println("The second tile has no free side!");
                                break;
                            }
                            if(chosen.size()>2){
                                if(this.board[row][col + 2] == chosen.get(2) ) {
                                    flagStraightline=3;
                                    //verifico che la terza abbia un lato libero
                                    if(sideFree(row,col + 2)) flagSideFree=3;
                                    else {
                                        System.out.println("The third tile has no free side!");
                                        break;
                                    }
                                }
                            }
                        }
                        else if (this.board[row][col - 1] == chosen.get(1)) { //Sx
                            flagDir=4;
                            flagStraightline=2;

                            //verifico che la seconda abbia un lato libero
                            if(sideFree(row,col-1)) flagSideFree=2;
                            else {
                                System.out.println("The second tile has no free side!");
                                break;
                            }
                            if(chosen.size()>2){
                                if(this.board[row][col - 2] == chosen.get(2) ) {
                                    flagStraightline=3;
                                    //verifico che la terza abbia un lato libero
                                    if(sideFree(row,col-2)) flagSideFree=3;
                                    else {
                                        System.out.println("The third tile has no free side!");
                                        break;
                                    }
                                }
                            }
                        }
                    }


                }

            }
        }


        //TODO: verifico i flag e quindi rimuovo le tessere dalla board
        if(flagStraightline==chosen.size()){
            System.out.println("Move accepted!");
            board[firstRow][firstCol].
        }

        return null;
    }

    //TODO: copiare l'interno dei metodi comuni anche nelle altre implementazioni
}
