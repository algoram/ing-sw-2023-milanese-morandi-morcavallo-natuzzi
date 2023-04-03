package myshelfie_model.board;

import myshelfie_model.Position;
import myshelfie_model.Tile;
import myshelfie_model.Type;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BoardTest {

    //create a board with random number of players
    Board randomboard = null;

    //4 players board
    Board board4 = null;

    @Before
    public void setup(){
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


    // 1. Board with 2 tiles adjacent and list of 2 positions with each of them being the position of the tile to remove -> should return the List of Tiles removed
    @Test
    public void remove_2AdjacentTiles_shouldReturnList(){
        List<Position> positions = new ArrayList<>();

        Tile tile1 = new Tile(Type.BOOKS);
        Tile tile2 = new Tile(Type.CATS);


        randomboard.setTileTest(4,4, tile1);
        randomboard.setTileTest(4,5, tile2);

        positions.add(new Position(4,4));
        positions.add(new Position(4,5));

        //the assertion calls the remove function  and checks if the list returned contains tile1 and tile2 and that the list is of size 2
        assertTrue(randomboard.remove(positions).contains(tile1));
        assertTrue(randomboard.remove(positions).contains(tile2));
        assertTrue(randomboard.remove(positions).size() == 2);

    }

    // 2: Board with 2 tiles adjacent and a list of 3 positions with 2 of them being the position of the tiles and 1 being an adjacent position where there is no tile -> should return NULL
    @Test
    public void remove_2AdjacentTiles_1NotAdjacent_shouldReturnNull() {
        List<Position> positions = new ArrayList<>();

        Tile tile1 = new Tile(Type.BOOKS);
        Tile tile2 = new Tile(Type.CATS);

        randomboard.setTileTest(4, 4, tile1);
        randomboard.setTileTest(4, 5, tile2);

        positions.add(new Position(4, 4));
        positions.add(new Position(4, 5));
        positions.add(new Position(4, 6));

        //the assertion calls the remove function  and checks if the list returned is null
        assertNull(randomboard.remove(positions));
    }


    // 3. Board with 3 tiles adjacent and a list of 3 positions with each of them being the position of the tile to remove -> should return the List of Tiles removed
    @Test
    public void remove_3AdjacentTiles_shouldReturnList(){
        List<Position> positions = new ArrayList<>();

        Tile tile1 = new Tile(Type.BOOKS);
        Tile tile2 = new Tile(Type.FRAMES);
        Tile tile3 = new Tile(Type.CATS);

        randomboard.setTileTest(4,4, tile1);
        randomboard.setTileTest(4,5, tile2);
        randomboard.setTileTest(4,6, tile3);

        positions.add(new Position(4,4));
        positions.add(new Position(4,5));
        positions.add(new Position(4,6));

        //the assertion calls the remove function  and checks if the list returned contains tile1, tile2 and tile3 and that the list is of size 3
        assertTrue(randomboard.remove(positions).contains(tile1));
        assertTrue(randomboard.remove(positions).contains(tile2));
        assertTrue(randomboard.remove(positions).contains(tile3));
        assertTrue(randomboard.remove(positions).size() == 3);
    }

    // 4. Board with 3 tiles adjacent but not aligned (they make an L) and a list of 3 positions with each of them being the position of the tile to remove -> should return NULL
    @Test
    public void remove_3TilesAdjacent_NotAligned_MakingL_shouldReturnNull(){
        List<Position> positions = new ArrayList<>();

        Tile tile1 = new Tile(Type.BOOKS);
        Tile tile2 = new Tile(Type.FRAMES);
        Tile tile3 = new Tile(Type.CATS);

        randomboard.setTileTest(4,4, tile1);
        randomboard.setTileTest(4,5, tile2);
        randomboard.setTileTest(5,5, tile3);

        positions.add(new Position(4,4));
        positions.add(new Position(4,5));
        positions.add(new Position(5,5));

        //the assertion calls the remove function  and checks if the list returned is null
        assertNull(randomboard.remove(positions));
    }

    // 5. Board with 3 tiles in line but spaced out and a list of 3 positions with each of them being the position of the tile to remove -> should return NULL
    @Test
    public void remove_3TilesInLine_SpacedOut_shouldReturnNull(){
        List<Position> positions = new ArrayList<>();

        Tile tile1 = new Tile(Type.BOOKS);
        Tile tile2 = new Tile(Type.FRAMES);
        Tile tile3 = new Tile(Type.CATS);

        randomboard.setTileTest(4,4, tile1);
        randomboard.setTileTest(4,5, tile2);
        randomboard.setTileTest(4,7, tile3);

        positions.add(new Position(4,4));
        positions.add(new Position(4,5));
        positions.add(new Position(4,7));

        //the assertion calls the remove function  and checks if the list returned is null
        assertNull(randomboard.remove(positions));
    }

    // 6. Board with a block 4x4 of tiles and a list of positions pointing to 2 adjacent TILES AT THE BORDER -> should return the tiles removed
    @Test
    public void remove_2AdjacentTilesAtBorder_shouldReturnList(){
        List<Position> positions = new ArrayList<>();

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


        positions.add(new Position(2,3));
        positions.add(new Position(2,4));

        //the assertion calls the remove function  and checks if the list returned contains tile1 and tile2 and that the list is of size 2
        assertTrue(randomboard.remove(positions).contains(tile1));
        assertTrue(randomboard.remove(positions).contains(tile2));
        assertTrue(randomboard.remove(positions).size() == 2);
    }

    // 7. Board with a block 4x4 of tiles and a list of positions pointing to 2 adjacent TILES 1 at the border and 1 in the middle -> should return NULL
    @Test
    public void remove_2AdjacentTiles1AtBorder1InMiddle_shouldReturnNull(){
        List<Position> positions = new ArrayList<>();

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


        positions.add(new Position(2,4));
        positions.add(new Position(3,4));

        //the assertion calls the remove function  and checks if the list returned is null
        assertNull(randomboard.remove(positions));
    }

    // 8. Board with a block 4x4 of tiles and a list of positions pointing to 2 adjacent TILES IN THE MIDDLE -> should return NULL
    @Test
    public void remove_2AdjacentTilesInMiddle_shouldReturnNull(){
        List<Position> positions = new ArrayList<>();

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


        positions.add(new Position(3,4));
        positions.add(new Position(3,5));

        //the assertion calls the remove function  and checks if the list returned is null
        assertNull(randomboard.remove(positions));
    }

    // 9. Board with a block 4x4 of tiles and a list of positions pointing to 2 not adjacent TILES AT THE BORDER -> should return NULL
    @Test
    public void remove_2NotAdjacentTilesAtBorder_shouldReturnNull(){
        List<Position> positions = new ArrayList<>();

        Tile tile1 = new Tile(Type.getRandomType());
        Tile tile2 = new Tile(Type.getRandomType());

        //the board is set up with a 4x4 block of tiles
        randomboard.setTileTest(2,3, new Tile(Type.getRandomType()));
        //the tile to remove at the border is set up
        randomboard.setTileTest(2,4, tile1);
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
        //the tile to remove at the border is set up
        randomboard.setTileTest(5,4, tile2);
        randomboard.setTileTest(5,5, new Tile(Type.getRandomType()));
        randomboard.setTileTest(5,6, new Tile(Type.getRandomType()));


        positions.add(new Position(2,4));
        positions.add(new Position(5,4));

        //the assertion calls the remove function  and checks if the list returned is null
        assertNull(randomboard.remove(positions));
    }


    // TODO: test after refill testing
    // 10. Board full of tiles and a list of positions pointing to 2 adjacent TILES at the boarder of the matrix -> should return the list of tiles removed

    // TODO: to complete
    @Test
    public void remove_2AdjacentTilesAtBorder_RefilledBoard_shouldReturnList(){
        List<Position> positions = new ArrayList<>();
        List<Tile> tiles = new ArrayList<>();

        //create the tiles
        for (int i=0; i < 47; i++) {
            tiles.add(new Tile(Type.getRandomType()));
        }

        board4.refill(tiles);

        positions.add(new Position(2,4));
        positions.add(new Position(5,4));

        //the assertion calls the remove function  and checks if the list returned is null
        assertNotNull(randomboard.remove(positions));
    }

}
