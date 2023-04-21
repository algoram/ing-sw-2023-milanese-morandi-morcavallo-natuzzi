package myshelfie_controller.event;

public class PlayerConnect extends Event {
    public PlayerConnect(String player, String game) {
        super(player, game);
    }
}
