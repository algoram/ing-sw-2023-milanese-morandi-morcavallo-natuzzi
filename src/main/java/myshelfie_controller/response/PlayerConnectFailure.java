package myshelfie_controller.response;

public class PlayerConnectFailure extends Response {

    private String message;
    public PlayerConnectFailure(String player, String message) {
        super(player);

        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
