package myshelfie_model.goal.common_goal;

import myshelfie_model.goal.Goal;

public abstract class CommonGoal extends Goal {
    private Stack<Integer> scoringTokens;


    //retrun an integer from -1 to 4(-1 means CommonGaol not satisfied)
    public abstract int check(Bookshelf b);

    public ScoringTokensStack() {
        this.scoringTokens = new Stack<>();
        this.scoringTokens.push(4);
        this.scoringTokens.push(3);
        this.scoringTokens.push(2);
        this.scoringTokens.push(1);
    }

    private int popTokens(int){
        try {
            return this.scoringTokens.pop();
        } catch (EmptyStackException e) {
            System.out.println("ScoringTokens è vuoto!");
            return -1;
        }
    }
}
