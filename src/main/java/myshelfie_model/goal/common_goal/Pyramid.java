package myshelfie_model.goal.common_goal;

import myshelfie_model.Tile;
import myshelfie_model.player.Bookshelf;

public class Pyramid extends CommonGoal {


    public Pyramid(int numberOfPlayers) {
        super(numberOfPlayers);
    }

    /**
     * Checks whether the player has 5 columns
     * with a decreasing or increasing length
     * @param b the player's bookshelf
     * @return an int: -1 if the goal wasn't achieved
     *                  otherwise the points of the Token in stack's first place
     */
    @Override
    public int check(Bookshelf b){

        boolean dooableFlag = true;
        int ColPrec = numTilesInColumn(b,0);
        int temp;

        //Check if the columns are in descending order
        for (int j = 1; j < 5 && dooableFlag; j++){
            temp = numTilesInColumn(b,j);
            if (ColPrec != temp + 1) {dooableFlag = false;}
            ColPrec = temp;
        }
        if(dooableFlag) {
            return popTokens();
        }


        dooableFlag = true;
        ColPrec = numTilesInColumn(b,4);

        //Check if the columns are in ascending order
        for (int j = 3; j >= 0 && dooableFlag; j--){
            temp = numTilesInColumn(b,j);
            if (ColPrec != temp + 1) {dooableFlag = false;}
            ColPrec = temp;
        }
        if (dooableFlag) {
            return popTokens();
        }
        else {
            return -1;
        }
    }

    //returns the number of tiles in the column
    private int numTilesInColumn(Bookshelf b,int c){
        Tile[][] tiles = b.getTiles();
        int numTiles = 0;

        for (Tile[] tile : tiles) {
            if (tile[c] != null) {
                numTiles++;
            }
        }
        return numTiles;
    }
}