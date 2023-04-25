package myshelfie_controller.response;

import myshelfie_model.GameUpdate;
import myshelfie_model.board.Board;
import myshelfie_model.goal.Token;
import myshelfie_model.player.Bookshelf;

public class TakeTilesUpdate extends Response{

    private GameUpdate gameUpdate;
    private String updatePlayer; //the player who took the tiles and updated the bookshelf


    public TakeTilesUpdate(String player, GameUpdate gameUpdate, String updatePlayer) {
        super(player);
        this.gameUpdate = gameUpdate;
        this.updatePlayer = updatePlayer;
    }

    public GameUpdate getGameUpdate() {
        return gameUpdate;
    }
}