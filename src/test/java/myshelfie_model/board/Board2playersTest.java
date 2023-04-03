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
        for (int i = 0; i < 29; i++) {
            tiles.add(new Tile(Type.getRandomType()));
        }
        board.refill(tiles);
        assertEquals(10, tiles.size());
    }

}
