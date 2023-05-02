package myshelfie_model.player;

import myshelfie_model.Score;
import myshelfie_model.goal.PersonalGoal;
import myshelfie_model.goal.Token;

public class Player {

    private Bookshelf bookshelf; //from here you can take the adjacent score
    private PersonalGoal personalGoal; //from here you can take the personal score
    private String username;

    private boolean finishedFirst; //if the player finished first he gets 1 point, otherwise 0

    private Token[] commonGoalsToken; //from here you can take the common score

    public Player(String username, PersonalGoal personalGoal) {
        this.username = username;

        bookshelf = new Bookshelf();

        commonGoalsToken = new Token[2];
        finishedFirst = false;

        this.personalGoal = personalGoal;
    }

    public String getUsername() {
        return username;
    }

    public int getPersonalGoalPoints() {
        return personalGoal.getPersonalGoalPoints(bookshelf);
    }

    public Token[] getCommonTokens() {
        return commonGoalsToken;
    }

    public void setCommonToken(int i, Token token) {
        commonGoalsToken[i] = token;
    }

    public void setFinishedFirst(boolean finishedFirst) {
        this.finishedFirst = finishedFirst;
    }

    public Bookshelf getBookshelf() {
        return bookshelf;
    }
}
