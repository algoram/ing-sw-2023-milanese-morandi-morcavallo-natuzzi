package myshelfie_controller.response;

import myshelfie_model.GameState;


public class TakeTilesUpdate extends Response{

    private final GameState gameState;
    private final String updatePlayer; //the player who took the tiles and updated the bookshelf


    public TakeTilesUpdate(String player, GameState gameState, String updatePlayer) {
        super(player);
        this.gameState = gameState;
        this.updatePlayer = updatePlayer;
    }

    public GameState getGameState() {
        return gameState;
    }


    public String getUpdatePlayer() {
        return updatePlayer;
    }
}