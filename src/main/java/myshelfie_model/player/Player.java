package myshelfie_model.player;

import myshelfie_model.goal.PersonalGoal;
import myshelfie_model.goal.Token;

public class Player {

    private Bookshelf bookshelf;
    private PersonalGoal personalGoal;
    private String username;

    private boolean finishedFirst; //if the player finished first he gets 1 point, otherwise 0

    private Token[] commonGoalsToken;

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

    public boolean getFinishedFirst() {
        return finishedFirst;
    }
    public Bookshelf getBookshelf() {
        return bookshelf;
    }

    public int getAdjacentPoints() {
        return bookshelf.getPoints();
    }

    public PersonalGoal getPersonalGoal() {
        return personalGoal;
    }

    public int getCommonGoalPoints() {
        int points = 0;
        for (Token token : commonGoalsToken) {
            points += token.getPoints();
        }
        return points;
    }
}
