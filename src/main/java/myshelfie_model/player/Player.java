package myshelfie_model.player;

import myshelfie_model.goal.PersonalGoal;
import myshelfie_model.goal.Token;

public class Player {

    private Token[] commonGoalsToken;
    private Bookshelf bookshelf;
    private PersonalGoal personalGoal;

    public int getPersonalGoalPoints(){
        // TODO: implement method

        return 0;
    }

    public Token getCommonGoalPoints(int commonGoalIndex) {
        return commonGoalsToken[commonGoalIndex];
    }

    public void setCommonGoalToken(int commonGoalIndex,Token token) {

        if (commonGoalsToken[commonGoalIndex] == null) {
            return;
        }
        commonGoalsToken[commonGoalIndex] = token;
    }

    public Bookshelf getBookshelf() {
        return bookshelf;
    }
}
