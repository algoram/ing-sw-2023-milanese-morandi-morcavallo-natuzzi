package myshelfie_model.goal.common_goal;

import myshelfie_model.Tile;
import myshelfie_model.Type;
import myshelfie_model.player.Bookshelf;

import java.util.HashSet;

public class TwoLines extends CommonGoal {


    /**
     * Checks whether the player has two lines with
     * all different types of tile
     * @param b the player's bookshelf
     * @return a int: -1 if the goal wasn't achieved
     *                  otherwise the points of the Token in stack's first place
     */
    @Override
    public int check(Bookshelf b){
        Tile[][] tiles = b.getTiles();

        HashSet<Type> typesCounter = new HashSet<Type>();
        int tilesCounter;
        int numberOfCorrectLines = 0;

        // we iterate each raw
        for (int i = 0; i < tiles.length; i++) {

            typesCounter.clear(); // clear the set from previous iterations
            tilesCounter = 0; //Set the number of tiles in the row to zero

            // we add the types to the set
            for (int j = 0; j < tiles[0].length; j++){
                if (tiles[i][j] != null) {
                    tilesCounter++;
                    typesCounter.add(tiles[i][j].getType());
                }
            }

            // check if we have exactly 5 tiles of different types in the line
            if (tilesCounter == 5 && typesCounter.size() == 5) {
                numberOfCorrectLines++;
            }
        }

        if (numberOfCorrectLines >= 2) {
            return popTokens();
        } else {
            return -1;
        }
    }
}