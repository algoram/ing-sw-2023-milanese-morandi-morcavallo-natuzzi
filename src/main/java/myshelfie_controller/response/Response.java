package myshelfie_controller.response;

public abstract class Response {

    protected String targetPlayer;
    public Response(String player) {
        targetPlayer = player;
    }

    public String[] getTarget() {
        return new String[] {targetPlayer};
    }

}
