package myshelfie_model.player;

import myshelfie_model.Tile;

import java.util.List;

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
    public void fill(int col, List<Tile> chosen) {
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

    public void visit(boolean[][] visited, int r, int c, int[] count) {
        if (r < 0 || r >= tiles.length || c < 0 || c >= tiles[0].length || visited[r][c] || tiles[r][c] == null) {
            return;
        }

        visited[r][c] = true;
        count[0]++;

        if (r + 1 < tiles.length && tiles[r + 1][c] != null && tiles[r + 1][c].getType() == tiles[r][c].getType()) {
            visit(visited, r + 1, c, count);
        }

        if (r - 1 >= 0 && tiles[r - 1][c] != null && tiles[r - 1][c].getType() == tiles[r][c].getType()) {
            visit(visited, r - 1, c, count);
        }

        if (c + 1 < tiles[0].length && tiles[r][c + 1] != null && tiles[r][c + 1].getType() == tiles[r][c].getType()) {
            visit(visited, r, c + 1, count);
        }

        if (c - 1 >= 0 && tiles[r][c - 1] != null && tiles[r][c - 1].getType() == tiles[r][c].getType()) {
            visit(visited, r, c - 1, count);
        }
    }

    public int getPoints() {
        boolean[][] visited = new boolean[tiles.length][tiles[0].length];
        int[] count = new int[1];
        int punteggio = 0;

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (!visited[i][j] && tiles[i][j] != null) {
                    count[0] = 0;
                    visit(visited, i, j, count);

                    if (count[0] == 3) {
                        punteggio += 2;
                    } else if (count[0] == 4) {
                        punteggio += 3;
                    } else if (count[0] == 5) {
                        punteggio +=5;
                    } else if (count[0] >= 6) {
                        punteggio += 8;

                    }
                }
            }
        }

        return punteggio;
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
