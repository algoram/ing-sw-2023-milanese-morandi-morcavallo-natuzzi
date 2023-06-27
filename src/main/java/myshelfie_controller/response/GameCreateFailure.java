package myshelfie_controller.response;

public class GameCreateFailure extends Response {

    private final String reason;

    public GameCreateFailure(String player, String reason) {
        super(player);

        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

}
