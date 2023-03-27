
public class Pyramid extends CommonGoal {

    @Override
    public int check(Bookshelf b){

        boolean dooableFlag = true;
        int ColPrec = numTilesInColumn(b,0);
        int temp;

        //Check if the columns are in descending order
        for (int j = 1; j < 5 && dooableFlag == true; j++){
            temp = numTilesInColumn(b,j);
            if (ColPrec != temp + 1) {dooableFlag = false;}
            ColPrec = temp;
        }
        if(dooableFlag == true) {
            return popTokens();
        }


        dooableFlag = true;
        ColPrec = numTilesInColumn(b,4);

        //Check if the columns are in ascending order
        for (int j = 3 ; j >= 0 && dooableFlag == true; j--){
            temp = numTilesInColumn(b,j);
            if (ColPrec != temp + 1) {dooableFlag = false;}
            ColPrec = temp;
        }
        if (dooableFlag == true) {
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

        for (i = 0; i < tiles.length(); i++){
            if (tiles[i][j] != NULL ){
                numTiles++;
            }
        }
        return numTiles;
    }
}