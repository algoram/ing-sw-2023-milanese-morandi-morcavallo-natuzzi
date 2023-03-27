package myshelfie_model.goal.common_goal;

import myshelfie_model.Tile;
import myshelfie_model.Type;
import myshelfie_model.player.Bookshelf;

import java.util.HashSet;

public class ThreeColumnsMax3 extends CommonGoal {
    public ThreeColumnsMax3(int numberOfPlayers) {
        super(numberOfPlayers);
    }

    @Override
    public int check(Bookshelf b) {
        Tile[][] tiles = b.getTiles(); // tiles we'll be checking

        HashSet<Type> typesCounter = new HashSet<Type>(); // set used to count unique types

        int numberOfCorrectColumns = 0;

        // we iterate each column
        for (int j = 0; j < tiles[0].length; j++) {
            typesCounter.clear(); // clear the set from previous iterations

            boolean columnComplete = true; // flag that indicates if a column is full

            // we add the types to the set
            for (int i = 0; i < tiles.length && columnComplete; i++) {
                if (tiles[i][j] != null) {
                    typesCounter.add(tiles[i][j].getType());
                } else {
                    columnComplete = false;
                }
            }

            // check if we have a maximum of three types in the column
            if (columnComplete && typesCounter.size() <= 3) {
                numberOfCorrectColumns++;
            }
        }

        if (numberOfCorrectColumns >= 3) {
            return popTokens();
        } else {
            return -1;
        }
    }
}
