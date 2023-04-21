package myshelfie_model.player;

import myshelfie_model.goal.PersonalGoal;
import myshelfie_model.goal.Token;

public class Player {

    private Token[] commonGoalsToken;
    private Bookshelf bookshelf;
    private PersonalGoal personalGoal;
    private String username;

    public Player(String username, PersonalGoal personalGoal) {
        this.username = username;

        commonGoalsToken = new Token[2];
        bookshelf = new Bookshelf();

        this.personalGoal = personalGoal;
    }

    public String getUsername() {
        return username;
    }

    public int getPersonalGoalPoints(){
        return personalGoal.getPersonalGoalPoints(bookshelf);
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
