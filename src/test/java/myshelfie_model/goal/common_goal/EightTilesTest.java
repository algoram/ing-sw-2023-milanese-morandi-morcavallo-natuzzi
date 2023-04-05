package myshelfie_model.goal.common_goal;

import myshelfie_model.Tile;
import myshelfie_model.Type;
import myshelfie_model.player.Bookshelf;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EightTilesTest {

    Bookshelf bookshelf = null;
    EightTiles eightTiles = null;

    @Before
    public void setUp() {
        bookshelf = new Bookshelf();
        eightTiles = new EightTiles(4);
    }

    @After
    public void tearDown() { }

    @Test
    public void check_emptyBookshelf_shouldReturnFalse() {
        Tile[][] tiles = new Tile[6][5];

        bookshelf.setTiles(tiles);

        assertFalse(eightTiles.check(bookshelf));
    }

    @Test
    public void check_sevenTiles_shouldReturnFalse() {
        Tile[][] tiles = {
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS)},
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
        };

        bookshelf.setTiles(tiles);

        assertFalse(eightTiles.check(bookshelf));
    }

    @Test
    public void check_eightTilesWrongType_shouldReturnFalse() {
        Tile[][] tiles = {
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS)},
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.CATS), null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
        };

        bookshelf.setTiles(tiles);

        assertFalse(eightTiles.check(bookshelf));
    }

    @Test
    public void check_eightTilesSameType_shouldReturnTrue() {
        Tile[][] tiles = {
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS)},
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS), null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
        };

        bookshelf.setTiles(tiles);

        assertTrue(eightTiles.check(bookshelf));
    }

    @Test
    public void check_nineTilesSameType_shouldReturnTrue() {
        Tile[][] tiles = {
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS)},
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS), null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
        };

        bookshelf.setTiles(tiles);

        assertTrue(eightTiles.check(bookshelf));
    }

    @Test
    public void check_eightTilesRandomPosition_shouldReturnTrue() {
        Tile[][] tiles = {
                {new Tile(Type.BOOKS), null, null, new Tile(Type.BOOKS), null},
                {null, null, new Tile(Type.BOOKS), null, null},
                {null, null, null, null, null},
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), null, null, null},
                {null, null, null, null, new Tile(Type.BOOKS)},
                {null, new Tile(Type.BOOKS), null, new Tile(Type.BOOKS), null}
        };

        bookshelf.setTiles(tiles);

        assertTrue(eightTiles.check(bookshelf));
    }

}
