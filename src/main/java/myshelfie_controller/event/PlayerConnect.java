package myshelfie_controller.event;

public class PlayerConnect extends Event {
    private int numberOfPlayers;
    public PlayerConnect(String player,int numberOfPlayers) {
        super(player);
        this.numberOfPlayers = numberOfPlayers;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }
}
