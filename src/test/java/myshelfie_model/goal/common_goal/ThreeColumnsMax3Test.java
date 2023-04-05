package myshelfie_model.goal.common_goal;

import myshelfie_model.Tile;
import myshelfie_model.Type;
import myshelfie_model.player.Bookshelf;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ThreeColumnsMax3Test {

    Bookshelf bookshelf = null;
    ThreeColumnsMax3 threeColumnsMax3 = null;

    @Before
    public void setUp() {
        bookshelf = new Bookshelf();
        threeColumnsMax3 = new ThreeColumnsMax3(4);
    }

    @After
    public void tearDown() { }

    @Test
    public void check_emptyBookshelf_shouldReturnFalse() {
        Tile[][] tiles = new Tile[6][5];

        bookshelf.setTiles(tiles);

        assertFalse(threeColumnsMax3.check(bookshelf));
    }

    @Test
    public void check_columnsNotFull_shouldReturnFalse() {
        Tile[][] tiles = {
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS),  null, null},
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS),  null, null},
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS),  null, null},
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS),  null, null},
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS),  null, null},
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), null,                  null, null},
        };

        bookshelf.setTiles(tiles);

        assertFalse(threeColumnsMax3.check(bookshelf));
    }

    @Test
    public void check_oneColumnFourTypes_shouldReturnFalse() {
        Tile[][] tiles = {
                {new Tile(Type.BOOKS),  new Tile(Type.BOOKS), new Tile(Type.BOOKS),  null, null},
                {new Tile(Type.CATS),   new Tile(Type.BOOKS), new Tile(Type.BOOKS),  null, null},
                {new Tile(Type.FRAMES), new Tile(Type.BOOKS), new Tile(Type.BOOKS),  null, null},
                {new Tile(Type.PLANTS), new Tile(Type.BOOKS), new Tile(Type.BOOKS),  null, null},
                {new Tile(Type.BOOKS),  new Tile(Type.BOOKS), new Tile(Type.BOOKS),  null, null},
                {new Tile(Type.BOOKS),  new Tile(Type.BOOKS), new Tile(Type.BOOKS),  null, null},
        };

        bookshelf.setTiles(tiles);

        assertFalse(threeColumnsMax3.check(bookshelf));
    }

    @Test
    public void check_columnsOneType_shouldReturnTrue() {
        Tile[][] tiles = {
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS),  null, null},
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS),  null, null},
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS),  null, null},
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS),  null, null},
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS),  null, null},
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS),  null, null},
        };

        bookshelf.setTiles(tiles);

        assertTrue(threeColumnsMax3.check(bookshelf));
    }

    @Test
    public void check_correctColumnsSpreadOut1_shouldReturnTrue() {
        Tile[][] tiles = {
                {new Tile(Type.BOOKS), null, new Tile(Type.BOOKS), null, new Tile(Type.BOOKS)},
                {new Tile(Type.BOOKS), null, new Tile(Type.BOOKS), null, new Tile(Type.BOOKS)},
                {new Tile(Type.BOOKS), null, new Tile(Type.BOOKS), null, new Tile(Type.BOOKS)},
                {new Tile(Type.BOOKS), null, new Tile(Type.BOOKS), null, new Tile(Type.BOOKS)},
                {new Tile(Type.BOOKS), null, new Tile(Type.BOOKS), null, new Tile(Type.BOOKS)},
                {new Tile(Type.BOOKS), null, new Tile(Type.BOOKS), null, new Tile(Type.BOOKS)},
        };

        bookshelf.setTiles(tiles);

        assertTrue(threeColumnsMax3.check(bookshelf));
    }

    @Test
    public void check_correctColumnsSpreadOut2_shouldReturnTrue() {
        Tile[][] tiles = {
                {new Tile(Type.BOOKS), null, new Tile(Type.BOOKS), null, new Tile(Type.BOOKS)},
                {new Tile(Type.BOOKS), null, new Tile(Type.BOOKS), null, new Tile(Type.TROPHIES)},
                {new Tile(Type.CATS),  null, new Tile(Type.GAMES), null, new Tile(Type.BOOKS)},
                {new Tile(Type.BOOKS), null, new Tile(Type.BOOKS), null, new Tile(Type.BOOKS)},
                {new Tile(Type.BOOKS), null, new Tile(Type.PLANTS),null, new Tile(Type.FRAMES)},
                {new Tile(Type.BOOKS), null, new Tile(Type.BOOKS), null, new Tile(Type.BOOKS)},
        };

        bookshelf.setTiles(tiles);

        assertTrue(threeColumnsMax3.check(bookshelf));
    }

}
