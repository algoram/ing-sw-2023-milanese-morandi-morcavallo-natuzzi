package myshelfie_model.goal.common_goal;


import myshelfie_model.Tile;
import myshelfie_model.Type;
import myshelfie_model.player.Bookshelf;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
public class Diagonal5TilesTest {
    Bookshelf bookshelf = null;
    Diagonal5Tiles diagonal5Tiles = null;

    @Before
    public void setUp() {
        bookshelf = new Bookshelf();
        diagonal5Tiles = new Diagonal5Tiles(4);
    }
    @After
    public void tearDown() { }

    @Test
    public void check_emptyBookshelf_shouldReturnFalse() {
        Tile[][] tiles = new Tile[6][5];

        bookshelf.setTiles(tiles);

        assertFalse(diagonal5Tiles.check(bookshelf));
    }


    @Test
    public void check_casual_tiles_shouldReturnFalse() {
        Tile[][] tiles = {
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS),  null, null},
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS),  null, null},
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS),  null, null},
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS),  null, null},
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS),  null, null},
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), null, null, null},
        };

        bookshelf.setTiles(tiles);


        assertFalse(diagonal5Tiles.check(bookshelf));
    }




    @Test
    public void check_goal_from_right_shouldReturnTrue() {
        Tile[][] tiles = {
                {null, null, null, null, new Tile(Type.CATS)},
                {null, null, null, new Tile(Type.CATS), null},
                {null, null, new Tile(Type.CATS), null, null},
                {null, new Tile(Type.CATS), null, null, null},
                {new Tile(Type.CATS), null, null, null, null},
                {null, null, null, null, null}
        };

        bookshelf.setTiles(tiles);


        assertTrue(diagonal5Tiles.check(bookshelf));
    }


    @Test
    public void check_goal_from_left_shouldReturnTrue() {
        Tile[][] tiles = {
                {new Tile(Type.CATS), null, null, null, null},
                {null, new Tile(Type.CATS), null, null, null},
                {null, null, new Tile(Type.CATS), null, null},
                {null, null, null, new Tile(Type.CATS), null},
                {null, null, null, null, new Tile(Type.CATS)},
                {null, null, null, null, null}
        };

        bookshelf.setTiles(tiles);


        assertTrue(diagonal5Tiles.check(bookshelf));
    }
    @Test
    public void check_goal_from_left_one_card_different_shouldReturnFalse() {
        Tile[][] tiles = {
                {new Tile(Type.CATS), null, null, null, null},
                {null, new Tile(Type.GAMES), null, null, null},
                {null, null, new Tile(Type.CATS), null, null},
                {null, null, null, new Tile(Type.CATS), null},
                {null, null, null, null, new Tile(Type.CATS)},
                {null, null, null, null, null}
        };

        bookshelf.setTiles(tiles);


        assertFalse(diagonal5Tiles.check(bookshelf));
    }


    @Test
    public void check_goal_from_right_to_low_left_shouldReturnTrue() {
        Tile[][] tiles = {
                {null, null, null, null, null},
                {null, null, null, null, new Tile(Type.BOOKS)},
                {null, null, null, new Tile(Type.BOOKS), null},
                {null, null, new Tile(Type.BOOKS), null, null},
                {null, new Tile(Type.BOOKS), null, null, null},
                {new Tile(Type.BOOKS), null, null, null, null}
        };

        bookshelf.setTiles(tiles);


        assertTrue(diagonal5Tiles.check(bookshelf));
    }
    @Test
    public void check_goal_from_left_to_low_right_shouldReturnTrue() {
        Tile[][] tiles = {
                {null, null, null, null, null},
                {new Tile(Type.BOOKS), null, null, null, null},
                {null, new Tile(Type.BOOKS), null, null, null},
                {null, null, new Tile(Type.BOOKS), null, null},
                {null, null, null, new Tile(Type.BOOKS), null},
                {null, null, null, null, new Tile(Type.BOOKS)}
        };

        bookshelf.setTiles(tiles);


        assertTrue(diagonal5Tiles.check(bookshelf));
    }


    @Test
    public void check_goal_two_equal_diagonal_shouldReturnTrue() {
        Tile[][] tiles = {
                {null, null, null, null, null},
                {new Tile(Type.BOOKS), null, null, null, new Tile(Type.BOOKS)},
                {null, new Tile(Type.BOOKS), null, new Tile(Type.BOOKS), null},
                {null, null, new Tile(Type.BOOKS), null, null},
                {null, new Tile(Type.BOOKS), null, new Tile(Type.BOOKS), null},
                {new Tile(Type.BOOKS), null, null, null, new Tile(Type.BOOKS)}
        };

        bookshelf.setTiles(tiles);


        assertTrue(diagonal5Tiles.check(bookshelf));
    }

    @Test
    public void check_goal_one_diagonal_true_and_one_false_shouldReturnTrue() {
        Tile[][] tiles = {
                {null, null, null, null, null},
                {new Tile(Type.BOOKS), null, null, null, new Tile(Type.BOOKS)},
                {null, new Tile(Type.BOOKS), null, new Tile(Type.CATS), null},
                {null, null, new Tile(Type.BOOKS), null, null},
                {null, new Tile(Type.BOOKS), null, new Tile(Type.BOOKS), null},
                {new Tile(Type.BOOKS), null, null, null, new Tile(Type.BOOKS)}
        };

        bookshelf.setTiles(tiles);


        assertTrue(diagonal5Tiles.check(bookshelf));
    }

    @Test
    public void check_goal_two_diagonal_false_shouldReturnFalse() {
        Tile[][] tiles = {
                {null, null, null, null, null},
                {new Tile(Type.CATS), null, null, null, new Tile(Type.BOOKS)},
                {null, new Tile(Type.BOOKS), null, new Tile(Type.CATS), null},
                {null, null, new Tile(Type.FRAMES), null, null},
                {null, new Tile(Type.BOOKS), null, new Tile(Type.BOOKS), null},
                {new Tile(Type.BOOKS), null, null, null, new Tile(Type.BOOKS)}
        };

        bookshelf.setTiles(tiles);


        assertFalse(diagonal5Tiles.check(bookshelf));
    }




}
