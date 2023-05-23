package myshelfie_controller.event;

public class Ping extends Event {
    private boolean lastPing;
    public Ping(String player, boolean lastPing) {

        super(player);
        this.lastPing = lastPing;
    }

    public boolean getLastPing() {
        return lastPing;
    }
}
