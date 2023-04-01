package myshelfie_model.goal.common_goal;

import myshelfie_model.Tile;
import myshelfie_model.player.Bookshelf;


public class SixGroups2Tiles extends CommonGoal {

    public SixGroups2Tiles(int numberOfPlayers) {super(numberOfPlayers);}


    /**
     * Checks whether the player has in the bookshelf
     * six groups of tiles containing at least 2
     * tiles of the same type (type among groups can be different)
     *
     * @param b the player's bookshelf
     * @return an int: -1 if the goal wasn't achieved
     * otherwise the points of the Token in stack's first place
     */
    @Override
    public boolean check(Bookshelf b) {

        Tile[][] tiles = b.getTiles();
        int[][] visited = new int[tiles.length][tiles[0].length];
        int count = 0;

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (tiles[i][j] != null && visited[i][j] == 0) {
                    if (( j < tiles[0].length-1 && tiles[i][j+1] != null && tiles[i][j].getType() == tiles[i][j+1].getType() )||
                        (j > 0 && tiles[i][j-1] != null && tiles[i][j].getType() == tiles[i][j-1].getType() )||
                        ( i < tiles.length -1 && tiles[i+1][j] != null && tiles[i][j].getType() == tiles[i+1][j].getType() )||
                        ( i > 0 && tiles[i-1][j] != null && tiles[i][j].getType() == tiles[i-1][j].getType())){

                        count++;
                        visited = visit(tiles,visited,i,j);

                    }

                }
            }
        }
        return count >= 6;
    }

    private int[][] visit(Tile[][] tiles, int[][] visited, int i, int j){
        visited[i][j]=1;
        if(tiles[i][j] != null && j < tiles[0].length-1 && tiles[i][j+1] != null && tiles[i][j].getType() == tiles[i][j+1].getType()){
            if ( visited[i][j+1] == 0 ) {visited = visit(tiles,visited, i, j+1);}
        }
        if(tiles[i][j] != null && j > 0 && tiles[i][j-1] != null &&tiles[i][j].getType() == tiles[i][j-1].getType()){
            if (visited[i][j-1] == 0){ visited = visit(tiles,visited, i, j-1);}
        }
        if(tiles[i][j] != null && i < tiles.length-1 &&  tiles[i+1][j] != null && tiles[i][j].getType() == tiles[i+1][j].getType()){
            if (visited[i+1][j] == 0) {visited = visit(tiles,visited, i+1, j);}
        }
        if(tiles[i][j] != null && i > 0 && tiles[i-1][j] != null && tiles[i][j].getType() == tiles[i-1][j].getType()){
            if (visited[i-1][j] == 0) {visited = visit(tiles,visited, i-1, j) ;}
        }
        return visited;
    }
}

