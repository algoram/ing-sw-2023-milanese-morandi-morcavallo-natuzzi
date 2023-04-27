package myshelfie_controller.response;

import java.io.Serializable;

public abstract class Response implements Serializable {

    protected String targetPlayer;
    public Response(String player) {
        targetPlayer = player;
    }

    public String getTarget() {
        return targetPlayer;
    }

}
