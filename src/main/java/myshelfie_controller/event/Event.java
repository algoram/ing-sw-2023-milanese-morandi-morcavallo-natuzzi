package myshelfie_controller.event;

import java.io.Serializable;

public abstract class Event implements Serializable {

    protected String sourcePlayer;

    public Event(String player) {
        sourcePlayer = player;
    }

    public String getSource() {
        return sourcePlayer;
    }

}
