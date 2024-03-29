package myshelfie_model.goal.common_goal;



import myshelfie_model.Tile;
import myshelfie_model.Type;
import myshelfie_model.player.Bookshelf;
import java.util.HashSet;
import java.util.Set;


public class TwoColumns extends CommonGoal {
    public TwoColumns(int numberOfPlayers) {
        super(numberOfPlayers);
    }

    /**
     * Checks whether the player has two columns on the bookshelf, where each column is formed by six different types of tiles.
     *
     * @param b the player's bookshelf
     * @return true if the goal is achieved, false otherwise
     */


    @Override
    public boolean check(Bookshelf b) {
        Tile[][] tiles = b.getTiles();
        int numColsSameSixTiles = 0;

        for (int j = 0; j < tiles[0].length; j++) {
            HashSet<Type> types = new HashSet<Type>();

            boolean valid = true;

            for (int i = 0; i < tiles.length; i++) {
                if (tiles[i][j] != null) {
                    types.add(tiles[i][j].getType());
                } else {
                    valid = false;
                    break;
                }
            }

            if (valid && types.size() == 6) {
                numColsSameSixTiles++;
            }
        }

        if (numColsSameSixTiles >= 2){
            return true;
        }
        return false;
    }

}
