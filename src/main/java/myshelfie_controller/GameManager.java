package myshelfie_controller;

import myshelfie_model.Game;
import myshelfie_model.GameState;
import myshelfie_model.GameUpdate;
import myshelfie_model.Position;
import myshelfie_model.board.Board;
import myshelfie_model.goal.Token;
import myshelfie_model.goal.common_goal.CommonGoal;
import myshelfie_model.player.Bookshelf;
import myshelfie_model.player.Player;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameManager {

    private final ArrayList<Game> games = new ArrayList<>();
    private final HashMap<String, Integer> playerToGame = new HashMap<>();

    private static GameManager instance = null;

    private GameManager() {}

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }

        return instance;
    }

    public void removePlayer(String player) {
        games.get(playerToGame.get(player)).removePlayer(player);
    }

    public List<String> getPlayers(String player) {
        return games.get(playerToGame.get(player)).getPlayersUsernames();
    }

    /**
     * This function is calls from event handler when a player connects to a game.
     * if the game doesn't exist it will be created.
     * @param newPlayer the player to add
     * @return true if the player was added, false if the addPlayer failed
     */
    public boolean addPlayer(String newPlayer, int players) {
        for (int i = 0; i < games.size(); i++) {
            // check if the game is not full
            if (games.get(i).getPlayers().size() < games.get(i).getNumberOfPlayers()) {
                playerToGame.put(newPlayer, i); // save which game the player is playing
                return games.get(i).addPlayer(newPlayer); // add the player to the game
            }
        }

        // if all games are full we create a new one
        Game game = new Game();
        game.startGame(players); // start it with the number of players specified

        games.add(game);

        playerToGame.put(newPlayer, games.size() - 1); // save which game the player is playing

        return game.addPlayer(newPlayer); // add the player to the game
    }


    public boolean takeTiles(String player, int column, List<Position> tiles) {
        return games.get(playerToGame.get(player)).takeTiles(player, tiles, column);
    }

    public int getNumberOfPlayers(String player) {
        return games.get(playerToGame.get(player)).getNumberOfPlayers();
    }


    public void lostConnection(String player) {
        Integer game = playerToGame.get(player);
        //if (game == null) { return false; }
        games.get(game).lostConnection(player);
    }


    //------------PLAYER STATE FUNCTIONS----------------

    private Bookshelf getBookshelf(String player) {
        return games.get(playerToGame.get(player)).getBookshelf(player);
    }

    private Token[] getCommonTokens(String player) {
        return games.get(playerToGame.get(player)).getCommonGoalTokens(player);
    }

    private int getFinishToken(String player) {
        if (games.get(playerToGame.get(player)).getFinishPoint(player)) { return 1;}
        return 0;
    }

    private int getAdjacentScore(String player) {
        return games.get(playerToGame.get(player)).getAdjacentScore(player);
    }

    private int getPersonalScore( String player) {
        return games.get(playerToGame.get(player)).getPersonalScore(player);
    }


    //------------GAME STATE FUNCTIONS----------------

    public GameState getGameState(String player) {
        Integer game = playerToGame.get(player);
        Board board = GameManager.getInstance().getBoard(game);
        CommonGoal[] commonGoals = GameManager.getInstance().getCommonGoals(game);
        int playerSeat = GameManager.getInstance().getPlayerSeat(game);
        int finishedFirst = GameManager.getInstance().getFinishedFirst(game);
        String playerTurn = GameManager.getInstance().getTurn(game);
        ArrayList<Player> players = GameManager.getInstance().getObjectPlayers(game);

        return new GameState(game, board, commonGoals, playerSeat, playerTurn, finishedFirst, players).deepClone();
    }



    public GameUpdate getGameUpdate(String player) {
        Integer game = playerToGame.get(player);
        Board board = GameManager.getInstance().getBoard(game);
        Bookshelf bookshelf = GameManager.getInstance().getBookshelf(player);
        Token[] commonTokens = GameManager.getInstance().getCommonTokens(player);
        int finishPoint = GameManager.getInstance().getFinishToken(player);
        int adjacentScore = GameManager.getInstance().getAdjacentScore(player);
        int personalScore = GameManager.getInstance().getPersonalScore(player);

        return new GameUpdate(board, bookshelf, commonTokens, finishPoint, adjacentScore,personalScore).deepClone();
    }

    //all the functions below are used to get the gameState and may become private later
    private Board getBoard(Integer game) {
        return games.get(game).getBoard();
    }

    private CommonGoal[] getCommonGoals(Integer game) {
        return games.get(game).getCommonGoals();
    }

    private int getPlayerSeat(Integer game) {
        return games.get(game).getPlayerSeat();
    }

    private int getFinishedFirst(Integer game) {
        return games.get(game).getFinishedFirst();
    }

    private ArrayList<Player> getObjectPlayers(Integer game) {
        return games.get(game).getPlayers();
    }

    private String getTurn(Integer game) {
        return games.get(game).getTurn();
    }



}
