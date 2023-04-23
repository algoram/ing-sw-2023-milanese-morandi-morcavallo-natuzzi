package myshelfie_controller;

import myshelfie_model.Game;

import java.util.HashMap;

public class GameManager {

    private final HashMap<String, Game> games = new HashMap<>();

    public boolean gameExists(String game) {
        return games.containsKey(game);
    }

    public boolean removePlayer(String game, String player) {
        return games.get(game).removePlayer(player);
    }

    public String[] getPlayers(String game) {
        return games.get(game).getPlayersUsernames();
    }

    /**
     * This function is calles from event handler when a player connects to a game.
     * if the game doesn't exist it will be created.
     * @param game the game to add the player to
     * @param newPlayer the player to add
     * @return true if the player was added, false if the addplayer failed
     */
    public boolean addGame(String game,String newPlayer, int players) {
        if (!gameExists(game)){
            Game newGame = new Game();
            newGame.startGame(players);
            games.put(game, newGame);
        }
        return games.get(game).addPlayer(newPlayer);
    }



}
