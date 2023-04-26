package myshelfie_controller.event;

import java.io.Serializable;
import java.util.UUID;

public abstract class Event implements Serializable {

    protected String sourcePlayer;
    private UUID uuid;

    public Event(String player) {
        sourcePlayer = player;
    }

    public String getSource() {
        return sourcePlayer;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

}
