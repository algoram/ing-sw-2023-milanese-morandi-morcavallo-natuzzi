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
        this.scoringTokens = new Stack<>();

        this.scoringTokens.push(8);
        if (numberOfPlayers >= 3) {this.scoringTokens.push(6);}
        this.scoringTokens.push(4);
        if (numberOfPlayers == 4) {this.scoringTokens.push(2);}
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
