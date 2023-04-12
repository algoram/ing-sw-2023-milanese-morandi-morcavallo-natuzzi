import myshelfie_model.Game;

public class Main {

    public static void main(String[] args) {
        Game game = new Game();

        game.startGame(4);

        game.debugBoard();

        System.out.println(game.getAvailableTiles());
    }

}
