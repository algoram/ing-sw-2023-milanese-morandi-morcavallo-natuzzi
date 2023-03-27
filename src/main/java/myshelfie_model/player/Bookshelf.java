package myshelfie_model.player;

import myshelfie_model.Tile;

public class Bookshelf {

    private Tile[][] tiles;

    public Tile[][] getTiles() {
        return tiles;
    }

    public int getPoints() {

        return 0;
    }
    public boolean isFull(){
        for (int i = 0;i< tiles.length;i++){
            for(int j=0;j< tiles.length;j++){
                if(tiles[i][j]==null){
                    return false;
                }
            }
        }
        return true;


    }

}
