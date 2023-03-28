package myshelfie_model.goal.common_goal;

import myshelfie_model.goal.Goal;
import myshelfie_model.goal.Token;
import myshelfie_model.player.Bookshelf;

import java.util.EmptyStackException;
import java.util.Stack;

public abstract class CommonGoal extends Goal {
    private final Stack<Token> scoringTokens;


    //return an integer from -1 to 4(-1 means CommonGaol not satisfied)
    public abstract boolean check(Bookshelf b);

    public CommonGoal(int numberOfPlayers) {
        this.scoringTokens = new Stack<>();

        this.scoringTokens.push(new Token(8));
        if (numberOfPlayers >= 3) {new Token(6);}
        this.scoringTokens.push(new Token(4));
        if (numberOfPlayers == 4) {new Token(2);}
    }

    protected Token popTokens(){
        try {
            return this.scoringTokens.pop();
        } catch (EmptyStackException e) {
            System.out.println("ScoringTokens è vuoto!");
            return null;
        }
    }
}
