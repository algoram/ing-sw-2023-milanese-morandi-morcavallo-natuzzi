package myshelfie_model.goal.common_goal;

import myshelfie_model.Tile;
import myshelfie_model.Type;
import myshelfie_model.player.Bookshelf;

import java.util.HashMap;

public class EightTiles extends CommonGoal {

    public EightTiles(int numberOfPlayers) {
        super(numberOfPlayers);
    }

    /**
     * Checks whether the player has eight tiles of the same
     * type on the bookshelf
     * @param b the player's bookshelf
     * @return a boolean indicating whether is goal is achieved
     */
    @Override
    public boolean check(Bookshelf b) {
        HashMap<Type, Integer> counter = new HashMap<Type, Integer>();

        Tile[][] tiles = b.getTiles();

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (tiles[i][j] != null) {
                    Type type = tiles[i][j].getType();

                    if (!counter.containsKey(type)) {
                        counter.put(type, 1);
                    } else {
                        counter.put(type, counter.get(type) + 1);
                    }
                }
            }
        }

        for (Type t : counter.keySet()) {
            if (counter.get(t) >= 8) {
                return true;
            }
        }

        return false;
    }
}
