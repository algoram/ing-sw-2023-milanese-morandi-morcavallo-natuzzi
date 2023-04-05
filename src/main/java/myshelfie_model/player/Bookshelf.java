package myshelfie_model.player;

import myshelfie_model.Tile;

public class Bookshelf {

    private Tile[][] tiles;
    private final int rows = 6;
    private final int cols = 5;
    public Bookshelf() {
        tiles = new Tile[rows][cols];
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }

    /**
     * Fills the column with the given tile
     *
     * @param col: column to be filled
     * @param chosen: the tile to be added
     * */
    public void fill(int col, Tile[] chosen) {
        int i=0;

        while (tiles[i][col]!=null){ i++;}
        for (Tile value : chosen) {
            tiles[i][col] = value;
            i++;
        }

    }


    /**
     *
     * @return an array with the number of free tiles in each column
     * */
    public int[] emptyCol() {
        int[] empty = new int[cols];

        for (int i = 0; i <cols ; i++) {
            empty[i] = 0;
        }

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if(tiles[i][j]==null){
                    empty[j]++;
                }
            }
        }
        return empty;
    }

    public int visit(boolean[][] visited, int r, int c){
        if (visited[r][c]) return 0;


        int points = 0;


        if (!visited[r+1][c] && tiles[r+1][c].getType()==tiles[r][c].getType()) {
            points += visit(visited, r+1, c);
        }
        if (!visited[r-1][c] && tiles[r-1][c].getType()==tiles[r][c].getType()){
            points += visit(visited, r-1,c);
        }
        if (!visited[r][c+1] && tiles[r][c+1].getType()==tiles[r][c].getType()){
            points += visit(visited, r,c+1);
        }
        if(!visited[r][c-1] && tiles[r][c-1].getType()==tiles[r][c].getType() ){
            points += visit(visited,r,c-1);
        }
        return 1+points;
    }

    public int getPoints() {
        boolean[][] visited = new boolean[tiles.length][tiles[0].length];
        int count = 0;
        int point = 0;
        for(int i = 0;i< tiles.length;i++){
            for(int j=0;j< tiles.length;j++){
                if (!visited[i][j]){
                    count = visit(visited,i,j);
                }

            }
        }
        if (count==3) {
            point = 2;
        }
        if (count==4) {
            point = 3;
        }
        if (count==5) {
            point = 5;
        }
        if (count>6) {
            point = 6;
        }
        return point;
    }
    public boolean isFull(){
        for (int i = 0;i< tiles.length;i++){
            for(int j=0;j< tiles[0].length;j++){
                if(tiles[i][j]==null){
                    return false;
                }
            }
        }
        return true;


    }

}
