package myshelfie_controller;

import myshelfie_model.Game;
import myshelfie_model.Position;

import java.util.ArrayList;
import java.util.HashMap;

public class ServerController {

    private class GameContainer {
        public Game game;
        public String[] players;

        public GameContainer(int numberOfPlayers) {
            game = new Game();
            players = new String[numberOfPlayers];
        }
    }

    private final HashMap<String, GameContainer> games;

    public ServerController() {
        games = new HashMap<>();
    }

    // methods to be used by the server
    public void create(String gameName, int numberOfPlayers) {
        // TODO: create a new game, start it when the right number of players has connected
    }

    public void stop(String gameName) {
        // TODO: disconnect all players and remove the game from memory
    }

    // methods to be used by the players
    public void connect(String gameName, String username) {

    }

    public void disconnect(String gameName, String username) {

    }

    public boolean take(String gameName, String username, int column, ArrayList<Position> positions) {
        return false;
    }

    public void chat(String gameName, String username, String to, String message) {

    }

}
