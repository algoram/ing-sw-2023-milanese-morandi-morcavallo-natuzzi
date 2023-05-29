package myshelfie_model;

import myshelfie_model.board.Board2players;
import myshelfie_model.board.Board3players;
import myshelfie_model.board.Board4players;
import myshelfie_model.goal.PersonalGoal;
import myshelfie_model.goal.common_goal.CommonGoal;
import myshelfie_model.player.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {

    private Game game = null;

    @Before
    public void setUp() {
        game = new Game();
    }

    @After
    public void tearDown() {}

    @Test
    public void startGame_2players_correctState() {
        game.startGame(2);

        // check that the number of players is correct
        assertEquals("Correct number of players", 2, game.getNumberOfPlayers());

        // check that the bag has the right amount of tiles
        assertEquals("Correct number of tiles inside the bag", 132 - 29, game.getBag().size());

        // check that the board is of the correct type
        assertTrue("Correct type of board", game.getBoard() instanceof Board2players);

        // check that the common goals are created and are different from each other
        CommonGoal cg1 = game.getCommonGoals()[0];
        CommonGoal cg2 = game.getCommonGoals()[1];

        assertNotNull("Common goal 1 created", cg1);
        assertNotNull("Common goal 2 created", cg2);

        assertNotEquals("Common goals are different", cg1.getClass(), cg2.getClass());

        // check that the player seat is correct
        assertTrue("Correct player seat number", game.getPlayerSeatIndex() >= 0 && game.getPlayerSeatIndex() <= 1);

        // check that no one has finished yet
        assertNull("No one has finished yet", game.getFinishedFirst());
    }

    @Test
    public void startGame_3players_correctState() {
        game.startGame(3);

        // check that the number of players is correct
        assertEquals("Correct number of players", 3, game.getNumberOfPlayers());

        // check that the bag has the right amount of tiles
        assertEquals("Correct number of tiles inside the bag", 132 - 37, game.getBag().size());

        // check that the board is of the correct type
        assertTrue("Correct type of board", game.getBoard() instanceof Board3players);

        // check that the common goals are created and are different from each other
        CommonGoal cg1 = game.getCommonGoals()[0];
        CommonGoal cg2 = game.getCommonGoals()[1];

        assertNotNull("Common goal 1 created", cg1);
        assertNotNull("Common goal 2 created", cg2);

        assertNotEquals("Common goals are different", cg1.getClass(), cg2.getClass());

        // check that the player seat is correct
        assertTrue("Correct player seat number", game.getPlayerSeatIndex() >= 0 && game.getPlayerSeatIndex() <= 2);

        // check that no one has finished yet
        assertNull("No one has finished yet", game.getFinishedFirst());
    }

    @Test
    public void startGame_4players_correctState() {
        game.startGame(4);

        // check that the number of players is correct
        assertEquals("Correct number of players", 4, game.getNumberOfPlayers());

        // check that the bag has the right amount of tiles
        assertEquals("Correct number of tiles inside the bag", 132 - 45, game.getBag().size());

        // check that the board is of the correct type
        assertTrue("Correct type of board", game.getBoard() instanceof Board4players);

        // check that the common goals are created and are different from each other
        CommonGoal cg1 = game.getCommonGoals()[0];
        CommonGoal cg2 = game.getCommonGoals()[1];

        assertNotNull("Common goal 1 created", cg1);
        assertNotNull("Common goal 2 created", cg2);

        assertNotEquals("Common goals are different", cg1.getClass(), cg2.getClass());

        // check that the player seat is correct
        assertTrue("Correct player seat number", game.getPlayerSeatIndex() >= 0 && game.getPlayerSeatIndex() <= 3);

        // check that no one has finished yet
        assertNull("No one has finished yet", game.getFinishedFirst());
    }

    @Test
    public void addPlayer_2differentPlayers_correctState() {
        game.startGame(2);

        // check that all insertions go well
        try {
            assertTrue("Adding first player", game.addPlayer("1"));
            assertTrue("Adding second player", game.addPlayer("2"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // check that all players have a personal goal
        Player p1 = game.getPlayers().get(0);
        Player p2 = game.getPlayers().get(1);

        assertNotNull("Player 1 personal goal created", p1.getPersonalGoal());
        assertNotNull("Player 2 personal goal created", p2.getPersonalGoal());

        // check that all personal goals are different from each other
        assertNotEquals("Different personal goals", p1.getPersonalGoal(), p2.getPersonalGoal());
    }

}
