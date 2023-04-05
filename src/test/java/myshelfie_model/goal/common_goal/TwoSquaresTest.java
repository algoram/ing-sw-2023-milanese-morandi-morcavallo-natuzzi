package myshelfie_model.goal.common_goal;

import myshelfie_model.Tile;
import myshelfie_model.Type;
import myshelfie_model.player.Bookshelf;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TwoSquaresTest {

    Bookshelf bookshelf = null;
    TwoSquares twoSquares = null;

    @Before
    public void setUp() {
        bookshelf = new Bookshelf();
        twoSquares = new TwoSquares(4);
    }

    @After
    public void tearDown() { }

    @Test
    public void check_emptyBookshelf_shouldReturnFalse() {
        Tile[][] tiles = new Tile[6][5];

        bookshelf.setTiles(tiles);

        assertFalse(twoSquares.check(bookshelf));
    }

    @Test
    public void check_oneWrongTile_shouldReturnFalse() {
        Tile[][] tiles = {
                {new Tile(Type.BOOKS),  new Tile(Type.BOOKS),   null,                   null,                   null},
                {new Tile(Type.BOOKS),  new Tile(Type.CATS),    null,                   null,                   null},
                {null,                  null,                   null,                   null,                   null},
                {null,                  null,                   new Tile(Type.BOOKS),   new Tile(Type.BOOKS),   null},
                {null,                  null,                   new Tile(Type.BOOKS),   new Tile(Type.BOOKS),   null},
                {null,                  null,                   null,                   null,                   null},
        };

        bookshelf.setTiles(tiles);

        assertFalse(twoSquares.check(bookshelf));
    }

    @Test
    public void check_overlappingSquares_shouldReturnFalse() {
        Tile[][] tiles = {
                {new Tile(Type.BOOKS),  new Tile(Type.BOOKS),   new Tile(Type.BOOKS),   null,                   null},
                {new Tile(Type.BOOKS),  new Tile(Type.BOOKS),   new Tile(Type.BOOKS),   null,                   null},
                {null,                  null,                   null,                   null,                   null},
                {null,                  null,                   null,                   null,                   null},
                {null,                  null,                   null,                   null,                   null},
                {null,                  null,                   null,                   null,                   null},
        };

        bookshelf.setTiles(tiles);

        assertFalse(twoSquares.check(bookshelf));
    }

    @Test
    public void chech_sharingCornerSquares_shouldReturnFalse() {
        Tile[][] tiles = {
                {new Tile(Type.BOOKS),  new Tile(Type.BOOKS),   null,                   null,                   null},
                {new Tile(Type.BOOKS),  new Tile(Type.BOOKS),   new Tile(Type.BOOKS),   null,                   null},
                {null,                  new Tile(Type.BOOKS),   new Tile(Type.BOOKS),   null,                   null},
                {null,                  null,                   null,                   null,                   null},
                {null,                  null,                   null,                   null,                   null},
                {null,                  null,                   null,                   null,                   null},
        };

        bookshelf.setTiles(tiles);

        assertFalse(twoSquares.check(bookshelf));
    }

    @Test
    public void check_adjacentSquares_shouldReturnTrue() {
        Tile[][] tiles = {
                {new Tile(Type.BOOKS),  new Tile(Type.BOOKS),   new Tile(Type.BOOKS),   new Tile(Type.BOOKS),   null},
                {new Tile(Type.BOOKS),  new Tile(Type.BOOKS),   new Tile(Type.BOOKS),   new Tile(Type.BOOKS),   null},
                {null,                  null,                   null,                   null,                   null},
                {null,                  null,                   null,                   null,                   null},
                {null,                  null,                   null,                   null,                   null},
                {null,                  null,                   null,                   null,                   null},
        };

        bookshelf.setTiles(tiles);

        assertTrue(twoSquares.check(bookshelf));
    }

    @Test
    public void check_separatedSquares1_shouldReturnTrue() {
        Tile[][] tiles = {
                {new Tile(Type.BOOKS),  new Tile(Type.BOOKS),   null,                   new Tile(Type.BOOKS),   new Tile(Type.BOOKS)},
                {new Tile(Type.BOOKS),  new Tile(Type.BOOKS),   null,                   new Tile(Type.BOOKS),   new Tile(Type.BOOKS)},
                {null,                  null,                   null,                   null,                   null},
                {null,                  null,                   null,                   null,                   null},
                {null,                  null,                   null,                   null,                   null},
                {null,                  null,                   null,                   null,                   null},
        };

        bookshelf.setTiles(tiles);

        assertTrue(twoSquares.check(bookshelf));
    }

    @Test
    public void check_separatedSquares2_shouldReturnTrue() {
        Tile[][] tiles = {
                {new Tile(Type.BOOKS),  new Tile(Type.BOOKS),   null,                   null,                   null},
                {new Tile(Type.BOOKS),  new Tile(Type.BOOKS),   new Tile(Type.BOOKS),   new Tile(Type.BOOKS),   null},
                {null,                  null,                   new Tile(Type.BOOKS),   new Tile(Type.BOOKS),   null},
                {null,                  null,                   null,                   null,                   null},
                {null,                  null,                   null,                   null,                   null},
                {null,                  null,                   null,                   null,                   null},
        };

        bookshelf.setTiles(tiles);

        assertTrue(twoSquares.check(bookshelf));
    }

    @Test
    public void check_separatedSquares3_shouldReturnTrue() {
        Tile[][] tiles = {
                {new Tile(Type.BOOKS),  new Tile(Type.BOOKS),   null,                   null,                   null},
                {new Tile(Type.BOOKS),  new Tile(Type.BOOKS),   null,                   null,                   null},
                {null,                  null,                   null,                   null,                   null},
                {null,                  null,                   null,                   null,                   null},
                {null,                  null,                   null,                   new Tile(Type.BOOKS),   new Tile(Type.BOOKS)},
                {null,                  null,                   null,                   new Tile(Type.BOOKS),   new Tile(Type.BOOKS)},
        };

        bookshelf.setTiles(tiles);

        assertTrue(twoSquares.check(bookshelf));
    }

    @Test
    public void check_separatedSquares4_shouldReturnTrue() {
        Tile[][] tiles = {
                {null,                  null,                   null,                   new Tile(Type.BOOKS),   new Tile(Type.BOOKS)},
                {null,                  null,                   null,                   new Tile(Type.BOOKS),   new Tile(Type.BOOKS)},
                {null,                  null,                   null,                   null,                   null},
                {null,                  null,                   null,                   null,                   null},
                {null,                  null,                   null,                   new Tile(Type.BOOKS),   new Tile(Type.BOOKS)},
                {null,                  null,                   null,                   new Tile(Type.BOOKS),   new Tile(Type.BOOKS)},
        };

        bookshelf.setTiles(tiles);

        assertTrue(twoSquares.check(bookshelf));
    }

}
