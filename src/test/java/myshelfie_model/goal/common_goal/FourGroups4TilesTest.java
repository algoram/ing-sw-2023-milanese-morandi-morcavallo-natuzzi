package myshelfie_model.goal.common_goal;

import myshelfie_model.Tile;
import myshelfie_model.Type;
import myshelfie_model.player.Bookshelf;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FourGroups4TilesTest extends CommonGoalTest {

    Bookshelf bookshelf = null;
    CommonGoal fourGroups4Tiles = null;

    @Before
    public void setup() {
        bookshelf = new Bookshelf();

        // random number of players from 2 to 4 no need of setting it up so far
        // create a random number of players from 2 to 4  -> 2, 3, 4
        int numberOfPlayers = (int) (Math.random() * 3) + 2;
        //System.out.println("Number of players: " + numberOfPlayers);
        fourGroups4Tiles = new FourGroups4Tiles(numberOfPlayers);
    }


    @After
    public void tearDown() {
    }


    //1. check empty bookshelf
    //2. check full bookshelf with no match among tiles
    //3. check full bookshelf with 3 groups of 4 tiles
    //4. check full bookshelf with 4 groups of 4 tiles
    //5. check full bookshelf with 5 groups of 4 tiles

    @Test
    public void check_emptyBookShelf_ShouldReturnFalse() {

        Tile[][] tiles = new Tile[6][5];

        bookshelf.setTiles(tiles);
        assertFalse(fourGroups4Tiles.check(bookshelf));

    }

    @Test
    public void check_fullBookShelfNoMatch_ShouldReturnFalse() {

        Tile[][] tiles = new Tile[6][5];

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
        assertFalse(fourGroups4Tiles.check(bookshelf));

    }


    @Test
    public void check_FullBookShelf3Groups_ShouldReturnFalse() {

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
        assertFalse(fourGroups4Tiles.check(bookshelf));

    }

    @Test
    public void check_FullBookShelf4Groups_ShouldReturnTrue() {

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
        tiles[2][2] = new Tile(Type.CATS);
        tiles[3][2] = new Tile(Type.BOOKS);
        tiles[4][2] = new Tile(Type.CATS);
        tiles[5][2] = new Tile(Type.FRAMES);

        tiles[0][3] = new Tile(Type.PLANTS);
        tiles[1][3] = new Tile(Type.PLANTS);
        tiles[2][3] = new Tile(Type.CATS);
        tiles[3][3] = new Tile(Type.FRAMES);
        tiles[4][3] = new Tile(Type.PLANTS);
        tiles[5][3] = new Tile(Type.BOOKS);

        tiles[0][4] = new Tile(Type.PLANTS);
        tiles[1][4] = new Tile(Type.PLANTS);
        tiles[2][4] = new Tile(Type.CATS);
        tiles[3][4] = new Tile(Type.GAMES);
        tiles[4][4] = new Tile(Type.FRAMES);
        tiles[5][4] = new Tile(Type.BOOKS);


        for (int i = tiles.length - 1; i >= 0; i--) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (tiles[i][j] == null)
                    System.out.print("N ");
                else if (tiles[i][j].getType() == Type.BOOKS) {
                    System.out.print("B ");
                } else if (tiles[i][j].getType() == Type.CATS) {
                    System.out.print("C ");
                } else if (tiles[i][j].getType() == Type.FRAMES) {
                    System.out.print("F ");
                } else if (tiles[i][j].getType() == Type.PLANTS) {
                    System.out.print("P ");
                } else if (tiles[i][j].getType() == Type.TROPHIES) {
                    System.out.print("T ");
                } else if (tiles[i][j].getType() == Type.GAMES) {
                    System.out.print("G ");
                }
            }
            System.out.println();
        }

        bookshelf.setTiles(tiles);
        assertTrue(fourGroups4Tiles.check(bookshelf));
    }


    @Test
    public void check_FullBookshelfSameType_ShouldReturnFalse(){
        Tile[][] tiles = new Tile[6][5];

        for(int i = 0; i < tiles.length; i++){
            for(int j = 0; j < tiles[0].length; j++){
                tiles[i][j] = new Tile(Type.BOOKS);
            }
        }

        bookshelf.setTiles(tiles);
        assertFalse(fourGroups4Tiles.check(bookshelf));
    }



    @Test
    public void check_FullBookShelf5Groups_ShouldReturnTrue() {

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
        tiles[2][2] = new Tile(Type.CATS);
        tiles[3][2] = new Tile(Type.TROPHIES);
        tiles[4][2] = new Tile(Type.TROPHIES);
        tiles[5][2] = new Tile(Type.FRAMES);

        tiles[0][3] = new Tile(Type.PLANTS);
        tiles[1][3] = new Tile(Type.PLANTS);
        tiles[2][3] = new Tile(Type.CATS);
        tiles[3][3] = new Tile(Type.TROPHIES);
        tiles[4][3] = new Tile(Type.TROPHIES);
        tiles[5][3] = new Tile(Type.BOOKS);

        tiles[0][4] = new Tile(Type.PLANTS);
        tiles[1][4] = new Tile(Type.PLANTS);
        tiles[2][4] = new Tile(Type.CATS);
        tiles[3][4] = new Tile(Type.TROPHIES);
        tiles[4][4] = new Tile(Type.FRAMES);
        tiles[5][4] = new Tile(Type.BOOKS);


        for (int i = tiles.length - 1; i >= 0; i--) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (tiles[i][j] == null)
                    System.out.print("N ");
                else if (tiles[i][j].getType() == Type.BOOKS) {
                    System.out.print("B ");
                } else if (tiles[i][j].getType() == Type.CATS) {
                    System.out.print("C ");
                } else if (tiles[i][j].getType() == Type.FRAMES) {
                    System.out.print("F ");
                } else if (tiles[i][j].getType() == Type.PLANTS) {
                    System.out.print("P ");
                } else if (tiles[i][j].getType() == Type.TROPHIES) {
                    System.out.print("T ");
                } else if (tiles[i][j].getType() == Type.GAMES) {
                    System.out.print("G ");
                }
            }
            System.out.println();
        }

        bookshelf.setTiles(tiles);
        assertTrue(fourGroups4Tiles.check(bookshelf));
    }

}