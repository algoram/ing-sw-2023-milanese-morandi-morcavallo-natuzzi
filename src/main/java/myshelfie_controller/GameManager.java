package myshelfie_controller;

import myshelfie_model.Game;

import java.util.HashMap;

public class GameManager {

    private final HashMap<String, Game> games = new HashMap<>();

    public boolean gameExists(String game) {
        return games.containsKey(game);
    }

    public String[] getPlayers(String game) {
        return games.get(game).getPlayersUsernames();
    }

    public int addGame(String game, int players) {
        if (games.containsKey(game)) {
            return games.get(game).getNumberOfPlayers();
        }

        Game g = new Game();
        g.startGame(players);

        return g.getNumberOfPlayers();
    }

}
