package myshelfie_controller.response;

public class PlayerConnectFailure extends Response {

    private String message;
    public PlayerConnectFailure(String player, String game, String message) {
        super(player, game);

        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
