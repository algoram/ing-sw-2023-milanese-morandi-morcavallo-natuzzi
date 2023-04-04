package myshelfie_model.goal.common_goal;

import myshelfie_model.Tile;
import myshelfie_model.Type;
import myshelfie_model.player.Bookshelf;

import java.util.ArrayList;
import java.util.List;

public class FourGroups4Tiles extends CommonGoal {

    public FourGroups4Tiles(int numberOfPlayers) {
        super(numberOfPlayers);
    }

    /**
     * Checks whether the player has 4 groups of adjacent tiles of the same
     * type on the bookshelf
     * @param b the player's bookshelf
     * @return int indicating whether is goal is achieved
     */
    @Override
    public boolean check(Bookshelf b) {
        Tile[][] tiles = b.getTiles();
        boolean[][] visited = new boolean[tiles.length][tiles[0].length];

        List< List<Tile> > groups = new ArrayList<>();

        for (int i = 0; i <tiles.length; i++) {
            for (int j = 0; j < tiles[0].length ; j++) {
                if (tiles[i][j] != null && !visited[i][j]) {

                    List<Tile> group = new ArrayList<>(); //TODO: vorrei liberare memoria in caso di gruppo non valido, come faccio?
                    findGroup(tiles, visited, i, j, tiles[i][j].getType(), group);
                    if (group.size() >= 4) {
                        groups.add(group);
                    }
                }
            }
        }
        return groups.size() >= 4;
    }

    private void findGroup(Tile[][] matrix, boolean[][] visited, int i, int j,Type type , List<Tile> group) {
        if (i < 0 || i >= matrix.length || j < 0 || j >= matrix[0].length || visited[i][j] || !matrix[i][j].getType().equals(type)) {
            return;
        }
        visited[i][j] = true;
        group.add(matrix[i][j]);
        findGroup(matrix, visited, i - 1, j, type, group); // check up
        findGroup(matrix, visited, i, j + 1, type, group); // check dx
        findGroup(matrix, visited, i + 1, j, type, group); // check down
        findGroup(matrix, visited, i, j - 1, type, group); // check sx
    }
}
