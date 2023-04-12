package myshelfie_model.goal.common_goal;
import myshelfie_model.Tile;
import myshelfie_model.Type;
import myshelfie_model.player.Bookshelf;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
public class FourLinesMax3Test {
    Bookshelf bookshelf = null;
    FourLinesMax3 fourLinesMax3 = null;


    @Before
    public void setUp() {
        bookshelf = new Bookshelf();
        fourLinesMax3 = new FourLinesMax3(4);
    }

    @After
    public void tearDown() { }

    @Test
    public void check_emptyBookshelf_shouldReturnFalse() {
        Tile[][] tiles = new Tile[6][5];

        bookshelf.setTiles(tiles);

        assertFalse(fourLinesMax3.check(bookshelf));
    }

    @Test
    public void check_columnsNotFull_shouldReturnFalse() {
        Tile[][] tiles = {
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS),  new Tile(Type.BOOKS), null},
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS),  null, new Tile(Type.BOOKS)},
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS),  new Tile(Type.BOOKS), null},
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS),  null, new Tile(Type.BOOKS)},
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS),  new Tile(Type.BOOKS), new Tile(Type.BOOKS)},
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), null, null, null},
        };

        bookshelf.setTiles(tiles);

        assertFalse(fourLinesMax3.check(bookshelf));
    }


    @Test
    public void check_oneColumnFourTypes_shouldReturnFalse() {
        Tile[][] tiles = {
                {new Tile(Type.BOOKS),  new Tile(Type.CATS), new Tile(Type.BOOKS),  new Tile(Type.FRAMES), new Tile(Type.PLANTS)},
                {new Tile(Type.BOOKS),   new Tile(Type.BOOKS), new Tile(Type.BOOKS),  new Tile(Type.BOOKS), new Tile(Type.BOOKS)},
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS),  new Tile(Type.BOOKS), new Tile(Type.BOOKS)},
                {new Tile(Type.PLANTS), new Tile(Type.BOOKS), new Tile(Type.BOOKS),  null, null},
                {new Tile(Type.BOOKS),  new Tile(Type.BOOKS), new Tile(Type.BOOKS),  null, null},
                {new Tile(Type.BOOKS),  new Tile(Type.BOOKS), new Tile(Type.BOOKS),  null, null},
        };

        bookshelf.setTiles(tiles);

        assertFalse(fourLinesMax3.check(bookshelf));
    }



    @Test
    public void check_columnsOneType_shouldReturnTrue() {
        Tile[][] tiles = {
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS),  new Tile(Type.BOOKS), new Tile(Type.BOOKS)},
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS),  new Tile(Type.BOOKS), new Tile(Type.BOOKS)},
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS),  new Tile(Type.BOOKS), new Tile(Type.BOOKS)},
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS),  new Tile(Type.BOOKS), new Tile(Type.BOOKS)},
                {null, null, null,  null, null},
                {null, null, null,  null, null},
        };

        bookshelf.setTiles(tiles);

        assertTrue(fourLinesMax3.check(bookshelf));
    }

    @Test
    public void check_correct_2_begins_2_end_shouldReturnTrue() {
        Tile[][] tiles = {
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS)},
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS)},
                {null, null, null,  null, null},
                {null, null, null,  null, null},
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS)},
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS)},
        };

        bookshelf.setTiles(tiles);

        assertTrue(fourLinesMax3.check(bookshelf));
    }


    @Test
    public void check_correct_alternate_shouldReturnTrue() {
        Tile[][] tiles = {
                {new Tile(Type.BOOKS), new Tile(Type.CATS), new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS)},
                {new Tile(Type.BOOKS), new Tile(Type.GAMES), new Tile(Type.BOOKS), new Tile(Type.PLANTS), new Tile(Type.BOOKS)},
                {new Tile(Type.BOOKS),  new Tile(Type.FRAMES), new Tile(Type.GAMES), new Tile(Type.TROPHIES), new Tile(Type.BOOKS)},
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS)},
                {new Tile(Type.BOOKS), null, new Tile(Type.PLANTS),null, new Tile(Type.FRAMES)},
                {new Tile(Type.BOOKS), null, new Tile(Type.BOOKS), null, new Tile(Type.BOOKS)},
        };

        bookshelf.setTiles(tiles);

        assertTrue(fourLinesMax3.check(bookshelf));
    }






}
