package myshelfie_view.cli;

public class Messages {
    private final String message;
    private final String sender;
    private final boolean isPublic;

    public Messages(String message, String sender, boolean isPublic) {
        this.message = message;
        this.sender = sender;
        this.isPublic = isPublic;
    }

    public String getMessage() {
        return message;
    }

    public String getSender() {
        return sender;
    }

    public boolean isPublic() {
        return isPublic;
    }
}
