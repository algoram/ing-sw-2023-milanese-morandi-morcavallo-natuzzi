package myshelfie_model.goal.common_goal;

import myshelfie_model.Tile;
import myshelfie_model.Type;
import myshelfie_model.player.Bookshelf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FourGroups4Tiles extends CommonGoal {

    public FourGroups4Tiles(int numberOfPlayers) {
        super(numberOfPlayers);
    }

    /**
     * Checks whether the player has 4 groups of adjacent tiles of the same
     * type on the bookshelf
     * @param b the player's bookshelf
     * @return a int indicating whether is goal is achieved
     */
    @Override
    public int check(Bookshelf b) {
        if (findGroups(b.getTiles())) return popTokens();
        return -1;
    }


    private boolean findGroups(Tile[][] matrix) {
        List<List<Tile>> groups = new ArrayList<>();
        int counter=0;
        int rows = matrix.length;
        int cols = matrix[0].length;
        boolean[][] visited = new boolean[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!visited[i][j]) {
                    Tile tile = matrix[i][j];
                    List<Tile> group = new ArrayList<>();
                    findGroup(matrix, visited, i, j, tile, group);
                    if (group.size() >= 4) {
                        groups.add(group);
                        counter++;
                    }
                }
            }
        }
        if(counter>=4) return true;
        return false;
    }

    private void findGroup(Tile[][] matrix, boolean[][] visited, int row, int col, Tile tile, List<Tile> group) {
        if (row < 0 || row >= matrix.length || col < 0 || col >= matrix[0].length) {
            return;
        }
        if (visited[row][col]) {
            return;
        }
        Tile currentTile = matrix[row][col];
        if (!currentTile.equals(tile)) {
            return;
        }
        visited[row][col] = true;
        group.add(currentTile);
        findGroup(matrix, visited, row - 1, col, tile, group);
        findGroup(matrix, visited, row + 1, col, tile, group);
        findGroup(matrix, visited, row, col - 1, tile, group);
        findGroup(matrix, visited, row, col + 1, tile, group);
    }

}
