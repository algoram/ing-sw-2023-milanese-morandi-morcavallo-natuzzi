package myshelfie_model.goal;

import myshelfie_model.Tile;
import myshelfie_model.Type;
import myshelfie_model.player.Bookshelf;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PersonalGoalTest {


    Bookshelf bookshelf = null;
    PersonalGoal Card = null;

    /**
     * This method builds a bookshelf with the given configuration.
     * @param tiles: the bookshelf to be built
     * @param BOOKSHELF_SETUP: the configuration of the bookshelf
     */

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
                    case 8 -> tiles[i][j] = new Tile(Type.values()[((int) (Math.random() * 5)+1)]);
                    default -> tiles[i][j] = null;
                }
            }
        }
    }

    /**
     * This method inverts the rows of a matrix.
     * It is used to create the conventional configuration of the bookshelf .
     * @param matrix the matrix to be inverted
     * @return the inverted matrix
     * */
    private int[][] invertRows(int[][] matrix) {
        int numRows = matrix.length;

        for (int i = 0; i < numRows / 2; i++) {
            int[] temp = matrix[i];
            matrix[i] = matrix[numRows - i - 1];
            matrix[numRows - i - 1] = temp;
        }
        return matrix;
    }

    @Before
    public void setup() {
        bookshelf = new Bookshelf();

        // random number of players from 2 to 4 no need of setting it up so far
        // create a random number of players from 2 to 4  -> 2, 3, 4
        int numberOfPlayers = (int) (Math.random() * 3) + 2;
        //System.out.println("Number of players: " + numberOfPlayers);
        Card = new PersonalGoal(numberOfPlayers);
    }


    @After
    public void tearDown() {
    }

    @Test
    public void check_emptyBookShelf_ShouldReturn0() {
        Tile[][] tiles = new Tile[6][5];
        bookshelf.setTiles(tiles);

        for (int i = 1; i <= 12; i++) {
            Card= new PersonalGoal(i);
            int points = Card.getPersonalGoalPoints(bookshelf);
            assertEquals(0, points);
        }
    }

    /**
     * This test checks if the PersonalGoal with card: 1 is able to return the correct number of points
     * when the bookshelf is full of tiles, in many different configurations.
     * */
    @Test
    public void check_FullBookShelf_Card1_ShouldReturn12() {
        Card = new PersonalGoal(1);
        Tile[][] tiles = new Tile[6][5];
        /*    null:     0
        *     CATS:     1 verde
        *     BOOKS:    2 bianco
        *     GAMES:    3 giallo
        *     FRAMES:   4 blu
        *     TROPHIES: 5 azzurro
        *     PLANTS:   6 rosa
        *     random:   8
        */
        //6 tiles
        int[][] BOOKSHELF_SETUP_6 = {
                {6,8,4,8,8},    //row:0
                {8,8,8,8,1},    //row:1
                {8,8,8,2,8},    //row:2
                {8,3,8,8,8},    //row:3
                {8,8,8,8,8},    //row:4
                {8,8,5,8,8},    //row:5
        };
        buildBookshelf(tiles, invertRows(BOOKSHELF_SETUP_6));
        bookshelf.setTiles(tiles);
        assertEquals(12, Card.getPersonalGoalPoints(bookshelf));


        //5 tiles
        //substituted tiles[0][0] with another different type
        int[][] BOOKSHELF_SETUP_5 = {
                {3,8,4,8,8},    //row:0
                {8,8,8,8,1},    //row:1
                {8,8,8,2,8},    //row:2
                {8,3,8,8,8},    //row:3
                {8,8,8,8,8},    //row:4
                {8,8,5,8,8},    //row:5
        };
        buildBookshelf(tiles, invertRows(BOOKSHELF_SETUP_5));
        bookshelf.setTiles(tiles);
        assertEquals(9, Card.getPersonalGoalPoints(bookshelf));

        //4 tiles
        //substituted tiles[0][2] with another different type
        final int[][] BOOKSHELF_SETUP_4 = {
                {3,8,5,8,8},    //row:0
                {8,8,8,8,1},    //row:1
                {8,8,8,2,8},    //row:2
                {8,3,8,8,8},    //row:3
                {8,8,8,8,8},    //row:4
                {8,8,5,8,8},    //row:5
        };
        buildBookshelf(tiles, invertRows(BOOKSHELF_SETUP_4));
        bookshelf.setTiles(tiles);
        assertEquals(6, Card.getPersonalGoalPoints(bookshelf));

        //3 tiles
        //substituted tiles[1][4] with another different type
        final int[][] BOOKSHELF_SETUP_3 = {
                {3,8,5,8,8},    //row:0
                {8,8,8,8,2},    //row:1
                {8,8,8,2,8},    //row:2
                {8,3,8,8,8},    //row:3
                {8,8,8,8,8},    //row:4
                {8,8,5,8,8},    //row:5
        };
        buildBookshelf(tiles, invertRows(BOOKSHELF_SETUP_3));
        bookshelf.setTiles(tiles);
        assertEquals(4, Card.getPersonalGoalPoints(bookshelf));

        //2 tiles
        //substituted tiles[2][3] with another different type
        final int[][] BOOKSHELF_SETUP_2 = {
                {3,8,5,8,8},    //row:0
                {8,8,8,8,2},    //row:1
                {8,8,8,3,8},    //row:2
                {8,3,8,8,8},    //row:3
                {8,8,8,8,8},    //row:4
                {8,8,5,8,8},    //row:5
        };
        buildBookshelf(tiles, invertRows(BOOKSHELF_SETUP_2));
        bookshelf.setTiles(tiles);
        assertEquals(2, Card.getPersonalGoalPoints(bookshelf));

        //1 tiles
        //substituted tiles[3][1] with another different type
        final int[][] BOOKSHELF_SETUP_1 = {
                {3,8,5,8,8},    //row:0
                {8,8,8,8,2},    //row:1
                {8,8,8,3,8},    //row:2
                {8,4,8,8,8},    //row:3
                {8,8,8,8,8},    //row:4
                {8,8,5,8,8},    //row:5
        };
        buildBookshelf(tiles, invertRows(BOOKSHELF_SETUP_1));
        bookshelf.setTiles(tiles);
        assertEquals(1, Card.getPersonalGoalPoints(bookshelf));

        //0 tiles
        //substituted tiles[5][2] with another different type
        final int[][] BOOKSHELF_SETUP_0 = {
                {3,8,5,8,8},    //row:0
                {8,8,8,8,2},    //row:1
                {8,8,8,3,8},    //row:2
                {8,4,8,8,8},    //row:3
                {8,8,8,8,8},    //row:4
                {8,8,6,8,8},    //row:5
        };
        buildBookshelf(tiles, invertRows(BOOKSHELF_SETUP_0));
        bookshelf.setTiles(tiles);
        assertEquals(0, Card.getPersonalGoalPoints(bookshelf));



    }

}
