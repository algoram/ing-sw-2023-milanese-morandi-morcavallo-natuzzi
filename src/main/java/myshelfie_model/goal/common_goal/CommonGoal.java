package myshelfie_model.goal.common_goal;

import myshelfie_model.goal.Goal;
import myshelfie_model.player.Bookshelf;

import java.util.EmptyStackException;
import java.util.Stack;

public abstract class CommonGoal extends Goal {
    private Stack<Integer> scoringTokens;


    //retrun an integer from -1 to 4(-1 means CommonGaol not satisfied)
    public abstract int check(Bookshelf b);

    public CommonGoal(int numberOfPlayers) {
        // TODO: creare lo stack con i punti giusti (2, 4, 6, 8) ed in base al numero di giocatori
        this.scoringTokens = new Stack<>();
        this.scoringTokens.push(4);
        this.scoringTokens.push(3);
        this.scoringTokens.push(2);
        this.scoringTokens.push(1);
    }

    protected int popTokens(){
        try {
            return this.scoringTokens.pop();
        } catch (EmptyStackException e) {
            System.out.println("ScoringTokens è vuoto!");
            return -1;
        }
    }
}
