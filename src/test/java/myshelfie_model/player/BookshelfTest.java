package myshelfie_model.player;

import myshelfie_model.Tile;
import myshelfie_model.Type;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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

    @Test
    public void getPoints_shouldreturn2() {
        Tile[][] tiles = {
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.CATS), new Tile(Type.FRAMES)},
                {new Tile(Type.GAMES), new Tile(Type.CATS), new Tile(Type.FRAMES), new Tile(Type.PLANTS), new Tile(Type.GAMES)},
                {new Tile(Type.PLANTS), new Tile(Type.FRAMES), new Tile(Type.CATS), new Tile(Type.GAMES), new Tile(Type.BOOKS)},
                {new Tile(Type.FRAMES), new Tile(Type.PLANTS), new Tile(Type.GAMES), new Tile(Type.BOOKS), new Tile(Type.CATS)},
                {new Tile(Type.BOOKS), new Tile(Type.GAMES), new Tile(Type.PLANTS), new Tile(Type.CATS), new Tile(Type.FRAMES)},
                {new Tile(Type.GAMES), new Tile(Type.PLANTS), new Tile(Type.CATS), new Tile(Type.FRAMES), new Tile(Type.BOOKS)}
        };

        bookshelf.setTiles(tiles);
        assertEquals(2,bookshelf.getPoints());



    }
    @Test
    public void getPoints_shouldreturn0() {
        Tile[][] tiles = {
                {new Tile(Type.BOOKS), new Tile(Type.GAMES), new Tile(Type.TROPHIES), new Tile(Type.CATS), new Tile(Type.FRAMES)},
                {new Tile(Type.GAMES), new Tile(Type.CATS), new Tile(Type.FRAMES), new Tile(Type.PLANTS), new Tile(Type.GAMES)},
                {new Tile(Type.PLANTS), new Tile(Type.FRAMES), new Tile(Type.CATS), new Tile(Type.GAMES), new Tile(Type.BOOKS)},
                {new Tile(Type.FRAMES), new Tile(Type.PLANTS), new Tile(Type.GAMES), new Tile(Type.BOOKS), new Tile(Type.CATS)},
                {new Tile(Type.BOOKS), new Tile(Type.GAMES), new Tile(Type.PLANTS), new Tile(Type.CATS), new Tile(Type.FRAMES)},
                {new Tile(Type.GAMES), new Tile(Type.PLANTS), new Tile(Type.CATS), new Tile(Type.FRAMES), new Tile(Type.BOOKS)}
        };

        bookshelf.setTiles(tiles);
        assertEquals(0,bookshelf.getPoints());



    }

    @Test
    public void getPoints_shouldreturn3() {
        Tile[][] tiles = {
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.TROPHIES), new Tile(Type.CATS), new Tile(Type.FRAMES)},
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.FRAMES), new Tile(Type.PLANTS), new Tile(Type.GAMES)},
                {new Tile(Type.PLANTS), new Tile(Type.FRAMES), new Tile(Type.CATS), new Tile(Type.GAMES), new Tile(Type.BOOKS)},
                {new Tile(Type.FRAMES), new Tile(Type.PLANTS), new Tile(Type.GAMES), new Tile(Type.BOOKS), new Tile(Type.CATS)},
                {new Tile(Type.BOOKS), new Tile(Type.GAMES), new Tile(Type.PLANTS), new Tile(Type.CATS), new Tile(Type.FRAMES)},
                {new Tile(Type.GAMES), new Tile(Type.PLANTS), new Tile(Type.CATS), new Tile(Type.FRAMES), new Tile(Type.BOOKS)}
        };

        bookshelf.setTiles(tiles);
        assertEquals(3,bookshelf.getPoints());



    }
    @Test
    public void getPoints_shouldreturn5() {
        Tile[][] tiles = {
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.TROPHIES), new Tile(Type.CATS), new Tile(Type.FRAMES)},
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.PLANTS), new Tile(Type.GAMES)},
                {new Tile(Type.PLANTS), new Tile(Type.FRAMES), new Tile(Type.CATS), new Tile(Type.GAMES), new Tile(Type.BOOKS)},
                {new Tile(Type.FRAMES), new Tile(Type.PLANTS), new Tile(Type.GAMES), new Tile(Type.BOOKS), new Tile(Type.CATS)},
                {new Tile(Type.BOOKS), new Tile(Type.GAMES), new Tile(Type.PLANTS), new Tile(Type.CATS), new Tile(Type.FRAMES)},
                {new Tile(Type.GAMES), new Tile(Type.PLANTS), new Tile(Type.CATS), new Tile(Type.FRAMES), new Tile(Type.BOOKS)}
        };

        bookshelf.setTiles(tiles);
        assertEquals(5,bookshelf.getPoints());



    }


    @Test
    public void getPoints_shouldreturn6() {
        Tile[][] tiles = {
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.TROPHIES), new Tile(Type.CATS), new Tile(Type.FRAMES)},
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.PLANTS), new Tile(Type.GAMES)},
                {new Tile(Type.BOOKS), new Tile(Type.FRAMES), new Tile(Type.CATS), new Tile(Type.GAMES), new Tile(Type.BOOKS)},
                {new Tile(Type.FRAMES), new Tile(Type.PLANTS), new Tile(Type.GAMES), new Tile(Type.BOOKS), new Tile(Type.CATS)},
                {new Tile(Type.BOOKS), new Tile(Type.GAMES), new Tile(Type.PLANTS), new Tile(Type.CATS), new Tile(Type.FRAMES)},
                {new Tile(Type.GAMES), new Tile(Type.PLANTS), new Tile(Type.CATS), new Tile(Type.FRAMES), new Tile(Type.BOOKS)}
        };

        bookshelf.setTiles(tiles);
        assertEquals(8,bookshelf.getPoints());



    }

    @Test
    public void getPoints_second_try_shouldreturn6() {
        Tile[][] tiles = {
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.TROPHIES), new Tile(Type.CATS), new Tile(Type.FRAMES)},
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.PLANTS), new Tile(Type.GAMES)},
                {new Tile(Type.BOOKS), new Tile(Type.FRAMES), new Tile(Type.CATS), new Tile(Type.GAMES), new Tile(Type.BOOKS)},
                {new Tile(Type.BOOKS), new Tile(Type.PLANTS), new Tile(Type.GAMES), new Tile(Type.BOOKS), new Tile(Type.CATS)},
                {new Tile(Type.BOOKS), new Tile(Type.GAMES), new Tile(Type.PLANTS), new Tile(Type.CATS), new Tile(Type.FRAMES)},
                {new Tile(Type.GAMES), new Tile(Type.PLANTS), new Tile(Type.CATS), new Tile(Type.FRAMES), new Tile(Type.BOOKS)}
        };

        bookshelf.setTiles(tiles);
        assertEquals(8,bookshelf.getPoints());



    }
    @Test
    public void getPoints_more_than_group_shouldreturn6() {
        Tile[][] tiles = {
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.TROPHIES), new Tile(Type.CATS), new Tile(Type.FRAMES)},
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.PLANTS), new Tile(Type.GAMES)},
                {new Tile(Type.BOOKS), new Tile(Type.FRAMES), new Tile(Type.CATS), new Tile(Type.GAMES), new Tile(Type.BOOKS)},
                {new Tile(Type.BOOKS), new Tile(Type.PLANTS), new Tile(Type.GAMES), new Tile(Type.BOOKS), new Tile(Type.CATS)},
                {new Tile(Type.BOOKS), new Tile(Type.GAMES), new Tile(Type.GAMES), new Tile(Type.CATS), new Tile(Type.FRAMES)},
                {new Tile(Type.GAMES), new Tile(Type.GAMES), new Tile(Type.GAMES), new Tile(Type.FRAMES), new Tile(Type.BOOKS)}
        };

        bookshelf.setTiles(tiles);
        assertEquals(16,bookshelf.getPoints());



    }
    @Test
    public void getPoints_shouldreturn10() {
        Tile[][] tiles = {
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.CATS), new Tile(Type.FRAMES)},
                {new Tile(Type.GAMES), new Tile(Type.GAMES), new Tile(Type.GAMES), new Tile(Type.PLANTS), new Tile(Type.GAMES)},
                {new Tile(Type.PLANTS), new Tile(Type.PLANTS), new Tile(Type.PLANTS), new Tile(Type.GAMES), new Tile(Type.BOOKS)},
                {new Tile(Type.FRAMES), new Tile(Type.FRAMES), new Tile(Type.FRAMES), new Tile(Type.BOOKS), new Tile(Type.CATS)},
                {new Tile(Type.TROPHIES), new Tile(Type.TROPHIES), new Tile(Type.TROPHIES), new Tile(Type.CATS), new Tile(Type.FRAMES)},
                {new Tile(Type.GAMES), new Tile(Type.PLANTS), new Tile(Type.CATS), new Tile(Type.FRAMES), new Tile(Type.BOOKS)}
        };

        bookshelf.setTiles(tiles);
        assertEquals(10,bookshelf.getPoints());



    }


}

