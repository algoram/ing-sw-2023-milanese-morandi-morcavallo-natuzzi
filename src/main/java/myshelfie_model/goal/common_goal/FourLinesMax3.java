package myshelfie_model.goal.common_goal;


import myshelfie_model.Tile;
import myshelfie_model.Type;
import myshelfie_model.player.Bookshelf;

public class FourLinesMax3 extends CommonGoal{
    public int check(Bookshelf b) {
        Tile[][] tiles = b.getTiles();
        int score = 0;

        // Controlla le quattro righe di cinque tessere ciascuna
        for (int i = 0; i < 4; i++) {
            boolean valid = true;
            for (int j = 0; j < 5; j++) {
                TileTypes types = tiles[i][j].getType();
                // Controlla che la tessera corrente abbia lo stesso colore di tutte le precedenti
                for (int k = 0; k < j; k++) {
                    //if (tiles[i][k].getType() != color) {
                        valid = false;
                        break;
                    }
                }
                if (!valid) {
                    break;
                }
            }
            if (valid) {
                score += 4;
            }
        }

        return score;
    }







}
