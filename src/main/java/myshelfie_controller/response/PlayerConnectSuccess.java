package myshelfie_controller.response;

import myshelfie_model.board.Board;
import myshelfie_model.goal.Token;
import myshelfie_model.player.Bookshelf;

public class PlayerConnectSuccess extends Response{

    public PlayerConnectSuccess(String player, String game) {
        super(player, game);

    }

}
