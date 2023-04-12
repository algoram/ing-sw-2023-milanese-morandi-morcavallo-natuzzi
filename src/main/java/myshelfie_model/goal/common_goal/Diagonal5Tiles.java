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
        int rows = tiles.length;
        int cols = tiles[0].length;
        int diagonalSize = 5;

        // Check diagonals from top-left corner
        for (int i = 0; i <= rows - diagonalSize; i++) {
            for (int j = 0; j <= cols - diagonalSize; j++) {
                boolean match = true;
                Type type = tiles[i][j] != null ? tiles[i][j].getType() : null;
                if (type == null) {
                    match = false;
                } else {
                    for (int k = 1; k < diagonalSize; k++) {
                        if (tiles[i + k][j + k] == null || tiles[i + k][j + k].getType() != type) {
                            match = false;
                            break;
                        }
                    }
                }
                if (match) {
                    return true;
                }
            }
        }

        // Check diagonals from bottom-left corner
        for (int i = diagonalSize - 1; i < rows; i++) {
            for (int j = 0; j <= cols - diagonalSize; j++) {
                boolean match = true;
                Type type = tiles[i][j] != null ? tiles[i][j].getType() : null;
                if (type == null) {
                    match = false;
                } else {
                    for (int k = 1; k < diagonalSize; k++) {
                        if (tiles[i - k][j + k] == null || tiles[i - k][j + k].getType() != type) {
                            match = false;
                            break;
                        }
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