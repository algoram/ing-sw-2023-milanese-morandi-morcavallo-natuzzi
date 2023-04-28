package myshelfie_controller.response;

//TODO THIS MESSAGE IS SENT TO ALL PLAYERS WHEN A PLAYER DISCONNECTS OR LOSE CONNECTION

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
