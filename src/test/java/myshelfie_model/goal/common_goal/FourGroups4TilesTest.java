package myshelfie_model.goal.common_goal;

import myshelfie_model.player.Bookshelf;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FourGroups4TilesTest {

    Bookshelf bookshelf = null;
    CommonGoal fourGroups4Tiles = null;

    @Before
    public void setup(){
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

    @Test
    public void testCheck() {
    }

}
