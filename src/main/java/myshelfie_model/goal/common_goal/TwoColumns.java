package myshelfie_model.goal.common_goal;



import myshelfie_model.Tile;
import myshelfie_model.Type;
import myshelfie_model.player.Bookshelf;
import java.util.HashSet;
import java.util.Set;


public class TwoColumns extends CommonGoal{
    public TwoColumns(int numberOfPlayers) {
        super(numberOfPlayers);
    }

    public boolean check(Bookshelf b) {
        Tile[][] tiles = b.getTiles();
        int numColsSameSixTiles = 0;

        for (int j = 0; j < tiles[0].length; j++) {
            HashSet<Type> types = new HashSet<Type>();

            boolean valid = true;

            for (int i = 0; i < tiles.length; i++) {
                Type t = tiles[i][j].getType();
                if (types.contains(t)) {
                    valid = false;
                    break;
                }

                types.add(t);
            }

            if (valid) {
                numColsSameSixTiles++;
            }
        }

        if (numColsSameSixTiles >= 2) {
            return true;
        }

        return false;
    }


}
