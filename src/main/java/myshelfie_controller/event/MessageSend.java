package myshelfie_controller.event;

import java.io.Serializable;

public class MessageSend extends Event implements Serializable {

    private String to;
    private String message;

    public MessageSend(String player, String game, String to, String message) {
        super(player);

        this.to = to;
        this.message = message;
    }

    public String getRecipient() {
        return to;
    }

    public String getMessage() {
        return message;
    }
}
