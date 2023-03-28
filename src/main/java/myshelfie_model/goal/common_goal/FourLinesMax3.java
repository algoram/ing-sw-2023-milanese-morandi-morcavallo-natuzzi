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

    public int check(Bookshelf b) {
        Tile[][] tiles = b.getTiles();
        int score = 0;

        int validRows = 0;

        for (int i = 0; i < tiles.length; i++) {
            Set<Type> type = new HashSet<Type>();
            boolean valid = true;
            for (int j = 0; j < tiles[i].length; j++) {
                Type types = tiles[i][j].getType();
                if (!type.contains(types)) {
                    type.add(types);
                } else {
                    valid = false;
                    break;
                }
            }

            if (valid) {
                validRows++;
            }

        }

        if (validRows >= 4) {
            return popTokens();
        }
        return -1;


}
