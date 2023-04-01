package myshelfie_model.goal.common_goal;

import myshelfie_model.Tile;
import myshelfie_model.Type;
import myshelfie_model.player.Bookshelf;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
     * The entire bookshelf is full of tiles of the same type -> should return true
     *
     */
    @Test
    public void check_FullShelfAllSameTypeWithCrossInTheMiddle_shouldReturnTrue(){

        //create a bookshelf with 6 rows and 5 columns
        Tile[][] tiles = new Tile[6][5];

        Type typeCross = Type.getRandomType();
        Type type = Type.getRandomType();

        while (typeCross == type) {
            type = Type.getRandomType();
        }

        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 5; j++){
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
}

