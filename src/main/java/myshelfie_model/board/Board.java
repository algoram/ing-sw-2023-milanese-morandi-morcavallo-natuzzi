package myshelfie_model.board;
import myshelfie_model.Position;
import myshelfie_model.Tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



/**
 *  La board è descritta come una matrice 9x9 in cui le caselle vengono settate così:
 * non raggiungibili: null
 * vuote : EMPTY
 * coperte : getType()
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
            {0,0,0,0,4,3,0,0,0},//row:0
            {0,0,0,4,2,2,0,0,0},//row:1
            {0,0,3,2,2,2,3,0,0},//row:2
            {3,2,2,2,2,2,2,4,0},//row:3
            {4,2,2,2,2,2,2,2,4},//row:4
            {0,4,2,2,2,2,2,2,3},//row:5
            {0,0,3,2,2,2,3,0,0},//row:6
            {0,0,0,2,2,4,0,0,0},//row:7
            {0,0,0,3,4,0,0,0,0} //row:8
    };

    /**
     * refill all the empty tiles with the tiles in the list (bag)
     * @param tiles: the list of tiles to refill the board (bag)
     * */
    public abstract void refill(List<Tile> tiles);
    /**
     * @param row: row position to check
     * @param col: col position to check
     * @return: true if the position is occupied, false otherwise
     * */
    private boolean isOccupied(int row,int col){
        if (row < 0 || row >= BOARD_LENGTH || col < 0 || col >= BOARD_LENGTH){
            return false;
        }
        return this.board[row][col] != null;
    }

    /**
     * Checks if the position indicated by (row, col) can be occupied by a tile
     * in a game with numberOfPlayers players
     * @param numberOfPlayers the number of players playing the game
     * @param row position to check
     * @param col position to check
     * @return whether the cell can be occupied by a tile
     */
    public boolean isLegalPosition(int numberOfPlayers, int row, int col) {
        if (row < 0 || row >= BOARD_PRE_SET.length || col < 0 || col >= BOARD_PRE_SET[0].length) {
            return false; // invalid position
        }

        if (BOARD_PRE_SET[row][col] == 0) {
            return false; // this cell can never be occupied
        }

        if (numberOfPlayers < BOARD_PRE_SET[row][col]) {
            return false; // not enough players to use this cell
        }

        return true;
    }

    /**
     * Returns all the tiles that could be picked by a player
     * @return all the tiles that could be picked by a player
     */
    public ArrayList<Position> getAvailableTiles(int numberOfPlayers) {
        ArrayList<Position> positions = new ArrayList<>();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (isLegalPosition(numberOfPlayers, i, j) && sideFree(i, j)) {
                    positions.add(new Position(i, j));
                }
            }
        }

        return positions;
    }

    private boolean sideFree(int row,int col){
        if(!isOccupied(row+1,col)) return true;//Up
        else if(!isOccupied(row-1,col)) return true;//Down
        else if(!isOccupied(row,col+1)) return true;//Rx
        else return !isOccupied(row, col - 1);//Sx
    }

    /**
    * @return: true if the board contains only isolated tiles, false otherwise
    * */
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

    /**
    * @param a: first number corresponds to row/column of the first tile
    * @param b: second number corresponds to row/column of the second tile
    * @param c: third number corresponds to row/column of the third tile, -1 if the third tile is not present
    * @return: true if the 3 number are adjacent and aligned, false otherwise
    * */
    private boolean checkAdjacent(int a, int b, int c){
            int[] nums = {a, b, c};
            Arrays.sort(nums);
            if (c==-1) return (nums[2] == nums[1] + 1);
        return (nums[1] == nums[0] + 1 && nums[2] == nums[1] + 1);
    }


    /**
     * @param pos: the position to check
     * @return: true if the position is valid, false otherwise
     * */
    protected abstract boolean CheckBoardPosition(Position pos);

    // TODO: Establish requirements for the position of the tiles to be removed: what about positions outside the board?

    //NEW VERSION
    //  TODO public List<Tile> remove(List<Position> chosen)
    /**
     * @param chosen: list of positions of the tiles to be removed
     * @return: list of tiles removed, if no tiles are removed, the list is empty
     * @throws:  if the list is empty, if the list contains more than 3 tiles, if the list contains tiles with no free side,  if the tiles are not adjacent or aligned or if the tiles are outside the board
     *
     * */
    public List<Tile> remove(List<Position> chosen) throws NullPointerException,IndexOutOfBoundsException,IllegalArgumentException {
        List<Tile> removed = new ArrayList<>();
        int flagSideFree = 0; //flagSidefree is set to 1, 2 or 3 based on how many tiles with free side are found, the method reaches the correct termination if flagStraightline== chosen.size()
        int flagStraightline = 0; //flagStraightline is set to 1, 2 or 3 based on how many aligned tiles are found, the method reaches the correct termination if flagStraightline== chosen.size()
        int flagAdjacent = 0; //flagAdjacent is set to 1 if the tiles are adjacent, 0 otherwise

        //Check chosen.size()
        if (chosen == null) throw new NullPointerException("The list of tiles to be removed is null!\n No tile has been moved...");
        if(chosen.size()>3 || chosen.size()<1) throw new IllegalArgumentException("The number of tiles to be removed is not valid!\n No tile has been moved...");

        //Check ValidBoardPosition
        for (Position boardPosition : chosen){
            if (boardPosition.getRow() < 0 || boardPosition.getRow() > 8 || boardPosition.getColumn() < 0 || boardPosition.getColumn() > 8) {
                throw new IndexOutOfBoundsException("A Position is out of bounds!\n No tile has been moved...");
            }
        }
        for (Position boardPosition : chosen) {
            if (!CheckBoardPosition(boardPosition)) {
                throw new IllegalArgumentException("A Position is not a valid!\n No tile has been moved...");



            }
            if (this.board[boardPosition.getRow()][boardPosition.getColumn()] == null) {
                throw new IllegalArgumentException("A Position is empty!\n No tile has been moved...");
            }
        }


        //Check flagSideFree
        for (Position boardPosition : chosen) {
            if (sideFree(boardPosition.getRow(), boardPosition.getColumn())) flagSideFree = 1;
            else {
                throw new IllegalArgumentException("A Tile has no free side!\n No tile has been moved...");
            }
        }

        //Check flagStraightline && flagAdjacent
        if(chosen.size()==1){
            flagAdjacent=1;
            flagStraightline=1;

        }
        else if(chosen.size()==2) {
            //Check flagStraightline on row
            if ( chosen.get(0).getRow() == chosen.get(1).getRow() ){

                flagStraightline = 1; //horizontal
                //Check flagAdjacent on column
                if ( checkAdjacent( chosen.get(0).getColumn(), chosen.get(1).getColumn(), -1) )
                    flagAdjacent = 1;
                else throw new IllegalArgumentException("The Tiles are not adjacent!\n No tile has been moved...");
            }
            //Check flagStraightline on column
            else if ( chosen.get(0).getColumn() == chosen.get(1).getColumn() ){
                flagStraightline = 1; //vertical

                //Check flagAdjacent on row
                if ( checkAdjacent( chosen.get(0).getRow(), chosen.get(1).getRow(), -1) ) flagAdjacent = 1;
                else throw new IllegalArgumentException("The Tiles are not adjacent!\n No tile has been moved...");
            }
        }
        else if(chosen.size()==3) {
            //Check flagStraightline on row
            if (chosen.get(0).getRow() == chosen.get(1).getRow() && chosen.get(1).getRow() == chosen.get(2).getRow()) {
                flagStraightline = 1; //horizontal

                //Check flagAdjacent on column
                if (checkAdjacent(chosen.get(0).getColumn(), chosen.get(1).getColumn(), chosen.get(2).getColumn()))
                    flagAdjacent = 1;
                else {
                    throw new IllegalArgumentException("The Tiles are not adjacent!\n No tile has been moved...");
                }


            }
            //Check flagStraightline on column
            else if (chosen.get(0).getColumn() == chosen.get(1).getColumn() && chosen.get(1).getColumn() == chosen.get(2).getColumn())
                flagStraightline = 1; //vertical
            else {
                throw new IllegalArgumentException("The Tiles do not form a straight line!\n No tile has been moved...");
            }
        }

        //replace tile on the board
        if(flagStraightline==1 && flagSideFree==1 && flagAdjacent==1){
                //first tile
                removed.add(board[chosen.get(0).getRow()][chosen.get(0).getColumn()]);
                board[chosen.get(0).getRow()][chosen.get(0).getColumn()]= null;

                if(chosen.size()>1 ) {
                    //second tile
                    removed.add(board[chosen.get(1).getRow()][chosen.get(1).getColumn()]);
                    board[chosen.get(1).getRow()][chosen.get(1).getColumn()]= null;
                }
                if(chosen.size()>2 ) {
                    //third tile
                    removed.add(board[chosen.get(2).getRow()][chosen.get(2).getColumn()]);
                    board[chosen.get(2).getRow()][chosen.get(2).getColumn()]= null;
                }
                System.out.println("Move accepted!");
                return removed;
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
     * this method is used to check the state of the board after a remove in Tests
     * @return a copy of the board
     */
    public Tile[][] getBoard(){
        Tile[][] boardCopy = new Tile[9][9];
        for(int i=0; i<9; i++){
            System.arraycopy(this.board[i], 0, boardCopy[i], 0, 9);
        }
        return boardCopy;
    }

}