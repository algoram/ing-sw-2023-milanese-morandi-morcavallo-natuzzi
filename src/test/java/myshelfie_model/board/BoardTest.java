package myshelfie_model.board;

import myshelfie_model.BoardPosition;
import myshelfie_model.Tile;
import myshelfie_model.Type;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;


//HOW TO manage TEST EXCEPTIONS
// WE WRITE THROWS WHEN WE DON'T EXPECT AN EXCEPTION TO BE THROWN
// WE WRITE CATCH WHEN WE EXPECT AN EXCEPTION TO BE THROWN




public class BoardTest {

    //create a board with random number of players
    Board randomboard = null;

    //4 players board
    Board board4 = null;
    Board board2 = null;
    Board board3 = null;

    @Before
    public void setup() {
        int numberOfPlayers;
        // create a random number of players from 2 to 4  -> 2, 3, 4
        numberOfPlayers = (int) (Math.random() * 3) + 2;
        System.out.println("Number of players: " + numberOfPlayers);
        if(numberOfPlayers == 2){
            randomboard = new Board2players();
        }
        else if(numberOfPlayers == 3){
            randomboard = new Board3players();
        }
        else if(numberOfPlayers == 4){
            randomboard = new Board4players();
        }

        board4 = new Board4players();
        board2 = new Board2players();
        board3 = new Board3players();

    }

    @After
    public void tearDown(){
        randomboard = null;
        board4 = null;
        board2 = null;
        board3 = null;
    }


//__________________________________________________
    // TEST REFILL NEEDED

    // The refillNeeded function requires that the board is compatible with the number of players

    //1. empty board should return true
    //2. board with 1 tile in the middle should return true
    //3. board with 2 adjacent tiles should return false
    //4. board with 2 not adjacent tiles should return true
    //5. board with 1 Tile at a boundary should return true
    //6. board with 2 Tiles at a boundary should return false

    @Test
    public void refillNeeded_emptyBoard_shouldReturnTrue(){
        assertTrue(randomboard.refillNeeded());
    }

    @Test
    public void refillNeeded_1Tile_shouldReturnTrue(){
        randomboard.setTileTest(4,4, new Tile(Type.BOOKS));
        assertTrue(randomboard.refillNeeded());
    }

    @Test
    public void refillNeeded_2AdjacentTiles_shouldReturnFalse(){
        randomboard.setTileTest(4,4, new Tile(Type.BOOKS));
        randomboard.setTileTest(4,5, new Tile(Type.CATS));
        assertFalse(randomboard.refillNeeded());
    }

    @Test
    public void refillNeeded_2NonAdjacentTiles_shouldReturnTrue(){
        randomboard.setTileTest(4,4, new Tile(Type.BOOKS));
        randomboard.setTileTest(5,5, new Tile(Type.CATS));
        assertTrue(randomboard.refillNeeded());
    }

    @Test
    public void refillNeeded_1TileAtBoundary_shouldReturnTrue(){
        randomboard.setTileTest(0,4, new Tile(Type.BOOKS));
        assertTrue(randomboard.refillNeeded());
    }

    @Test
    public void refillNeeded_2TileAtBoundary_shouldReturnFalse(){
        randomboard.setTileTest(3,0, new Tile(Type.CATS));
        randomboard.setTileTest(4,0, new Tile(Type.BOOKS));
        assertFalse(randomboard.refillNeeded());
    }


    //__________________________________________________
    // TEST REMOVE (refillNeeded should be false)
    //REMOVE IS NOT A PURE METHOD WE NEED TO CHECK THE BOARD AFTERWARDS

    // CHECKING THE ADJACENCY OF THE TILES
    // 1. Board with 2 tiles adjacent and list of 2 positions with each of them being the position of the tile to remove -> should return the List of Tiles removed
    // 2: Board with 2 tiles adjacent and a list of 3 positions with 2 of them being the position of the tiles and 1 being an adjacent position where there is no tile -> should return NULL
    // 3. Board with 3 tiles adjacent and a list of 3 positions with each of them being the position of the tile to remove -> should return the List of Tiles removed
    // 4. Board with 3 tiles adjacent but not aligned (they make an L) and a list of 3 positions with each of them being the position of the tile to remove -> should return NULL
    // 5. Board with 3 tiles in line but spaced out and a list of 3 positions with each of them being the position of the tile to remove -> should return NULL

    // CHECKING THE FREE SPACE OF EACH TILE REMOVED
    // 6. Board with a block 4x4 of tiles and a list of positions pointing to 2 adjacent TILES AT THE BORDER -> should return the tiles removed
    // 7. Board with a block 4x4 of tiles and a list of positions pointing to 2 adjacent TILES 1 at the border and 1 in the middle -> should return NULL
    // 8. Board with a block 4x4 of tiles and a list of positions pointing to 2 adjacent TILES IN THE MIDDLE -> should return NULL
    // 9. Board with a block 4x4 of tiles and a list of positions pointing to 2 not adjacent TILES AT THE BORDER -> should return NULL
    // 10. Board full of tiles and a list of positions pointing to 2 adjacent TILES at the boarder of the matrix -> should return the list of tiles removed
    // 11. Board full of tiles and a list of positions pointing to 2 adjacent TILES in the middle of the matrix -> should return NULL
    // 12. Board full of tiles and a list of positions pointing to 2 not adjacent (diagonally positioned near the boundary of the matrix) TILES at the boarder of the matrix -> should return NULL
    //13. list<Positions> == null -> null pointer exception
    //14. list<Positions> == empty -> illegal argument exception
    //15 INDEX OUT OF BOUND


//________________
// (out of bound) -> ""






    // 1. Board with 2 tiles adjacent and list of 2 positions with each of them being the position of the tile to remove -> should return the List of Tiles removed
    @Test
    public void remove_2AdjacentTiles_shouldReturnList() throws NullPointerException,IndexOutOfBoundsException,IllegalArgumentException {
        List<BoardPosition> positions = new ArrayList<>();
        List<Tile> removed;

        Tile tile1 = new Tile(Type.BOOKS);
        Tile tile2 = new Tile(Type.CATS);


        randomboard.setTileTest(4,4, tile1);
        randomboard.setTileTest(4,5, tile2);

        positions.add(new BoardPosition(4,4));
        positions.add(new BoardPosition(4,5));


        removed = randomboard.remove(positions);
        //the assertion calls the remove function  and checks if the list returned contains tile1 and tile2 and that the list is of size 2
        assertTrue(removed.contains(tile1));
        assertTrue(removed.contains(tile2));
        assertEquals(2, removed.size());

        //this assertion check that the board is empty
        assertTrue(Arrays.stream(randomboard.getBoard()).allMatch(row -> Arrays.stream(row).allMatch(Objects::isNull)));

    }

    // 2: Board with 2 tiles adjacent and a list of 3 positions with 2 of them being the position of the tiles and 1 being an adjacent position where there is no tile
    // -> position pointing to NULL
    // ILLEGAL ARGUMENT EXCEPTION TEST
    @Test
    public void remove_2AdjacentTiles_1NotAdjacent_shouldThrowIllegalArgumentException() throws NullPointerException,IndexOutOfBoundsException {
        List<BoardPosition> positions = new ArrayList<>();
        Tile[][] oldBoard;

        Tile tile1 = new Tile(Type.BOOKS);
        Tile tile2 = new Tile(Type.CATS);

        randomboard.setTileTest(4, 4, tile1);
        randomboard.setTileTest(4, 5, tile2);

        positions.add(new BoardPosition(4, 4));
        positions.add(new BoardPosition(4, 5));
        positions.add(new BoardPosition(4, 6));

        //the assertion calls the remove function  and checks if the list returned is null
        oldBoard = randomboard.getBoard();
        try{
            randomboard.remove(positions);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            assertEquals("A Position is empty!\n No tile has been moved...",e.getMessage());
            //this assertion checks that the Tile[][] old is in each position equal to the return of the randomBoard.getBoard() function
            assertTrue(Arrays.deepEquals(oldBoard, randomboard.getBoard()));
        }

        //todo in test 4 e 5 controlla la board non modificata
    }


    // 3. Board with 3 tiles adjacent and a list of 3 positions with each of them being the position of the tile to remove -> should return the List of Tiles removed
    @Test
    public void remove_3AdjacentTiles_shouldReturnList() throws NullPointerException,IndexOutOfBoundsException,IllegalArgumentException {
        List<BoardPosition> positions = new ArrayList<>();
        List<Tile> removed;

        Tile tile1 = new Tile(Type.BOOKS);
        Tile tile2 = new Tile(Type.FRAMES);
        Tile tile3 = new Tile(Type.CATS);

        randomboard.setTileTest(4,4, tile1);
        randomboard.setTileTest(4,5, tile2);
        randomboard.setTileTest(4,6, tile3);

        positions.add(new BoardPosition(4,4));
        positions.add(new BoardPosition(4,5));
        positions.add(new BoardPosition(4,6));

        removed = randomboard.remove(positions);
        //the assertion calls the remove function  and checks if the list returned contains tile1, tile2 and tile3 and that the list is of size 3
        assertTrue(removed.contains(tile1));
        assertTrue(removed.contains(tile2));
        assertTrue(removed.contains(tile3));
        assertEquals(3, removed.size());

        //this assertion check that the board is empty
        assertTrue(Arrays.stream(randomboard.getBoard()).allMatch(row -> Arrays.stream(row).allMatch(Objects::isNull)));

    }

    // 4. Board with 3 tiles not aligned (they make an L) and a list of 3 positions with each of them being the position of the tile to remove -> should return NULL
    @Test
    public void remove_3TilesAdjacent_NotAligned_MakingL_shouldThrowIllegalArgumentException() throws NullPointerException,IndexOutOfBoundsException {
        List<BoardPosition> positions = new ArrayList<>();
        Tile[][] oldBoard;

        Tile tile1 = new Tile(Type.BOOKS);
        Tile tile2 = new Tile(Type.FRAMES);
        Tile tile3 = new Tile(Type.CATS);

        randomboard.setTileTest(4,4, tile1);
        randomboard.setTileTest(4,5, tile2);
        randomboard.setTileTest(5,5, tile3);

        positions.add(new BoardPosition(4,4));
        positions.add(new BoardPosition(4,5));
        positions.add(new BoardPosition(5,5));

        oldBoard = randomboard.getBoard();
        try {
            randomboard.remove(positions);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            assertEquals("The Tiles do not form a straight line!\n No tile has been moved...",e.getMessage());
            //this assertion checks that the Tile[][] old is in each position equal to the return of the randomBoard.getBoard() function
            assertTrue(Arrays.deepEquals(oldBoard, randomboard.getBoard()));
        }

    }

    // 5. Board with 3 tiles in line but spaced out and a list of 3 positions with each of them being the position of the tile to remove -> should return NULL
    @Test
    public void remove_3TilesInLine_SpacedOut_shouldThrowIllegalArgumentException() throws NullPointerException,IndexOutOfBoundsException {
        List<BoardPosition> positions = new ArrayList<>();
        Tile[][] oldBoard;

        Tile tile1 = new Tile(Type.BOOKS);
        Tile tile2 = new Tile(Type.FRAMES);
        Tile tile3 = new Tile(Type.CATS);

        randomboard.setTileTest(4,4, tile1);
        randomboard.setTileTest(4,5, tile2);
        randomboard.setTileTest(4,7, tile3);

        positions.add(new BoardPosition(4,4));
        positions.add(new BoardPosition(4,5));
        positions.add(new BoardPosition(4,7));

        oldBoard = randomboard.getBoard();
        try {
            randomboard.remove(positions);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            assertEquals("The Tiles are not adjacent!\n No tile has been moved...",e.getMessage());
            //this assertion checks that the Tile[][] old is in each position equal to the return of the randomBoard.getBoard() function
            assertTrue(Arrays.deepEquals(oldBoard, randomboard.getBoard()));
        }
    }

    // 6. Board with a block 4x4 of tiles and a list of positions pointing to 2 adjacent TILES AT THE BORDER -> should return the tiles removed
    @Test
    public void remove_2AdjacentTilesAtBorder_shouldReturnList(){
        List<BoardPosition> positions = new ArrayList<>();
        List<Tile> removed;
        Tile[][] oldBoard;

        Tile tile1 = new Tile(Type.PLANTS);
        Tile tile2 = new Tile(Type.CATS);

        //the board is set up with a 4x4 block of tiles
        //the tiles to remove are set up at the border of the block
        randomboard.setTileTest(2,3, tile1);
        randomboard.setTileTest(2,4, tile2);

        randomboard.setTileTest(2,5, new Tile(Type.getRandomType()));
        randomboard.setTileTest(2,6, new Tile(Type.getRandomType()));

        randomboard.setTileTest(3,3, new Tile(Type.getRandomType()));
        randomboard.setTileTest(3,4, new Tile(Type.getRandomType()));
        randomboard.setTileTest(3,5, new Tile(Type.getRandomType()));
        randomboard.setTileTest(3,6, new Tile(Type.getRandomType()));

        randomboard.setTileTest(4,3, new Tile(Type.getRandomType()));
        randomboard.setTileTest(4,4, new Tile(Type.getRandomType()));
        randomboard.setTileTest(4,5, new Tile(Type.getRandomType()));
        randomboard.setTileTest(4,6, new Tile(Type.getRandomType()));

        randomboard.setTileTest(5,3, new Tile(Type.getRandomType()));
        randomboard.setTileTest(5,4, new Tile(Type.getRandomType()));
        randomboard.setTileTest(5,5, new Tile(Type.getRandomType()));
        randomboard.setTileTest(5,6, new Tile(Type.getRandomType()));


        positions.add(new BoardPosition(2,3));
        positions.add(new BoardPosition(2,4));

        //save the state of the board before the remove function is called
        oldBoard = randomboard.getBoard();

        removed = randomboard.remove(positions);

        //modified board should be equal to the old board except for the tiles removed so let's put those positions to null in the old board
        oldBoard[2][3] = null;
        oldBoard[2][4] = null;

        //the assertion calls the remove function  and checks if the list returned contains tile1 and tile2 and that the list is of size 2

        assertTrue(removed.contains(tile1));
        assertTrue(removed.contains(tile2));
        assertEquals(2, removed.size());

        //this assertion checks that the Tile[][] old is in each position equal to the return of the randomBoard.getBoard() function
        assertTrue(Arrays.deepEquals(oldBoard, randomboard.getBoard()));

    }

    // 7. Board with a block 4x4 of tiles and a list of positions pointing to 2 adjacent TILES 1 at the border and 1 in the middle -> should return NULL
    @Test
    public void remove_2AdjacentTiles1AtBorder1InMiddle_shouldThrowIllegalArgumentException() throws NullPointerException,IndexOutOfBoundsException {
        List<BoardPosition> positions = new ArrayList<>();
        Tile[][] oldBoard;

        Tile tile1 = new Tile(Type.getRandomType());
        Tile tile2 = new Tile(Type.getRandomType());

        //the board is set up with a 4x4 block of tiles
        randomboard.setTileTest(2,3, new Tile(Type.getRandomType()));
        //the tile to remove at the border is set up
        randomboard.setTileTest(2,4, tile1);
        randomboard.setTileTest(2,5, new Tile(Type.getRandomType()));
        randomboard.setTileTest(2,6, new Tile(Type.getRandomType()));

        randomboard.setTileTest(3,3, new Tile(Type.getRandomType()));
        //the tile to remove in the middle is set up
        randomboard.setTileTest(3,4, tile2);
        randomboard.setTileTest(3,5, new Tile(Type.getRandomType()));
        randomboard.setTileTest(3,6, new Tile(Type.getRandomType()));

        randomboard.setTileTest(4,3, new Tile(Type.getRandomType()));
        randomboard.setTileTest(4,4, new Tile(Type.getRandomType()));
        randomboard.setTileTest(4,5, new Tile(Type.getRandomType()));
        randomboard.setTileTest(4,6, new Tile(Type.getRandomType()));

        randomboard.setTileTest(5,3, new Tile(Type.getRandomType()));
        randomboard.setTileTest(5,4, new Tile(Type.getRandomType()));
        randomboard.setTileTest(5,5, new Tile(Type.getRandomType()));
        randomboard.setTileTest(5,6, new Tile(Type.getRandomType()));


        positions.add(new BoardPosition(2,4));
        positions.add(new BoardPosition(3,4));

        //save the state of the board before the remove function is called
        oldBoard = randomboard.getBoard();
        try {
            randomboard.remove(positions);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            assertEquals("A Tile has no free side!\n No tile has been moved...", e.getMessage());
            assertTrue(Arrays.deepEquals(oldBoard, randomboard.getBoard()));
        }
    }

    // 8. Board with a block 4x4 of tiles and a list of positions pointing to 2 adjacent TILES IN THE MIDDLE
    // should throw IllegalArgumentException and the board should be the same as before the remove function was called
    @Test
    public void remove_2AdjacentTilesInMiddle_shouldReturnNull()throws NullPointerException,IndexOutOfBoundsException {
        List<BoardPosition> positions = new ArrayList<>();
        Tile[][] oldBoard;

        Tile tile1 = new Tile(Type.getRandomType());
        Tile tile2 = new Tile(Type.getRandomType());

        //the board is set up with a 4x4 block of tiles
        randomboard.setTileTest(2,3, new Tile(Type.getRandomType()));
        randomboard.setTileTest(2,4, new Tile(Type.getRandomType()));
        randomboard.setTileTest(2,5, new Tile(Type.getRandomType()));
        randomboard.setTileTest(2,6, new Tile(Type.getRandomType()));

        randomboard.setTileTest(3,3, new Tile(Type.getRandomType()));
        //the tile to remove in the middle is set up
        randomboard.setTileTest(3,4, tile1);
        //the tile to remove in the middle is set up
        randomboard.setTileTest(3,5, tile2);
        randomboard.setTileTest(3,6, new Tile(Type.getRandomType()));

        randomboard.setTileTest(4,3, new Tile(Type.getRandomType()));
        randomboard.setTileTest(4,4, new Tile(Type.getRandomType()));
        randomboard.setTileTest(4,5, new Tile(Type.getRandomType()));
        randomboard.setTileTest(4,6, new Tile(Type.getRandomType()));

        randomboard.setTileTest(5,3, new Tile(Type.getRandomType()));
        randomboard.setTileTest(5,4, new Tile(Type.getRandomType()));
        randomboard.setTileTest(5,5, new Tile(Type.getRandomType()));
        randomboard.setTileTest(5,6, new Tile(Type.getRandomType()));


        positions.add(new BoardPosition(3,4));
        positions.add(new BoardPosition(3,5));

        //save the state of the board before the remove function is called
        oldBoard = randomboard.getBoard();
        try {
            randomboard.remove(positions);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            assertEquals("A Tile has no free side!\n No tile has been moved...", e.getMessage());
            assertTrue(Arrays.deepEquals(oldBoard, randomboard.getBoard()));
        }
    }

    // 9. Board with a block 4x4 of tiles and a list of positions pointing to 2 not adjacent TILES AT THE BORDER -> should return NULL
    @Test
    public void remove_2NotAdjacentTilesAtBorder_shouldThrowIllegalArgumentException() throws NullPointerException,IndexOutOfBoundsException {
        List<BoardPosition> positions = new ArrayList<>();
        Tile[][] oldBoard;

        Tile tile1 = new Tile(Type.getRandomType());
        Tile tile2 = new Tile(Type.getRandomType());

        //the board is set up with a 4x4 block of tiles
        board4.setTileTest(2,3, new Tile(Type.getRandomType()));
        //the tile to remove at the border is set up
        board4.setTileTest(2,4, tile1);
        board4.setTileTest(2,5, new Tile(Type.getRandomType()));
        board4.setTileTest(2,6, new Tile(Type.getRandomType()));

        board4.setTileTest(3,3, new Tile(Type.getRandomType()));
        board4.setTileTest(3,4, new Tile(Type.getRandomType()));
        board4.setTileTest(3,5, new Tile(Type.getRandomType()));
        board4.setTileTest(3,6, new Tile(Type.getRandomType()));

        board4.setTileTest(4,3, new Tile(Type.getRandomType()));
        board4.setTileTest(4,4, new Tile(Type.getRandomType()));
        board4.setTileTest(4,5, new Tile(Type.getRandomType()));
        board4.setTileTest(4,6, new Tile(Type.getRandomType()));

        board4.setTileTest(5,3, new Tile(Type.getRandomType()));
        //the tile to remove at the border is set up
        board4.setTileTest(5,4, tile2);
        board4.setTileTest(5,5, new Tile(Type.getRandomType()));
        board4.setTileTest(5,6, new Tile(Type.getRandomType()));


        positions.add(new BoardPosition(2,4));
        positions.add(new BoardPosition(5,4));

        //save the state of the board before the remove function is called
        oldBoard = board4.getBoard();
        try {
            board4.remove(positions);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            assertEquals("The Tiles are not adjacent!\n No tile has been moved...", e.getMessage());
            assertTrue(Arrays.deepEquals(oldBoard, board4.getBoard()));
        }
    }

    // TODO: test after refill testing TO COMPLETE
    // 10. Board4 full of tiles and a list of positions pointing to 2 adjacent TILES at the boarder of the matrix -> should return the list of tiles removed
    @Test
    public void remove_2AdjacentTilesAtBorder_RefilledBoard_shouldReturnList(){
        List<BoardPosition> positions = new ArrayList<>();
        List<Tile> removed;
        List<Tile> tiles = new ArrayList<>();
        Tile[][] oldBoard;


        //create the tiles
        for (int i=0; i < 47; i++) {
            tiles.add(new Tile(Type.getRandomType()));
        }

        board4.refill(tiles);

        positions.add(new BoardPosition(0,4));
        positions.add(new BoardPosition(0,5));

        //save the content of the board before the remove
        oldBoard = board4.getBoard();
        removed = board4.remove(positions);

        //the assertion calls the remove function  and checks if the list returned is null
        assertTrue(removed.contains(oldBoard[0][4]));
        assertTrue(removed.contains(oldBoard[0][5]));
        assertEquals(2, removed.size());

        //set the tiles to null in the old board
        oldBoard[0][4] = null;
        oldBoard[0][5] = null;
        assertTrue(Arrays.deepEquals(oldBoard, board4.getBoard()));
    }

    // TODO : test after refill testing TO COMPLETE
    // 11. Board full of tiles and a list of positions pointing to 2 adjacent TILES in the middle of the matrix -> should return NULL

    // TODO : test after refill testing TO COMPLETE
    // 12. Board full of tiles and a list of positions pointing to 2 not adjacent (diagonally positioned near the boundary of the matrix) TILES at the boarder of the matrix -> should return NULL


    // 13. chosen is null -> should throw NullPointerException
    @Test
    public void remove_chosenIsNull_shouldThrowNullPointerException() throws IllegalArgumentException, IndexOutOfBoundsException {
        List<BoardPosition> positions = null;
        Tile[][] oldBoard;

        //random filling of the board
        randomboard.setTileTest(5,5, new Tile(Type.getRandomType()));
        randomboard.setTileTest(5,6, new Tile(Type.getRandomType()));
        randomboard.setTileTest(5,7, new Tile(Type.getRandomType()));

        oldBoard = randomboard.getBoard();
        try {
            randomboard.remove(null);
            fail("NullPointerException expected");
        } catch (NullPointerException e) {
            assertEquals("The list of tiles to be removed is null!\n No tile has been moved...", e.getMessage());
            assertTrue(Arrays.deepEquals(oldBoard, randomboard.getBoard()));
        }
    }



   // 14 CHOSEN SIZE ZERO
    @Test
    public void remove_chosenSizeZero_shouldThrowIllegalArgumentException() throws NullPointerException , IndexOutOfBoundsException {
        List<BoardPosition> positions = new ArrayList<>();
        Tile[][] oldBoard;

        //random filling of the board
        randomboard.setTileTest(5,5, new Tile(Type.getRandomType()));
        randomboard.setTileTest(5,6, new Tile(Type.getRandomType()));
        randomboard.setTileTest(5,7, new Tile(Type.getRandomType()));

        oldBoard = randomboard.getBoard();
        try {
            randomboard.remove(positions);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            assertEquals("The number of tiles to be removed is not valid!\n No tile has been moved...", e.getMessage());
            assertTrue(Arrays.deepEquals(oldBoard, randomboard.getBoard()));
        }
    }

    //15 INDEX OUT OF BOUND
    // TODO TO FIX
    @Test
    public void remove_indexOutOfBound_shouldThrowIndexOutOfBoundsException() throws NullPointerException, IllegalArgumentException {
        List<BoardPosition> positions = new ArrayList<>();
        Tile[][] oldBoard;

        //random filling of the board
        randomboard.setTileTest(5,5, new Tile(Type.getRandomType()));
        randomboard.setTileTest(5,6, new Tile(Type.getRandomType()));
        randomboard.setTileTest(5,7, new Tile(Type.getRandomType()));

        positions.add(new BoardPosition(-1,5));
        positions.add(new BoardPosition(5,6));
        positions.add(new BoardPosition(5,7));

        oldBoard = randomboard.getBoard();
        try {
            randomboard.remove(positions);
            fail("IndexOutOfBoundsException expected");
        } catch (IndexOutOfBoundsException e) {
            assertEquals("A Position is not a valid!\n No tile has been moved...", e.getMessage());
            assertTrue(Arrays.deepEquals(oldBoard, randomboard.getBoard()));
        }
    }





}
