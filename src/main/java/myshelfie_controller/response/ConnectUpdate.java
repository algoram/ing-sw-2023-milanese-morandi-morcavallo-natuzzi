package myshelfie_controller.response;

import myshelfie_model.GameState;

public class ConnectUpdate extends Response {

    private GameState gameState;

    public ConnectUpdate(String player, String game, GameState gameState) {
        super(player, game);
        this.gameState = gameState;
    }

    public GameState getGameState() {
        return gameState;
    }

}
