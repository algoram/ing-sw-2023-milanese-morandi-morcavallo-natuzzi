package myshelfie_model.goal.common_goal;

import myshelfie_model.Tile;
import myshelfie_model.Type;
import myshelfie_model.player.Bookshelf;

public class Diagonal5Tiles extends CommonGoal {
    public Diagonal5Tiles(int numberOfPlayers) {
        super(numberOfPlayers);
    }

    //Diagonals
    public boolean check(Bookshelf b) {
        Tile[][] tiles = b.getTiles();
        int size = tiles.length;


        for (int i = 0; i <= size - 5; i++) {
            for (int j = 0; j <= size - 5; j++) {
                boolean match = true;
                Type type = tiles[i][j].getType();
                for (int k = 1; k < 5; k++) {
                    if (tiles[i + k][j + k].getType() != type) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    return true;
                }
            }
        }


        for (int i = 0; i <= size - 5; i++) {
            for (int j = size - 1; j >= 4; j--) {
                boolean match = true;
                Type type = tiles[i][j].getType();
                for (int k = 1; k < 5; k++) {
                    if (tiles[i + k][j - k].getType() != type) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    return true;
                }
            }
        }


        return false;

    }
}