package myshelfie_controller.response;

public class PlayerDisconnectSuccess extends Response{
    private final String disconnectedPlayer;

    public PlayerDisconnectSuccess(String player, String game, String disconnectedPlayer) {
        super(player, game);
        this.disconnectedPlayer = disconnectedPlayer;
    }

    public String getDisconnectedPlayer() {
        return disconnectedPlayer;
    }
}
