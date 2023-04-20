package myshelfie_controller.event;

import java.io.Serializable;

public abstract class Event implements Serializable {

    protected String sourcePlayer;
    protected String sourceGame;

    public Event(String player, String game) {
        sourcePlayer = player;
        sourceGame = game;
    }

    public String[] getSource() {
        return new String[] {sourcePlayer, sourceGame};
    }

}
