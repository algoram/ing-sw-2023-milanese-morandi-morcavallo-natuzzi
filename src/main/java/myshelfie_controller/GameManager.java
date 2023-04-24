package myshelfie_controller;

import myshelfie_model.Game;
import myshelfie_model.Position;
import myshelfie_model.board.Board;
import myshelfie_model.goal.Token;
import myshelfie_model.player.Bookshelf;

import java.util.HashMap;
import java.util.List;

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


    public boolean takeTiles(String game, String player, int column, List<Position> tiles) {
        return games.get(game).takeTiles(player, tiles, column);
    }

    //TODO: add function to game
    public Board getBoard(String game) {
        return games.get(game).getBoard();
    }

    public Bookshelf getBookshelf(String game, String player) {
        return games.get(game).getBookshelf(player);
    }

    public Token[] getCommonTokens(String game, String player) {
        return games.get(game).getCommonGoalTokens(player);
    }

    public Token getFinishToken(String game, String player) {
        return games.get(game).getFinishToken();
    }

    public int getAdjacentScore(String game, String player) {
        return games.get(game).getAdjacentScore(player);
    }

    public int getPersonalScore(String game, String player) {
        return games.get(game).getPersonalScore(player);
    }

}
