package myshelfie_controller.response;

public class GameCreateUpdate extends Response {

    private final int queuePosition;

    public GameCreateUpdate(String player, int queuePosition) {
        super(player);
        this.queuePosition = queuePosition;
    }

    public int getQueuePosition() {
        return queuePosition;
    }

}
