package myshelfie_controller.response;

public class GameFinished extends Response{
    private final String winner;

    public GameFinished(String player,String winner) {
        super(player);
        this.winner = winner;
    }

    public String getWinner() {
        return winner;
    }
}
