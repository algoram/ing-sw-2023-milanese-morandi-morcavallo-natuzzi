package myshelfie_controller.response;

public class TakeTilesFailure extends Response{
    private String reason;

    public TakeTilesFailure(String player, String reason) {
        super(player);
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}
