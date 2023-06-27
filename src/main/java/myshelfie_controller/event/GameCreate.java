package myshelfie_controller.event;

public class GameCreate extends Event {

    private final int numberOfPlayers;

    public GameCreate(String player, int numberOfPlayers) {
        super(player);

        this.numberOfPlayers = numberOfPlayers;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

}
