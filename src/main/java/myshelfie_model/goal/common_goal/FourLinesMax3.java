package myshelfie_model.goal.common_goal;


import myshelfie_model.Tile;
import myshelfie_model.Type;
import myshelfie_model.player.Bookshelf;

import java.util.HashSet;
import java.util.Set;

public class FourLinesMax3 extends CommonGoal{
    public FourLinesMax3(int numberOfPlayers) {
        super(numberOfPlayers);
    }

    /**
     * Checks whether the player has four rows on the bookshelf,
     * where each row is formed by 5 tiles of one, two, or three different types.
     *
     *
     * @param b the player's bookshelf
     * @return true if the goal is achieved, false otherwise
     */



    public boolean check(Bookshelf b) {
        Tile[][] tiles = b.getTiles();


        int validRows = 0;

        for (int i = 0; i < tiles.length; i++) {
            HashSet<Type> type = new HashSet<Type>();
            boolean valid = true;
            for (int j = 0; j < tiles[i].length && valid; j++) {
                if (tiles[i][j] != null) {
                    type.add(tiles[i][j].getType());
                } else {
                    valid = false;

                }
            }
            if (valid && type.size() <= 3) {
                validRows++;
            }
        }

        if (validRows >= 3) {
            return true;
        } else {
            return false;
        }
    }
}
