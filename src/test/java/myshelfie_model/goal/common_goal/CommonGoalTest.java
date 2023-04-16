package myshelfie_model.goal.common_goal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CommonGoalTest {

    CommonGoal commonGoal4 = null;
    CommonGoal commonGoal3 = null;
    CommonGoal commonGoal2 = null;


    @Before
    public void setUp() {
        //choose to test three different common goals their constructor is the same
        commonGoal4 = new Cross(4);
        commonGoal3 = new SixGroups2Tiles(3);
        commonGoal2 = new Pyramid(2);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testPopTokens_4Players() {
        assertTrue(commonGoal4.popTokens().getPoints() == 8);
        assertTrue(commonGoal4.popTokens().getPoints() == 6);
        assertTrue(commonGoal4.popTokens().getPoints() == 4);
        assertTrue(commonGoal4.popTokens().getPoints() == 2);
        assertEquals(commonGoal4.popTokens(), null);
    }

    @Test
    public void testPopTokens_3Players() {
        assertTrue(commonGoal3.popTokens().getPoints() == 8);
        assertTrue(commonGoal3.popTokens().getPoints() == 6);
        assertTrue(commonGoal3.popTokens().getPoints() == 4);
        assertEquals(commonGoal3.popTokens(), null);
    }

    @Test
    public void testPopTokens_2Players() {
        assertTrue(commonGoal2.popTokens().getPoints() == 8);
        assertTrue(commonGoal2.popTokens().getPoints() == 4);
        assertEquals(commonGoal2.popTokens(), null);
    }
}
