public class Cross extends CommonGoal {

    @Override
    public int check(Bookshelf b){
        Tile[][] tiles = b.getTiles();

        //it iterates on tiles excluding the boundaries
        for (int i = 1; i < tiles.length - 1; i++) {
            for (int j = 1; j < tiles[0].length - 1; j++) {
                if (tiles[i][j] != null) { //Check if the current Tile is the center of a cross of equal type
                    if( tiles[i+1][j+1].getType() == tiles[i][j].getType() &&
                        tiles[i-1][j-1].getType() == tiles[i][j].getType() &&
                        tiles[i+1][j-1].getType() == tiles[i][j].getType() &&
                        tiles[i-1][j+1].getType() == tiles[i][j].getType()) {

                        return popTokens();
                    }
                }
            }
        }
        return -1;
    }
}