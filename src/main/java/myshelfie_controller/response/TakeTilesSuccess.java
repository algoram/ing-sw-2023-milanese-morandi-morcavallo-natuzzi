package myshelfie_controller.response;


import myshelfie_model.GameUpdate;
import myshelfie_model.board.Board;
import myshelfie_model.goal.Token;
import myshelfie_model.player.Bookshelf;

public class TakeTilesSuccess extends Response {

    private GameUpdate gameUpdate;

    public TakeTilesSuccess(String player, GameUpdate gameUpdate) {

        super(player);
        this.gameUpdate = gameUpdate;
    }

    public GameUpdate getGameUpdate() {
        return gameUpdate;
    }
}
