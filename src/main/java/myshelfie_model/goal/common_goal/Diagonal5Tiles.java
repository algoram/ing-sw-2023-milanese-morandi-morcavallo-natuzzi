package myshelfie_model.goal.common_goal;

import myshelfie_model.Tile;
import myshelfie_model.Type;
import myshelfie_model.player.Bookshelf;

public class Diagonal5Tiles extends CommonGoal {
    public Diagonal5Tiles(int numberOfPlayers) {
        super(numberOfPlayers);
    }

    public int check(Bookshelf b) {
        Tile[][] tiles = b.getTiles();
        for (int i = 0; i < tiles.length; i++) {
            Tile firstTile = tiles[i][0];
            boolean sameColor = true;
            for (int j = 1; j < tiles.length - i; j++) {
                if (tiles[i+j][j].getType() != firstTile.getType()) {
                    sameColor = false;
                    break;
                }
            }
            if (sameColor) {
                return popTokens();
            }
        }

        for (int i = 1; i < tiles.length; i++) {
            Tile firstTile = tiles[0][i];
            boolean sameColor = true;
            for (int j = 1; j < tiles.length - i; j++) {
                if (tiles[j][i+j].getType()  != firstTile.getType()) {
                    sameColor = false;
                    break;
                }
            }
            if (sameColor) {
                return popTokens();
            }
        }

        return -1;
    }






}
