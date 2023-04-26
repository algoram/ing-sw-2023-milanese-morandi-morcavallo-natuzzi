package myshelfie_controller.response;

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
