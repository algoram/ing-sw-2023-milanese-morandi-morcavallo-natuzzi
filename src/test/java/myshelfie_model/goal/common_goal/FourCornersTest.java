package myshelfie_model.goal.common_goal;

import myshelfie_model.Tile;
import myshelfie_model.Type;
import myshelfie_model.player.Bookshelf;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class FourCornersTest {

    Bookshelf bookshelf = null;
    CommonGoal fourCorners = null;

    @Before
    public void setUp() {

        bookshelf = new Bookshelf();

        // random number of players no need of setting it up so far
        fourCorners = new FourCorners(2);
    }

    @After
    public void tearDown() {

    }

    /**
     * Test of check method, of class FourCorners.
     * Two Columns full of tiles (type GAMES) at the corners of the board -> should return true
     */
    @Test
    public void check_equalTilesAt4CornersEqColumns_shouldReturnTrue() {

        Tile[][] tiles = new Tile[6][5];

        for (int i = 0; i < 6; i++) {
            tiles[i][0] = new Tile(Type.GAMES);
            tiles[i][4] = new Tile(Type.GAMES);
        }

        // we fill the bookshelf with the new created tiles
        bookshelf.setTiles(tiles);


        assertTrue(fourCorners.check(bookshelf));
    }


    /**
     * Test of check method, of class FourCorners.
     * A Column full of tiles (type GAMES) at a corners of the board
     * A Column full of tiles (type BOOKS) at the opposite corner of the board
     */
    @Test
    public void check_notEqualTilesAt4CornersDiffColumns_shouldReturnFalse() {

        Tile[][] tiles = new Tile[6][5];

        for (int i = 0; i < 6; i++) {
            tiles[i][0] = new Tile(Type.GAMES);
            tiles[i][4] = new Tile(Type.BOOKS);
        }

        // we fill the bookshelf with the new created tiles
        bookshelf.setTiles(tiles);


        assertFalse(fourCorners.check(bookshelf));
    }

    /**
     * Test of check method, of class FourCorners.
     * Just two Colums with different types but equals at corners of the board
     */
    @Test
    public void check_EqualTilesAt4CornersMixedColumns_shouldReturnTrue() {

        Tile[][] tiles = new Tile[6][5];
//      First Column
        for (int i = 0; i < 3; i++) {
            tiles[i][0] = new Tile(Type.BOOKS);
        }
        for (int i = 3; i < 5; i++) {
            tiles[i][0] = new Tile(Type.FRAMES);
        }
        tiles[5][0] = new Tile(Type.BOOKS);

        // Second Column
        for (int i = 0; i < 2; i++) {
            tiles[i][4] = new Tile(Type.BOOKS);
        }
        for (int i = 2; i < 5; i++) {
            tiles[i][4] = new Tile(Type.CATS);
        }
        tiles[5][4] = new Tile(Type.BOOKS);

        // we fill the bookshelf with the new created tiles
        bookshelf.setTiles(tiles);

        assertTrue(fourCorners.check(bookshelf));
    }


    /**
     * FULL BOARD EQUAL CORNERS
     */
    @Test
    public void check_diffTilesAt4CornersFullBoard_shouldReturnTrue() {

        Tile[][] tiles = new Tile[6][5];
//      First Column
        for (int i = 0; i < 3; i++) {
            tiles[i][0] = new Tile(Type.BOOKS);
        }
        for (int i = 3; i < 5; i++) {
            tiles[i][0] = new Tile(Type.FRAMES);
        }
        tiles[5][0] = new Tile(Type.BOOKS);

        // Second Column
        for (int i = 0; i < 2; i++) {
            tiles[i][4] = new Tile(Type.BOOKS);
        }
        for (int i = 2; i < 5; i++) {
            tiles[i][4] = new Tile(Type.CATS);
        }
        tiles[5][4] = new Tile(Type.BOOKS);

        // we fill the bookshelf with the new created tiles
        bookshelf.setTiles(tiles);

        assertTrue(fourCorners.check(bookshelf));
    }

}
