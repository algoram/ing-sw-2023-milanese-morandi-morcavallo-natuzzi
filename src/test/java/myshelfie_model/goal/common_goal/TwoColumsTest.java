package myshelfie_model.goal.common_goal;

import myshelfie_model.Tile;
import myshelfie_model.Type;
import myshelfie_model.player.Bookshelf;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TwoColumsTest{

    Bookshelf bookshelf = null;
    TwoColumns twoColumns = null;

    @Before
    public void setUp() {
        bookshelf = new Bookshelf();
        twoColumns = new TwoColumns(4);
    }

    @Test
    public void check_emptyBookshelf_shouldReturnFalse() {
        Tile[][] tiles = new Tile[6][5];

        bookshelf.setTiles(tiles);

        assertFalse(twoColumns.check(bookshelf));
    }
    @Test
    public void check_twocolums_shouldReturnTrue() {
        Tile[][] tiles = {
                {new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS),  new Tile(Type.TROPHIES), new Tile(Type.TROPHIES)},
                {new Tile(Type.CATS), new Tile(Type.CATS), new Tile(Type.BOOKS),  new Tile(Type.TROPHIES), new Tile(Type.TROPHIES)},
                {new Tile(Type.GAMES), new Tile(Type.GAMES), new Tile(Type.BOOKS),  new Tile(Type.TROPHIES), new Tile(Type.TROPHIES)},
                {new Tile(Type.PLANTS), new Tile(Type.PLANTS), new Tile(Type.BOOKS),  new Tile(Type.TROPHIES), new Tile(Type.TROPHIES)},
                {new Tile(Type.TROPHIES), new Tile(Type.TROPHIES), new Tile(Type.BOOKS),  new Tile(Type.TROPHIES), new Tile(Type.TROPHIES)},
                {new Tile(Type.FRAMES), new Tile(Type.FRAMES), new Tile(Type.BOOKS),new Tile(Type.CATS), new Tile(Type.TROPHIES)},
        };

        bookshelf.setTiles(tiles);

        assertTrue(twoColumns.check(bookshelf));
    }
    @Test
    public void check_twocolums_equals_shouldReturnFalse() {
        Tile[][] tiles = {
                {new Tile(Type.BOOKS), null, new Tile(Type.BOOKS), null, new Tile(Type.BOOKS)},
                {new Tile(Type.BOOKS), null, new Tile(Type.BOOKS), null, new Tile(Type.CATS)},
                {new Tile(Type.BOOKS),  null, new Tile(Type.BOOKS), null, new Tile(Type.GAMES)},
                {new Tile(Type.BOOKS), null, new Tile(Type.BOOKS), null, new Tile(Type.PLANTS)},
                {new Tile(Type.BOOKS), null, new Tile(Type.BOOKS),null, new Tile(Type.TROPHIES)},
                {new Tile(Type.BOOKS), null, new Tile(Type.BOOKS), null, new Tile(Type.FRAMES)},
        };

        bookshelf.setTiles(tiles);

        assertFalse(twoColumns.check(bookshelf));
    }
    @Test
    public void check_twocolums_aTtheend_equals_shouldReturnTrue() {
        Tile[][] tiles = {
                {new Tile(Type.CATS), null, new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS)},
                {new Tile(Type.PLANTS), null, new Tile(Type.BOOKS), new Tile(Type.CATS), new Tile(Type.CATS)},
                {new Tile(Type.CATS),  null, new Tile(Type.BOOKS), new Tile(Type.GAMES), new Tile(Type.GAMES)},
                {new Tile(Type.BOOKS), null, new Tile(Type.BOOKS), new Tile(Type.PLANTS), new Tile(Type.PLANTS)},
                {new Tile(Type.BOOKS), null, new Tile(Type.BOOKS),new Tile(Type.TROPHIES), new Tile(Type.TROPHIES)},
                {new Tile(Type.BOOKS), null, new Tile(Type.BOOKS), new Tile(Type.FRAMES), new Tile(Type.FRAMES)},
        };

        bookshelf.setTiles(tiles);

        assertTrue(twoColumns.check(bookshelf));
    }

    @Test
    public void check_twocolums_al_the_elements_are_different_shouldReturnFalse() {
        Tile[][] tiles = {
                {new Tile(Type.BOOKS), null, new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS)},
                {new Tile(Type.CATS), null, new Tile(Type.CATS), new Tile(Type.CATS), null},
                {new Tile(Type.GAMES),  null, new Tile(Type.GAMES), null, new Tile(Type.GAMES)},
                {new Tile(Type.PLANTS), null, new Tile(Type.PLANTS), new Tile(Type.PLANTS), new Tile(Type.PLANTS)},
                {new Tile(Type.TROPHIES), null, new Tile(Type.BOOKS),new Tile(Type.TROPHIES), new Tile(Type.TROPHIES)},
                {new Tile(Type.BOOKS), null, new Tile(Type.CATS), new Tile(Type.FRAMES), new Tile(Type.FRAMES)},
        };

        bookshelf.setTiles(tiles);

        assertFalse(twoColumns.check(bookshelf));
    }
    @Test
    public void check_twocolums_at_begins_in_the_middle_equals_shouldReturnTrue() {
        Tile[][] tiles = {
                {new Tile(Type.BOOKS), null, new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS)},
                {new Tile(Type.CATS), null, new Tile(Type.CATS), new Tile(Type.CATS), null},
                {new Tile(Type.GAMES),  null, new Tile(Type.GAMES), null, new Tile(Type.GAMES)},
                {new Tile(Type.PLANTS), null, new Tile(Type.PLANTS), new Tile(Type.PLANTS), new Tile(Type.PLANTS)},
                {new Tile(Type.TROPHIES), null, new Tile(Type.TROPHIES),new Tile(Type.TROPHIES), new Tile(Type.TROPHIES)},
                {new Tile(Type.FRAMES), null, new Tile(Type.FRAMES), new Tile(Type.FRAMES), new Tile(Type.FRAMES)},
        };

        bookshelf.setTiles(tiles);

        assertTrue(twoColumns.check(bookshelf));
    }

    @Test
    public void check_twocolums_at_begins_in_the_end_equals_shouldReturnTrue() {
        Tile[][] tiles = {
                {new Tile(Type.FRAMES), null, new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS)},
                {new Tile(Type.CATS), null, new Tile(Type.CATS), new Tile(Type.CATS), new Tile(Type.CATS)},
                {new Tile(Type.GAMES),  null, new Tile(Type.GAMES), null, new Tile(Type.GAMES)},
                {new Tile(Type.PLANTS), null, new Tile(Type.PLANTS), new Tile(Type.PLANTS), new Tile(Type.PLANTS)},
                {new Tile(Type.TROPHIES), null, new Tile(Type.CATS),new Tile(Type.TROPHIES), new Tile(Type.TROPHIES)},
                {new Tile(Type.BOOKS), null, new Tile(Type.FRAMES), new Tile(Type.FRAMES), new Tile(Type.FRAMES)},
        };

        bookshelf.setTiles(tiles);

        assertTrue(twoColumns.check(bookshelf));
    }
    @Test
    public void check_twocolums_at_begins_in_the_end_equals_allthematrixcomplete_shouldReturnTrue() {
        Tile[][] tiles = {
                {new Tile(Type.FRAMES), new Tile(Type.FRAMES), new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS)},
                {new Tile(Type.CATS), new Tile(Type.PLANTS), new Tile(Type.CATS), new Tile(Type.CATS), new Tile(Type.CATS)},
                {new Tile(Type.GAMES),  new Tile(Type.PLANTS), new Tile(Type.GAMES), new Tile(Type.CATS), new Tile(Type.GAMES)},
                {new Tile(Type.PLANTS), new Tile(Type.TROPHIES), new Tile(Type.PLANTS), new Tile(Type.PLANTS), new Tile(Type.PLANTS)},
                {new Tile(Type.TROPHIES), new Tile(Type.BOOKS), new Tile(Type.CATS),new Tile(Type.TROPHIES), new Tile(Type.TROPHIES)},
                {new Tile(Type.BOOKS), new Tile(Type.PLANTS), new Tile(Type.FRAMES), new Tile(Type.FRAMES), new Tile(Type.FRAMES)},
        };

        bookshelf.setTiles(tiles);

        assertTrue(twoColumns.check(bookshelf));
    }


    @Test
    public void check_twocolums_more_than_two_columns_equals_shouldReturnTrue() {
        Tile[][] tiles = {
                {new Tile(Type.FRAMES), new Tile(Type.FRAMES), new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS)},
                {new Tile(Type.CATS), new Tile(Type.PLANTS), new Tile(Type.CATS), new Tile(Type.CATS), new Tile(Type.CATS)},
                {new Tile(Type.GAMES),  new Tile(Type.CATS), new Tile(Type.GAMES), new Tile(Type.CATS), new Tile(Type.GAMES)},
                {new Tile(Type.PLANTS), new Tile(Type.TROPHIES), new Tile(Type.PLANTS), new Tile(Type.PLANTS), new Tile(Type.PLANTS)},
                {new Tile(Type.TROPHIES), new Tile(Type.BOOKS), new Tile(Type.CATS),new Tile(Type.TROPHIES), new Tile(Type.TROPHIES)},
                {new Tile(Type.BOOKS), new Tile(Type.GAMES), new Tile(Type.FRAMES), new Tile(Type.FRAMES), new Tile(Type.FRAMES)},
        };

        bookshelf.setTiles(tiles);

        assertTrue(twoColumns.check(bookshelf));
    }
    @Test
    public void check_twocolums_all_different_shouldReturnFalse() {
        Tile[][] tiles = {
                {new Tile(Type.FRAMES), new Tile(Type.FRAMES), new Tile(Type.BOOKS), new Tile(Type.BOOKS), new Tile(Type.BOOKS)},
                {new Tile(Type.BOOKS), null, new Tile(Type.CATS), new Tile(Type.CATS), null},
                {new Tile(Type.GAMES),  new Tile(Type.CATS), new Tile(Type.GAMES), new Tile(Type.CATS), null},
                {new Tile(Type.PLANTS), new Tile(Type.TROPHIES), new Tile(Type.PLANTS), new Tile(Type.PLANTS), new Tile(Type.PLANTS)},
                {new Tile(Type.TROPHIES), new Tile(Type.BOOKS), new Tile(Type.CATS),new Tile(Type.TROPHIES), new Tile(Type.TROPHIES)},
                {new Tile(Type.BOOKS), new Tile(Type.GAMES), new Tile(Type.FRAMES), null, new Tile(Type.FRAMES)},
        };

        bookshelf.setTiles(tiles);

        assertFalse(twoColumns.check(bookshelf));
    }








}
