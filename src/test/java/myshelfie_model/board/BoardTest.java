package myshelfie_model.board;

import myshelfie_model.Tile;
import myshelfie_model.Type;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class BoardTest {

    Board board = null;

    @Before
    public void setup(){
        int numberOfPlayers;
        // create a random number of players from 2 to 4  -> 2, 3, 4
        numberOfPlayers = (int) (Math.random() * 3) + 2;
        System.out.println("Number of players: " + numberOfPlayers);
        if(numberOfPlayers == 2){
            board = new Board2players();
        }
        else if(numberOfPlayers == 3){
            board = new Board3players();
        }
        else if(numberOfPlayers == 4){
            board = new Board4players();
        }

    }

    @After
    public void tearDown(){

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
        assertTrue(board.refillNeeded());
    }

    @Test
    public void refillNeeded_1Tile_shouldReturnTrue(){
        board.setTileTest(4,4, new Tile(Type.BOOKS));
        assertTrue(board.refillNeeded());
    }

    @Test
    public void refillNeeded_2AdjacentTiles_shouldReturnFalse(){
        board.setTileTest(4,4, new Tile(Type.BOOKS));
        board.setTileTest(4,5, new Tile(Type.CATS));
        assertFalse(board.refillNeeded());
    }

    @Test
    public void refillNeeded_2NonAdjacentTiles_shouldReturnTrue(){
        board.setTileTest(4,4, new Tile(Type.BOOKS));
        board.setTileTest(5,5, new Tile(Type.CATS));
        assertTrue(board.refillNeeded());
    }

    @Test
    public void refillNeeded_1TileAtBoundary_shouldReturnTrue(){
        board.setTileTest(0,4, new Tile(Type.BOOKS));
        assertTrue(board.refillNeeded());
    }

    @Test
    public void refillNeeded_2TileAtBoundary_shouldReturnFalse(){
        board.setTileTest(3,0, new Tile(Type.CATS));
        board.setTileTest(4,0, new Tile(Type.BOOKS));
        assertFalse(board.refillNeeded());
    }


    //__________________________________________________
    // TEST REFILL NEEDED

}
