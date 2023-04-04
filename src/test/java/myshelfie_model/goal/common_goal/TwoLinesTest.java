package myshelfie_model.goal.common_goal;


import myshelfie_model.Tile;
import myshelfie_model.Type;
import myshelfie_model.player.Bookshelf;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TwoLinesTest {

    Bookshelf bookshelf = null;
    CommonGoal  TwoLines = null;
    private void buildBookshelf(Tile[][] tiles, final int[][] BOOKSHELF_SETUP) {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                switch (BOOKSHELF_SETUP[i][j]) {
                    case 1 -> tiles[i][j] = new Tile(Type.CATS);
                    case 2 -> tiles[i][j] = new Tile(Type.BOOKS);
                    case 3 -> tiles[i][j] = new Tile(Type.GAMES);
                    case 4 -> tiles[i][j] = new Tile(Type.FRAMES);
                    case 5 -> tiles[i][j] = new Tile(Type.TROPHIES);
                    case 6 -> tiles[i][j] = new Tile(Type.PLANTS);
                    default -> tiles[i][j] = null;
                }
            }
        }
    }
    @Before
    public void setup() {
        bookshelf = new Bookshelf();

        // random number of players from 2 to 4 no need of setting it up so far
        // create a random number of players from 2 to 4  -> 2, 3, 4
        int numberOfPlayers = (int) (Math.random() * 3) + 2;
        //System.out.println("Number of players: " + numberOfPlayers);
        TwoLines = new TwoLines(numberOfPlayers);
    }


    @After
    public void tearDown() {
    }

    @Test
    public void check_emptyBookShelf_ShouldReturnFalse() {

        Tile[][] tiles = new Tile[6][5];

        bookshelf.setTiles(tiles);
        assertFalse(TwoLines.check(bookshelf));

    }

    @Test
    public void check_FullBookShelf_0lines_ShouldReturnFalse() {
        //    null:     0
        //    CATS:     1
        //    BOOKS:    2
        //    GAMES:    3
        //    FRAMES:   4
        //    TROPHIES: 5
        //    PLANTS:   6
        final int[][] BOOKSHELF_SETUP = {
                {2,3,4,2,1},    //row:0
                {4,3,2,1,2},    //row:1
                {1,5,4,1,3},    //row:2
                {1,6,5,1,4},    //row:3
                {3,4,2,5,5},    //row:4
                {3,2,2,2,2},    //row:5
        };

        Tile[][] tiles = new Tile[6][5];
        buildBookshelf(tiles, BOOKSHELF_SETUP);

        bookshelf.setTiles(tiles);
        assertFalse(TwoLines.check(bookshelf));

    }
    @Test
    public void check_FullBookShelf_1lines_ShouldReturnFalse() {
        //    null:     0
        //    CATS:     1
        //    BOOKS:    2
        //    GAMES:    3
        //    FRAMES:   4
        //    TROPHIES: 5
        //    PLANTS:   6
        final int[][] BOOKSHELF_SETUP = {
                {2,3,4,2,1},    //row:0
                {4,3,2,1,2},    //row:1
                {1,5,4,1,3},    //row:2
                {1,6,5,1,4},    //row:3
                {3,4,2,5,5},    //row:4
                {1,2,3,4,5},    //row:5 <---  line
        };

        Tile[][] tiles = new Tile[6][5];
        buildBookshelf(tiles, BOOKSHELF_SETUP);

        bookshelf.setTiles(tiles);
        assertFalse(TwoLines.check(bookshelf));
    }

    @Test
    public void check_FullBookShelf_2lines_ShouldReturnTrue() {
        //    null:     0
        //    CATS:     1
        //    BOOKS:    2
        //    GAMES:    3
        //    FRAMES:   4
        //    TROPHIES: 5
        //    PLANTS:   6
        final int[][] BOOKSHELF_SETUP = {
                {2,3,4,2,1},    //row:0
                {4,3,2,1,5},    //row:1   <---  line
                {1,5,4,1,3},    //row:2
                {1,6,5,1,4},    //row:3
                {3,4,2,1,5},    //row:4   <---  line
                {3,2,2,2,2},    //row:5
        };

        Tile[][] tiles = new Tile[6][5];
        buildBookshelf(tiles, BOOKSHELF_SETUP);

        bookshelf.setTiles(tiles);
        assertTrue(TwoLines.check(bookshelf));

    }
    @Test
    public void check_FullBookShelf_AllLines_ShouldReturnTrue() {
        //    null:     0
        //    CATS:     1
        //    BOOKS:    2
        //    GAMES:    3
        //    FRAMES:   4
        //    TROPHIES: 5
        //    PLANTS:   6
        final int[][] BOOKSHELF_SETUP = {
                {2,3,4,6,1},    //row:0 <---  line
                {4,3,2,1,5},    //row:1 <---  line
                {1,5,4,2,3},    //row:2 <---  line
                {1,6,5,2,4},    //row:3 <---  line
                {6,2,4,3,5},    //row:4 <---  line
                {1,2,3,4,5},    //row:5 <---  line
        };

        Tile[][] tiles = new Tile[6][5];
        buildBookshelf(tiles, BOOKSHELF_SETUP);

        bookshelf.setTiles(tiles);
        assertTrue(TwoLines.check(bookshelf));

    }

    @Test
    public void check_BookShelf_2lines_ShouldReturnTrue() {
        //    null:     0
        //    CATS:     1
        //    BOOKS:    2
        //    GAMES:    3
        //    FRAMES:   4
        //    TROPHIES: 5
        //    PLANTS:   6
        final int[][] BOOKSHELF_SETUP = {
                {0,0,2,0,0},    //row:0
                {0,0,1,0,0},    //row:1
                {0,0,2,0,0},    //row:2
                {0,6,5,2,4},    //row:3
                {6,2,4,3,5},    //row:4 <---  line
                {1,2,3,4,5},    //row:5 <---  line
        };

        Tile[][] tiles = new Tile[6][5];
        buildBookshelf(tiles, BOOKSHELF_SETUP);

        bookshelf.setTiles(tiles);
        assertTrue(TwoLines.check(bookshelf));

    }

    @Test
    public void check_BookShelf_0lines_ShouldReturnFalse() {
        //    null:     0
        //    CATS:     1
        //    BOOKS:    2
        //    GAMES:    3
        //    FRAMES:   4
        //    TROPHIES: 5
        //    PLANTS:   6
        final int[][] BOOKSHELF_SETUP = {
                {0,0,2,0,0},    //row:0
                {0,0,1,0,0},    //row:1
                {0,0,2,2,0},    //row:2
                {0,6,5,2,4},    //row:3
                {5,2,4,3,5},    //row:4
                {1,2,2,4,5},    //row:5
        };

        Tile[][] tiles = new Tile[6][5];
        buildBookshelf(tiles, BOOKSHELF_SETUP);

        bookshelf.setTiles(tiles);
        assertFalse(TwoLines.check(bookshelf));

    }


}
