package myshelfie_controller.response;

public class MessageSendResponse extends Response{

    private String message;
    private String from;//the player who sent the message
    private Boolean AllPlayers;

    public MessageSendResponse(String player, String game, String message, String from, Boolean AllPlayers) {
        super(player, game);
        this.message = message;
        this.from = from;
        this.AllPlayers = AllPlayers;
    }

    public String getMessage() {
        return message;
    }

    public String getFrom() {
        return from;
    }

    public Boolean getAllPlayers() {
        return AllPlayers;
    }
}
