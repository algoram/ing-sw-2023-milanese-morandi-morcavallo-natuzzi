package myshelfie_model.goal.common_goal;

import myshelfie_model.Tile;
import myshelfie_model.Type;
import myshelfie_model.player.Bookshelf;

import java.util.HashSet;

public class FourCorners extends CommonGoal {

    public FourCorners(int numberOfPlayers) {super(numberOfPlayers);}


    /**
     * Checks whether the player has the four corners of
     * the bookshelf with tiles of the same type
     * @param b the player's bookshelf
     * @return an int: -1 if the goal wasn't achieved
     *                  otherwise the points of the Token in stack's first place
     */
    @Override
    public boolean check(Bookshelf b) {

        Tile[][] tiles = b.getTiles();
        HashSet<Type> typesCounter = new HashSet<>();

        if( tiles[0][0]== null || tiles[0][tiles[0].length-1] == null ||
                tiles[tiles.length-1][0] == null || tiles[tiles.length-1][tiles[0].length-1] == null){
            return false;
        }

        typesCounter.add(tiles[0][0].getType());
        typesCounter.add(tiles[0][tiles[0].length-1].getType());
        typesCounter.add(tiles[tiles.length-1][0].getType());
        typesCounter.add(tiles[tiles.length-1][tiles[0].length-1].getType());

        if( typesCounter.size() == 1 ){
            return true;
        }
        else {
            return false;
        }

    }
}