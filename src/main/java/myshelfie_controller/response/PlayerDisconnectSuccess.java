package myshelfie_controller.response;


public class PlayerDisconnectSuccess extends Response{
    private final String disconnectedPlayer;

    private final String playerTurn;

    public PlayerDisconnectSuccess(String player,String disconnectedPlayer,String playerTurn){
        super(player);
        this.disconnectedPlayer = disconnectedPlayer;
        this.playerTurn = playerTurn;
    }

    public String getDisconnectedPlayer() {
        return disconnectedPlayer;
    }

    public String getPlayerTurn(){
        return playerTurn;
    }
}
