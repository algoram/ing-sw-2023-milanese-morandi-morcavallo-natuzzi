package myshelfie_controller.response;

public abstract class Response {

    protected String targetPlayer;
    protected String targetGame;

    public Response(String player, String game) {
        targetGame = game;
        targetPlayer = player;
    }

    public String[] getTarget() {
        return new String[] {targetPlayer, targetGame};
    }

}
