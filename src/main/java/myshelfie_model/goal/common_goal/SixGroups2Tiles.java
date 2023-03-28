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
                if (visited[i][j] == 0) {
                    if (tiles[i][j] == tiles[i][j+1] ||
                        tiles[i][j] == tiles[i][j-1] ||
                        tiles[i][j] == tiles[i+1][j] ||
                        tiles[i][j] == tiles[i-1][j]){

                        count++;
                        visit(tiles,visited,i,j);
                    }

                }
            }
        }
        if(count>=6){
            return true;
        }
        else return false;
    }

    private int[][] visit (Tile[][] tiles, int[][] visited, int x, int y){
        visited[x][y]=1;
        if(tiles[x][y].getType() == tiles[x][y+1].getType()){
            visited = visit(tiles,visited, x, y+1);
        }
        if(tiles[x][y].getType() == tiles[x][y-1].getType()){
            visited = visit(tiles,visited, x, y-1);
        }
        if(tiles[x][y].getType() == tiles[x+1][y].getType()){
            visited = visit(tiles,visited, x+1, y);
        }
        if(tiles[x][y].getType() == tiles[x-1][y].getType()){
            visited = visit(tiles,visited, x-1, y);
        }
        return visited;
    }
}

