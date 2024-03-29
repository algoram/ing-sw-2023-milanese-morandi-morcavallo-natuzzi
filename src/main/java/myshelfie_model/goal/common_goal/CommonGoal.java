package myshelfie_model.goal.common_goal;

import myshelfie_model.goal.Goal;
import myshelfie_model.goal.Token;
import myshelfie_model.player.Bookshelf;

import java.util.EmptyStackException;
import java.util.Stack;

public abstract class CommonGoal extends Goal {
    private final Stack<Token> scoringTokens;



    public abstract boolean check(Bookshelf b);

    /***
     * The function creates the Token's Stack
     * depending on the number of players
     * @param numberOfPlayers
     */
    public CommonGoal(int numberOfPlayers) {
        this.scoringTokens = new Stack<>();

        if (numberOfPlayers == 4) {this.scoringTokens.push (new Token(2));}
        this.scoringTokens.push(new Token(4));
        if (numberOfPlayers >= 3) {this.scoringTokens.push (new Token(6));}
        this.scoringTokens.push(new Token(8));

    }

    public Token popTokens(){
        try {
            return this.scoringTokens.pop();
        } catch (EmptyStackException e) {
            System.out.println("ScoringTokens è vuoto!");
            return null;
        }
    }

    public Token peekTokens() {
        return this.scoringTokens.peek();
    }
}
