package myshelfie_model.goal.common_goal;


import myshelfie_model.Tile;
import myshelfie_model.Type;
import myshelfie_model.player.Bookshelf;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


// TESTS FOR THE CLASS SixGroups2Tiles
// 1. check_emptyBookshelf_shouldReturnFalse
// 2. check_fullBookshelfOfSameType_shouldReturnFalse
// 3. check_2columnsOrizontalMatch_shouldReturnTrue (Match da 2)
// 4. check_2columnsVerticalMatch_shouldReturnTrue  (Match da 2)
// 5. check_2columnsVerticalMatchofThreeTiles_shouldReturnFalse (Match da 3)
// 6. check_3columnsVerticalMatchofThreeTiles_shouldReturnTrue (Match da 3)
// 7. check_fullBookShelfDesignedForNoMatch_shouldReturnFalse
// 8. check_fullBookShelfDesignedForMatch_shouldReturnTrue


public class SixGroups2TilesTest {

    CommonGoal sixGroups2Tiles = null;
    Bookshelf bookshelf = null;

    @Before
    public void setup(){
        int numberOfPlayers = (int) (Math.random() * 3) + 2;
        //System.out.println("Number of players: " + numberOfPlayers);
        sixGroups2Tiles = new SixGroups2Tiles(numberOfPlayers);
    }

    @After
    public void tearDown(){

    }


    @Test
    public void check_emptyBookshelf_shouldReturnFalse(){
        bookshelf = new Bookshelf();
        Tile[][] tiles = new Tile[6][5];

        bookshelf.setTiles(tiles);
        assertFalse(sixGroups2Tiles.check(bookshelf));
    }


    /**
     * Test of check method, of class SixGroups2Tiles.
     * The entire bookshelf is full of tiles of the same type -> should return false
     */
    @Test
    public void check_fullBookshelfOfSameType_shouldReturnFalse(){

        bookshelf = new Bookshelf();
        Tile[][] tiles = new Tile[6][5];

        Type type = Type.getRandomType();

        while(type == Type.EMPTY){
            type = Type.getRandomType();
        }

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                tiles[i][j] = new Tile(type);
            }
        }

        bookshelf.setTiles(tiles);
        assertFalse(sixGroups2Tiles.check(bookshelf));
    }


    /**
     * Test of check method, of class SixGroups2Tiles.
     * The are just 2 columns full off tiles, in each row yhe tiles are of the same type
     * but two nearby row have different types -> should return true
     */
    @Test
    public void check_2columnsOrizontalMatch_shouldReturnTrue(){

        bookshelf = new Bookshelf();
        Tile[][] tiles = new Tile[6][5];

        Type oldType = Type.EMPTY;
        Type typematch = Type.getRandomType();

        for (int i = 0; i < 6; i++) {
            while (typematch == Type.EMPTY || typematch == oldType) {
                typematch = Type.getRandomType();
            }
            tiles[i][0] = new Tile(typematch);
            tiles[i][1] = new Tile(typematch);
            oldType = typematch;
        }


        bookshelf.setTiles(tiles);
        assertTrue(sixGroups2Tiles.check(bookshelf));
    }


    /**
     * Test of check method, of class SixGroups2Tiles.
     * There are just 2 columns full off tiles, in each column there are 3 matches  in vertical
     */
    @Test
    public void check_2columnsVerticalMatch_shouldReturnTrue(){

        bookshelf = new Bookshelf();
        Tile[][] tiles = new Tile[6][5];

       //Filled it manually to be 2 types matching diagonally

        tiles[0][0] = new Tile(Type.BOOKS);
        tiles[1][0] = new Tile(Type.BOOKS);

        tiles[0][1] = new Tile(Type.CATS);
        tiles[1][1] = new Tile(Type.CATS);

        tiles[2][0] = new Tile(Type.CATS);
        tiles[3][0] = new Tile(Type.CATS);

        tiles[2][1] = new Tile(Type.BOOKS);
        tiles[3][1] = new Tile(Type.BOOKS);

        tiles[4][0] = new Tile(Type.BOOKS);
        tiles[5][0] = new Tile(Type.BOOKS);

        tiles[4][1] = new Tile(Type.CATS);
        tiles[5][1] = new Tile(Type.CATS);

        bookshelf.setTiles(tiles);
        assertTrue(sixGroups2Tiles.check(bookshelf));
    }

    /**
     * Test of check method, of class SixGroups2Tiles.
     * There are just 2 columns full off tiles, in each column there are 2 matches  in vertical
     * but there are 4 groups of 3 tiles -> should return false
     */
    @Test
    public void check_2columnsVerticalMatchofThreeTiles_shouldReturnFalse(){

        bookshelf = new Bookshelf();
        Tile[][] tiles = new Tile[6][5];

        //Filled it manually to have 3 tiles matching vertically x 4 Groups

        tiles[0][0] = new Tile(Type.BOOKS);
        tiles[1][0] = new Tile(Type.BOOKS);
        tiles[2][0] = new Tile(Type.BOOKS);

        tiles[0][1] = new Tile(Type.CATS);
        tiles[1][1] = new Tile(Type.CATS);
        tiles[2][1] = new Tile(Type.CATS);

        tiles[3][0] = new Tile(Type.CATS);
        tiles[4][0] = new Tile(Type.CATS);
        tiles[5][0] = new Tile(Type.CATS);

        tiles[3][1] = new Tile(Type.BOOKS);
        tiles[4][1] = new Tile(Type.BOOKS);
        tiles[5][1] = new Tile(Type.BOOKS);

        bookshelf.setTiles(tiles);
        assertFalse(sixGroups2Tiles.check(bookshelf));
    }

    @Test
    public void check_3columnsVerticalMatchofThreeTiles_shouldReturnTrue(){
        bookshelf = new Bookshelf();
        Tile[][] tiles = new Tile[6][5];

        //Filled it manually to have 3 tiles matching vertically x 4 Groups

        tiles[0][2] = new Tile(Type.BOOKS);
        tiles[1][2] = new Tile(Type.BOOKS);
        tiles[2][2] = new Tile(Type.BOOKS);

        tiles[0][3] = new Tile(Type.CATS);
        tiles[1][3] = new Tile(Type.CATS);
        tiles[2][3] = new Tile(Type.CATS);

        tiles[0][4] = new Tile(Type.PLANTS);
        tiles[1][4] = new Tile(Type.PLANTS);
        tiles[2][4] = new Tile(Type.PLANTS);

        tiles[3][2] = new Tile(Type.CATS);
        tiles[4][2] = new Tile(Type.CATS);
        tiles[5][2] = new Tile(Type.CATS);

        tiles[3][3] = new Tile(Type.BOOKS);
        tiles[4][3] = new Tile(Type.BOOKS);
        tiles[5][3] = new Tile(Type.BOOKS);

        tiles[3][4] = new Tile(Type.FRAMES);
        tiles[4][4] = new Tile(Type.FRAMES);
        tiles[5][4] = new Tile(Type.FRAMES);

        bookshelf.setTiles(tiles);
        assertTrue(sixGroups2Tiles.check(bookshelf));
    }


    @Test
    public void check_fullBookShelfDesignedForNoMatch_shouldReturnFalse(){
        bookshelf = new Bookshelf();
        Tile[][] tiles = new Tile[6][5];

        //Filled it manually to have no tiles matching having the same type who are next to each other
        //in the same row or column

        tiles[0][0] = new Tile(Type.BOOKS);
        tiles[1][0] = new Tile(Type.PLANTS);
        tiles[2][0] = new Tile(Type.BOOKS);
        tiles[3][0] = new Tile(Type.CATS);
        tiles[4][0] = new Tile(Type.FRAMES);
        tiles[5][0] = new Tile(Type.PLANTS);

        tiles[0][1] = new Tile(Type.CATS);
        tiles[1][1] = new Tile(Type.BOOKS);
        tiles[2][1] = new Tile(Type.FRAMES);
        tiles[3][1] = new Tile(Type.PLANTS);
        tiles[4][1] = new Tile(Type.BOOKS);
        tiles[5][1] = new Tile(Type.CATS);

        tiles[0][2] = new Tile(Type.FRAMES);
        tiles[1][2] = new Tile(Type.CATS);
        tiles[2][2] = new Tile(Type.PLANTS);
        tiles[3][2] = new Tile(Type.BOOKS);
        tiles[4][2] = new Tile(Type.CATS);
        tiles[5][2] = new Tile(Type.FRAMES);

        tiles[0][3] = new Tile(Type.PLANTS);
        tiles[1][3] = new Tile(Type.FRAMES);
        tiles[2][3] = new Tile(Type.CATS);
        tiles[3][3] = new Tile(Type.FRAMES);
        tiles[4][3] = new Tile(Type.PLANTS);
        tiles[5][3] = new Tile(Type.BOOKS);

        tiles[0][4] = new Tile(Type.TROPHIES);
        tiles[1][4] = new Tile(Type.BOOKS);
        tiles[2][4] = new Tile(Type.PLANTS);
        tiles[3][4] = new Tile(Type.GAMES);
        tiles[4][4] = new Tile(Type.FRAMES);
        tiles[5][4] = new Tile(Type.CATS);

        bookshelf.setTiles(tiles);
        assertFalse(sixGroups2Tiles.check(bookshelf));
    }

    @Test
    public void check_fullBookShelfDesignedFor6Match_shouldReturnTrue(){

        bookshelf = new Bookshelf();
        Tile[][] tiles = new Tile[6][5];

        tiles[0][0] = new Tile(Type.BOOKS);
        tiles[1][0] = new Tile(Type.BOOKS);
        tiles[2][0] = new Tile(Type.BOOKS);
        tiles[3][0] = new Tile(Type.CATS);
        tiles[4][0] = new Tile(Type.FRAMES);
        tiles[5][0] = new Tile(Type.FRAMES);

        tiles[0][1] = new Tile(Type.CATS);
        tiles[1][1] = new Tile(Type.BOOKS);
        tiles[2][1] = new Tile(Type.FRAMES);
        tiles[3][1] = new Tile(Type.PLANTS);
        tiles[4][1] = new Tile(Type.BOOKS);
        tiles[5][1] = new Tile(Type.FRAMES);

        tiles[0][2] = new Tile(Type.CATS);
        tiles[1][2] = new Tile(Type.CATS);
        tiles[2][2] = new Tile(Type.PLANTS);
        tiles[3][2] = new Tile(Type.BOOKS);
        tiles[4][2] = new Tile(Type.CATS);
        tiles[5][2] = new Tile(Type.FRAMES);

        tiles[0][3] = new Tile(Type.PLANTS);
        tiles[1][3] = new Tile(Type.FRAMES);
        tiles[2][3] = new Tile(Type.CATS);
        tiles[3][3] = new Tile(Type.FRAMES);
        tiles[4][3] = new Tile(Type.PLANTS);
        tiles[5][3] = new Tile(Type.BOOKS);

        tiles[0][4] = new Tile(Type.PLANTS);
        tiles[1][4] = new Tile(Type.BOOKS);
        tiles[2][4] = new Tile(Type.CATS);
        tiles[3][4] = new Tile(Type.GAMES);
        tiles[4][4] = new Tile(Type.FRAMES);
        tiles[5][4] = new Tile(Type.BOOKS);

        bookshelf.setTiles(tiles);
        assertTrue(sixGroups2Tiles.check(bookshelf));
    }

    @Test
    public void check_fullBookShelfDesignedFor5Match_shouldReturnFalse(){

        bookshelf = new Bookshelf();
        Tile[][] tiles = new Tile[6][5];

        tiles[0][0] = new Tile(Type.BOOKS);
        tiles[1][0] = new Tile(Type.BOOKS);
        tiles[2][0] = new Tile(Type.BOOKS);
        tiles[3][0] = new Tile(Type.CATS);
        tiles[4][0] = new Tile(Type.FRAMES);
        tiles[5][0] = new Tile(Type.PLANTS);

        tiles[0][1] = new Tile(Type.CATS);
        tiles[1][1] = new Tile(Type.BOOKS);
        tiles[2][1] = new Tile(Type.FRAMES);
        tiles[3][1] = new Tile(Type.PLANTS);
        tiles[4][1] = new Tile(Type.BOOKS);
        tiles[5][1] = new Tile(Type.CATS);

        tiles[0][2] = new Tile(Type.CATS);
        tiles[1][2] = new Tile(Type.CATS);
        tiles[2][2] = new Tile(Type.PLANTS);
        tiles[3][2] = new Tile(Type.BOOKS);
        tiles[4][2] = new Tile(Type.CATS);
        tiles[5][2] = new Tile(Type.FRAMES);

        tiles[0][3] = new Tile(Type.PLANTS);
        tiles[1][3] = new Tile(Type.FRAMES);
        tiles[2][3] = new Tile(Type.CATS);
        tiles[3][3] = new Tile(Type.FRAMES);
        tiles[4][3] = new Tile(Type.PLANTS);
        tiles[5][3] = new Tile(Type.BOOKS);

        tiles[0][4] = new Tile(Type.PLANTS);
        tiles[1][4] = new Tile(Type.BOOKS);
        tiles[2][4] = new Tile(Type.CATS);
        tiles[3][4] = new Tile(Type.GAMES);
        tiles[4][4] = new Tile(Type.FRAMES);
        tiles[5][4] = new Tile(Type.BOOKS);

        bookshelf.setTiles(tiles);
        assertFalse(sixGroups2Tiles.check(bookshelf));
    }


    /**
     * MODIFIED THE 6 MATCHES CLASS JOINING TWO GROUPS OF MATCHES
     * IT MAKES A BIGGER ONE SO NOW THE MATCHES ARE 5
     */
    @Test
    public void check_fullBookShelfDesignedFor5MatchModified_shouldReturnFalse(){

        bookshelf = new Bookshelf();
        Tile[][] tiles = new Tile[6][5];

        tiles[0][0] = new Tile(Type.BOOKS);
        tiles[1][0] = new Tile(Type.BOOKS);
        tiles[2][0] = new Tile(Type.BOOKS);
        tiles[3][0] = new Tile(Type.CATS);
        tiles[4][0] = new Tile(Type.FRAMES);
        tiles[5][0] = new Tile(Type.FRAMES);

        tiles[0][1] = new Tile(Type.CATS);
        tiles[1][1] = new Tile(Type.BOOKS);
        tiles[2][1] = new Tile(Type.FRAMES);
        tiles[3][1] = new Tile(Type.PLANTS);
        tiles[4][1] = new Tile(Type.BOOKS);
        tiles[5][1] = new Tile(Type.FRAMES);

        tiles[0][2] = new Tile(Type.CATS);
        tiles[1][2] = new Tile(Type.CATS);

        //MODIFIED
        tiles[2][2] = new Tile(Type.CATS);

        tiles[3][2] = new Tile(Type.BOOKS);
        tiles[4][2] = new Tile(Type.CATS);
        tiles[5][2] = new Tile(Type.FRAMES);

        tiles[0][3] = new Tile(Type.PLANTS);
        tiles[1][3] = new Tile(Type.FRAMES);
        tiles[2][3] = new Tile(Type.CATS);
        tiles[3][3] = new Tile(Type.FRAMES);
        tiles[4][3] = new Tile(Type.PLANTS);
        tiles[5][3] = new Tile(Type.BOOKS);

        tiles[0][4] = new Tile(Type.PLANTS);
        tiles[1][4] = new Tile(Type.BOOKS);
        tiles[2][4] = new Tile(Type.CATS);
        tiles[3][4] = new Tile(Type.GAMES);
        tiles[4][4] = new Tile(Type.FRAMES);
        tiles[5][4] = new Tile(Type.BOOKS);

        bookshelf.setTiles(tiles);
        assertFalse(sixGroups2Tiles.check(bookshelf));
    }
}
