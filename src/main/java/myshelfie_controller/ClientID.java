package myshelfie_controller;

public class ClientID {

    private final String game;
    private final String player;

    public ClientID(String game, String player) {
        this.game = game;
        this.player = player;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ClientID) {
            return game.equals(((ClientID) o).game) && player.equals(((ClientID) o).player);
        }

        return false;
    }

}
