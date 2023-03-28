package myshelfie_model.board;

import myshelfie_model.Tile;
import myshelfie_model.Type;

import java.util.List;

/* La board è descritta come una matrice 9x9 in cui le caselle vengono settate così:
 * non raggiungibili: null
 * vuote : EMPTY
 * coperte : getType()
 *
 *   inizializzo così (i numeri in romano verranno posti a -1 sse verrà richiesta l'implementazione con il numero player corrispondente, altrimenti null)
 *   i\j   0     1       2     3     4      5      6      7      8
 *    0  null | null | null | III  | IV  | null | null | null | null
 *    1  null | null | null | II   | II  | IV   | null | null | null
 *    2  null | null | III  | II   | II  | II   | III  | null | null
 *    3  null | IV   | II   | II   | II  | II   | II   | II   | III
 *    4  IV   | II   | II   | II   | II  | II   | II   | II   | IV
 *    5  III  | II   | II   | II   | II  | II   | II   | IV   | null
 *    6  null | null | III  | II   | II  | II   | III  | null | null
 *    7  null | null | null | IV   | II  | II   | null | null | null
 *    8  null | null | null | null | IV  | III  | null | null | null
 * */
//TODO: MODIFICARE UML CON ABSTRACT
public abstract class Board {
    //TODO: Modificare uml protected
    protected Tile[][] board;
    private Tile emptyTile= new Tile(Type.EMPTY);
    final int board_lenght=9;


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

    public abstract void refill(List<Tile> tiles);

    public boolean refillNeeded(){
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

    };

    // TODO: Trasformare chosen in una lista di coordinate
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

        //TODO: PER FRANCI leggi qui sotto, servirebbe costruire una tessera empty da inserire in questa fase del codice
        if(flagStraightline==chosen.size()){
            System.out.println("Move accepted!");
            board[firstRow][firstCol]= emptyTile; //metti empty
            if(flagDir==0){ //up
                if(chosen.size()>1 ) board[firstRow+1][firstCol]= emptyTile ;//metti empty
                if(chosen.size()>2 ) board[firstRow+2][firstCol]= emptyTile ;//metti empty
            } else if (flagDir==1) { //down
                if(chosen.size()>1 ) board[firstRow-1][firstCol]= emptyTile ;//metti empty
                if(chosen.size()>2 ) board[firstRow-2][firstCol]= emptyTile ;//metti empty

            } else if (flagDir==2) { //right
                if(chosen.size()>1 ) board[firstRow][firstCol+1]= emptyTile ;//metti empty
                if(chosen.size()>2 ) board[firstRow][firstCol+2]= emptyTile ;//metti empty
            } else if (flagDir==3) {
                if(chosen.size()>1 ) board[firstRow][firstCol-1]= emptyTile ;//metti empty
                if(chosen.size()>2 ) board[firstRow][firstCol-2]= emptyTile ;//metti empty

            }
        }

        return null;
    }
}
