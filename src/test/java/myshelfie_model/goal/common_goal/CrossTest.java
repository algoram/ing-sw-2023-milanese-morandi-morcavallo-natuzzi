package myshelfie_model.goal.common_goal;

import myshelfie_model.Tile;
import myshelfie_model.Type;
import myshelfie_model.player.Bookshelf;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

// LIST OF TESTS
// 1. The entire bookshelf is full of tiles of the same type -> should return true
// 2. The entire bookshelf is full of tiles of different types WITH A CROSS IN THE MIDDLE -> should return true
// 3. check_emptyBookshelf_shouldReturnFalse
// 4. check_allRowFullExceptTheLastNoCross_shouldReturnFalse

public class CrossTest {

    Bookshelf bookshelf = null;
    CommonGoal cross = null;

    @Before
    public void setup(){
        bookshelf = new Bookshelf();

        // random number of players from 2 to 4 no need of setting it up so far
        // create a random number of players from 2 to 4  -> 2, 3, 4
        int numberOfPlayers = (int) (Math.random() * 3) + 2;
        //System.out.println("Number of players: " + numberOfPlayers);
        cross = new Cross(numberOfPlayers);
    }

    @After
    public void tearDown(){

    }

    /**
     * Test of check method, of class Cross.
     * The entire bookshelf is full of tiles of the same type -> should return true
     *
     */
    @Test
    public void check_FullShelfAllSameType_shouldReturnTrue(){

        // create a bookshelf with 6 rows and 5 columns
        Tile[][] tiles = new Tile[6][5];

        Type type = Type.getRandomType();

        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 5; j++){
                tiles[i][j] = new Tile(type);
            }
        }

        bookshelf.setTiles(tiles);

        assertTrue(cross.check(bookshelf));

    }

    /**
     * Test of check method, of class Cross.
     * The entire bookshelf is full of tiles of random tiles except
     * for a cross in the middle should return true
     */
    @Test
    public void check_FullShelfRandomTypeWithCrossInTheMiddle_shouldReturnTrue(){

        //create a bookshelf with 6 rows and 5 columns
        Tile[][] tiles = new Tile[6][5];

        Type typeCross = Type.getRandomType();
        Type type = Type.getRandomType();


        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 5; j++){
                while (typeCross == type) {
                    type = Type.getRandomType();
                }
                tiles[i][j] = new Tile(type);
            }
        }

        // create an X of tiles of the same type in the middle of the bookshelf
        tiles[2][2] = new Tile(typeCross);
        tiles[1][1] = new Tile(typeCross);
        tiles[1][3] = new Tile(typeCross);
        tiles[3][1] = new Tile(typeCross);
        tiles[3][3] = new Tile(typeCross);

        bookshelf.setTiles(tiles);

        assertTrue(cross.check(bookshelf));

    }

    /**
     * Test of check method, of class Cross.
     * The entire bookshelf is empty -> should return false
     */
    @Test
    public void check_emptyBookshelf_shouldReturnFalse(){

        Tile[][] tiles = new Tile[6][5];

        bookshelf.setTiles(tiles);

        assertFalse(cross.check(bookshelf));
    }

    @Test
    public void check_allRowFullExceptTheLastNoCross_shouldReturnFalse(){
        Tile[][] tiles = new Tile[6][5];

        Type type;

        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                type = Type.getRandomType();
                // AVOID TYPES TO BE EQUAL IN DIAGONAL -> NO CROSS
                while(i > 0 && j > 0 && type == tiles[i-1][j-1].getType()) {
                    type = Type.getRandomType();
                }
                tiles[i][j] = new Tile(type);
            }
        }

        bookshelf.setTiles(tiles);

        assertFalse(cross.check(bookshelf));
    }
}
