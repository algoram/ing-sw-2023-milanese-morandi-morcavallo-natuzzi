package myshelfie_model.player;

import myshelfie_model.Tile;
import myshelfie_model.Type;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BookshelfTest {

    Bookshelf bookshelf = null;

    @Before
    public void setUp() {
        bookshelf = new Bookshelf();
    }

    @After
    public void tearDown() {

    }

    @Test
    public void isFull_fullBookshelf_shouldReturnTrue() {
        // we create the tiles
        Tile[][] tiles = new Tile[6][5];

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                tiles[i][j] = new Tile(Type.BOOKS);
            }
        }

        // we fill the bookshelf with the new created tiles
        bookshelf.setTiles(tiles);

        assertTrue(bookshelf.isFull());
    }

    @Test
    public void isFull_emptyBookshelf_shouldReturnFalse() {
        // we create the tiles
        Tile[][] tiles = new Tile[6][5];

        // we fill the bookshelf with the new created tiles
        bookshelf.setTiles(tiles);

        assertFalse(bookshelf.isFull());
    }

    @Test
    public void isFull_notFullNotEmptyBookshelf_shouldReturnFalse() {
        // we create the tiles
        Tile[][] tiles = new Tile[6][5];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                tiles[i][j] = new Tile(Type.BOOKS);
            }
        }

        // we fill the bookshelf with the new created tiles
        bookshelf.setTiles(tiles);

        assertFalse(bookshelf.isFull());
    }

}
