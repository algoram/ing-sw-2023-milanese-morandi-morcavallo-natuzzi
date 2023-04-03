package myshelfie_model.board;
import myshelfie_model.Tile;
import java.util.Arrays;
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
    //private final Tile emptyTile= new Tile(Type.EMPTY);
    final int BOARD_LENGTH = 9;

    //TODO:inserire in UML attributo
    //indicates the position coordinates for the tile refill
    // 0: null, 2: two player, 3:three player, 4: four player
    final int[][] BOARD_PRE_SET = {
            {0,0,0,3,4,0,0,0,0},//row:0
            {0,0,0,2,2,4,0,0,0},//row:1
            {0,0,3,2,2,2,3,0,0},//row:2
            {0,4,2,2,2,2,2,2,3},//row:3
            {4,2,2,2,2,2,2,2,4},//row:4
            {3,2,2,2,2,2,2,4,0},//row:5
            {0,0,3,2,2,2,3,0,0},//row:6
            {0,0,0,4,2,2,0,0,0},//row:7
            {0,0,0,0,4,3,0,0,0} //row:8
    };

    public abstract void refill(List<Tile> tiles);

    private boolean isOccupied(int row, int col){
        return this.board[row][col] != null;

        // DEPRECATED
                //&& !this.board[row][col].getType().equals(Type.EMPTY);
    }

    private boolean sideFree(int row,int col){
        if(!isOccupied(row+1,col)) return true;//Up
        else if(!isOccupied(row-1,col)) return true;//Down
        else if(!isOccupied(row,col+1)) return true;//Rx
        else return !isOccupied(row, col - 1);//Sx
    }


    public boolean refillNeeded(){
        for (int i = 0; i < BOARD_LENGTH; i++) {
            for (int j = 0; j < BOARD_LENGTH; j++) {
                if(isOccupied(i,j)){
                    if(     (i > 0 && isOccupied(i-1,j) ) ||              // Down
                            (i < BOARD_LENGTH -1 && isOccupied(i+1,j) ) || // Up
                            (j > 0 && isOccupied(i,j-1) ) ||               // Sx
                            (j < BOARD_LENGTH -1 && isOccupied(i,j+1) ) ) { // Rx
                        // there is an adjacent tile, so they are not isolated
                        return false;
                    }
                }
            }
        }
        // All the tiles are isolated
        return true;

    }


    //TODO: aggiungere uml
    private boolean checkAdjacent(int a, int b, int c) {
            int[] nums = {a, b, c};
            Arrays.sort(nums);
            if (c==-1) return (nums[1] == nums[0] + 1);
        return (nums[1] == nums[0] + 1 && nums[2] == nums[1] + 1);
    }


    // TODO: Establish requirements for the position of the tiles to be removed: what about positions outside the board?

    //NEW VERSION
    //  TODO public List<Tile> remove(List<Position> chosen)
    public List remove(List<Tile> chosen)  {
        int flagStraightline = 0; //flagStraightline viene posto a 1, 2 o 3 in base a quante tessere allineate vengono trovate, il metodo giunge a corretta terminazione sse flagStraightline== chosen.size()
        int flagSideFree = 0; //flagSidefree viene posto a 1, 2 o 3 in base a quante tessere con lato libero vengono trovate, il metodo giunge a corretta terminazione sse flagStraightline== chosen.size()
        int flagAdjacent = 0; //flagAdjacent siano tutte




        //Check flagStraightLine
        for (int i = 0; i < chosen.size(); i++) {
            if( sideFree(chosen.get(i).getPosition().getRow(),chosen.get(i).getPosition().getColumn()) ) flagSideFree=1;
            else {
                System.out.println("The" + i+ "° Tile has no free side!\n No tile has been moved...");
                return null;
            }
        }


        //Check flagStraightline && flagAdjacent

        if(chosen.size()==1){
            flagAdjacent=1;
            flagStraightline=1;

        } else if( chosen.size()>1 ) {
            //Check flagStraightline on row
            if ( chosen.get(0).getPosition().getRow() == chosen.get(1).getPosition().getRow() ){

                flagStraightline = 1; //horizontal
                //Check flagAdjacent on column
                if ( checkAdjacent( chosen.get(0).getPosition().getColumn(), chosen.get(1).getPosition().getColumn(), -1) )
                    flagAdjacent = 1;
                else {
                    System.out.println("The Tiles do not are adjacent!\n No tile has been moved...");
                }

            }
            //Check flagStraightline on column
            else if (chosen.get(0).getPosition().getColumn() == chosen.get(1).getPosition().getColumn() && chosen.get(1).getPosition().getColumn() == chosen.get(2).getPosition().getColumn()){

                flagStraightline = 1; //vertical
                //Check flagAdjacent on row
                if ( checkAdjacent( chosen.get(0).getPosition().getRow(), chosen.get(1).getPosition().getRow(), -1) )
                    flagAdjacent = 1;
                else {
                    System.out.println("The Tiles do not are adjacent!\n No tile has been moved...");
                }
            }


        } else if( chosen.size()>2 ) {
            //Check flagStraightline on row
            if (chosen.get(0).getPosition().getRow() == chosen.get(1).getPosition().getRow() && chosen.get(1).getPosition().getRow() == chosen.get(2).getPosition().getRow()) {

                flagStraightline = 1; //horizontal
                //Check flagAdjacent on column
                if (checkAdjacent(chosen.get(0).getPosition().getColumn(), chosen.get(1).getPosition().getColumn(), chosen.get(2).getPosition().getColumn()))
                    flagAdjacent = 1;
                else {
                    flagAdjacent = 0;
                    System.out.println("The Tiles do not are adjacent!\n No tile has been moved...");
                }


            }
            //Check flagStraightline on column
            else if (chosen.get(0).getPosition().getColumn() == chosen.get(1).getPosition().getColumn() && chosen.get(1).getPosition().getColumn() == chosen.get(2).getPosition().getColumn())
                flagStraightline = 1; //vertical
            else {
                System.out.println("The Tiles do not form a straight line!\n No tile has been moved...");
                return null;
            }
        }

        //replace tile on the board
        if(flagStraightline==1 && flagSideFree==1 && flagAdjacent==1){
                //first tile
                // EMPTY DEPRECATED
                //board[chosen.get(0).getPosition().getRow()][chosen.get(0).getPosition().getColumn()]= emptyTile; // substitute the previous Tile with the emptyTile
                board[chosen.get(0).getPosition().getRow()][chosen.get(0).getPosition().getColumn()]= null;
                chosen.get(0).setPosition(-1,-1); //set a board outside position

                if(chosen.size()>1 ) {
                    //second tile
                    // EMPTY DEPRECATED
                    //board[chosen.get(1).getPosition().getRow()][chosen.get(1).getPosition().getColumn()]= emptyTile; // substitute the previous Tile with the emptyTile
                    board[chosen.get(1).getPosition().getRow()][chosen.get(1).getPosition().getColumn()]= null;
                    chosen.get(1).setPosition(-1,-1); //set a board outside position
                }
                if(chosen.size()>2 ) {
                    //third tile
                    // EMPTY DEPRECATED
                    //board[chosen.get(2).getPosition().getRow()][chosen.get(2).getPosition().getColumn()]= emptyTile; // substitute the previous Tile with the emptyTile
                    board[chosen.get(2).getPosition().getRow()][chosen.get(2).getPosition().getColumn()]= null;
                    chosen.get(2).setPosition(-1,-1); //set a board outside position
                }
                System.out.println("Move accepted!");
                return chosen;
            }
        return null;
    }

// _____________________________________________________________
//   TEST METHODS
    /**
     * This method is used to insert Tiles in a specific position of the board
     * @param i     the row of the board
     * @param j    the column of the board
     * @param tile the Tile to be inserted
     */
    public void setTileTest(int i, int j, Tile tile){
        this.board[i][j] = tile;
    }

    /**
     * This method is used set the entire board
     * @param tiles the matrix of Tiles to be inserted
     */
    public void setBoardTest(Tile[][] tiles) {
        this.board = tiles;
    }

}