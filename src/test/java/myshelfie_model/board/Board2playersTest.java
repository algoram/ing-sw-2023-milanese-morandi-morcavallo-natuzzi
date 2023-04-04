package myshelfie_model.board;

import myshelfie_model.Tile;
import myshelfie_model.Type;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class Board2playersTest {

    Board board;

    @Before
    public void setUp() {
        board = new Board2players();
    }

    @After
    public void tearDown() {
        board = null;
    }


    // To be verified
    @Test
    public void testRefill_() {
        List<Tile> tiles = new ArrayList<>();
        Tile[][] stateboard;

        for (int i = 0; i < 29; i++) {
            tiles.add(new Tile(Type.getRandomType()));
        }

        board.refill(tiles);
        assertEquals(0, tiles.size());
        stateboard = board.getBoard();
        for (int i = 8; i >= 0; i--) {
            for (int j = 0; j < 9; j++) {
                if (stateboard[i][j] != null){
                    System.out.print(" * ");
                } else {
                    System.out.print(" N ");
                }
            }
            System.out.print("\n");
        }
    }

}
