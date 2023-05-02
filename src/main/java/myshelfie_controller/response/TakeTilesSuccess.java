package myshelfie_controller.response;


import myshelfie_model.GameState;

public class TakeTilesSuccess extends Response {

    private GameState gameState;

    public TakeTilesSuccess(String player, GameState gameState) {

        super(player);
        this.gameState = gameState;
    }

    public GameState getGameState() {
        return gameState;
    }
}
