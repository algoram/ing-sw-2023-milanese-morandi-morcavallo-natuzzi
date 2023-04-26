package myshelfie_controller.response;

//TODO THIS MESSAGE IS SENT TO ALL PLAYERS WHEN A PLAYER DISCONNECTS OR LOSE CONNECTION

public class PlayerDisconnectSuccess extends Response{
    private final String disconnectedPlayer;

    public PlayerDisconnectSuccess(String player,String disconnectedPlayer) {
        super(player);
        this.disconnectedPlayer = disconnectedPlayer;
    }

    public String getDisconnectedPlayer() {
        return disconnectedPlayer;
    }
}
