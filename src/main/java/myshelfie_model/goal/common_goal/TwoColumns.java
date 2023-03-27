package myshelfie_model.goal.common_goal;



import myshelfie_model.Tile;
import myshelfie_model.Type;
import myshelfie_model.player.Bookshelf;
import java.util.HashSet;
import java.util.Set;


public class TwoColumns extends CommonGoal{
    public int check(Bookshelf b) {
        Tile[][] tiles = b.getTiles();
        int count = 0;

        for (int i = 0; i < tiles[0].length; i++) { // ciclo sulle colonne
            for (int j = i + 1; j < tiles[0].length; j++) { // ciclo sulle colonne successive
                boolean sameTiles = true;
                for (int k = 0; k < tiles.length && sameTiles; k++) { // ciclo sulle righe
                    boolean foundInI = false;
                    boolean foundInJ = false;
                    for (int l = 0; l < tiles[0].length && !(foundInI && foundInJ); l++) { // ciclo sulle colonne per cercare la tessera corrente
                        if (tiles[k][l].getType() == tiles[k][i].getType()) {
                            foundInI = true;
                        }
                        if (tiles[k][l].getType() == tiles[k][j].getType()) {
                            foundInJ = true;
                        }
                    }
                    if (!foundInI || !foundInJ) {
                        sameTiles = false;
                    }
                }
                if (sameTiles) {
                    count++;
                }
            }
        }

        return count;
    }



}
