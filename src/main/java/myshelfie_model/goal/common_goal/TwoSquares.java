package myshelfie_model.goal.common_goal;

import myshelfie_model.Tile;
import myshelfie_model.player.Bookshelf;

public class TwoSquares extends CommonGoal {
    public TwoSquares(int numberOfPlayers) {
        super(numberOfPlayers);
    }

    @Override
    public int check(Bookshelf b) {
        Tile[][] tiles = b.getTiles(); // tiles we'll be checking

        // variables to store the first square found
        int lastRow = -1;
        int lastColumn = -1;

        // we iterate one less row and column so that we always have a card to the right and bottom
        for (int i = 0; i < tiles.length - 1; i++) {
            for (int j = 0; j < tiles[0].length - 1; j++) {
                if (checkSquare(tiles, i, j)) {
                    // if this is the first square we store it
                    if (lastRow == -1 && lastColumn == -1) {
                        lastRow = i;
                        lastColumn = j;
                    }
                    // else check that the second square doesn't overlap with the first one
                    else if (Math.abs(i - lastRow) >= 2 || Math.abs(j - lastColumn) >= 2) {
                        return popTokens();
                    }
                }
            }
        }

        return -1;
    }

    private boolean checkSquare(Tile[][] tiles, int i, int j) {
        // if one of the cells is empty we can skip the check
        if (tiles[i][j] == null || tiles[i+1][j] == null || tiles[i][j+1] == null || tiles[i+1][j+1] == null) {
            return false;
        }

        // check that all the tiles are of the same type
        return (tiles[i][j].getType() == tiles[i][j+1].getType())
                && (tiles[i][j].getType() == tiles[i+1][j].getType())
                && (tiles[i][j].getType() == tiles[i+1][j+1].getType());
    }
}
