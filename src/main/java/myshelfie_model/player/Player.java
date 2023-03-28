package myshelfie_model.player;

import myshelfie_model.goal.PersonalGoal;

public class Player {

    private int[] commonGoalsPoints;
    private Bookshelf bookshelf;
    private PersonalGoal personalGoal;

    public int getPersonalGoalPoints(){
        // TODO: implement method

        return 0;
    }

    public int getCommonGoalPoints(int commonGoalIndex) {
        return commonGoalsPoints[commonGoalIndex];
    }

    public void setCommonGoalPoints(int commonGoalIndex, int points) {
        if (commonGoalsPoints[commonGoalIndex] == -1) {
            return;
        }

        commonGoalsPoints[commonGoalIndex] = points;
    }

    public Bookshelf getBookshelf() {
        return bookshelf;
    }
}
