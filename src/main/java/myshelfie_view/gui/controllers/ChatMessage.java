package myshelfie_view.gui.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatMessage {
    private String sender;
    private String recipient;
    private String message;
    private Date timeStamp;

    ChatMessage(String sender, String recipient, String message, Date timeStamp) {
        this.sender = sender;
        this.recipient = recipient;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String timeStampString = sdf.format(timeStamp);

        return "[" + timeStampString + "] " + sender + " to " + (recipient == null ? "ALL" : recipient) + ": " + message;
    }
}
