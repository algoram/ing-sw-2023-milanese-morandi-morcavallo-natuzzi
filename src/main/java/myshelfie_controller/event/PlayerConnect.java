package myshelfie_controller.event;

public class PlayerConnect extends Event {
    private int numberOfPlayers;
    public PlayerConnect(String player, String game,int numberOfPlayers) {
        super(player, game);
        this.numberOfPlayers = numberOfPlayers;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }
}
