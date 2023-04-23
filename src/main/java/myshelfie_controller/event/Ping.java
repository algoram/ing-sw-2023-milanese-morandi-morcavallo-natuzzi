package myshelfie_controller.event;

public class Ping extends Event {
    public Ping(String player, String game) {
        super(player, game);
    }
}
