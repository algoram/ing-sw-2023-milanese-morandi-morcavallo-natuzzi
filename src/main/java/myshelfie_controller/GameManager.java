package myshelfie_controller;

import myshelfie_model.Game;
import myshelfie_model.GameState;
import myshelfie_model.Position;
import myshelfie_model.board.Board;
import myshelfie_model.goal.Token;
import myshelfie_model.goal.common_goal.CommonGoal;
import myshelfie_model.player.Bookshelf;
import myshelfie_model.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameManager {

    private final HashMap<String, Game> games = new HashMap<>();

    private static GameManager instance = null;

    private GameManager() {}

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }

        return instance;
    }

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

    public int getNumberOfPlayers(String game) {
        return games.get(game).getNumberOfPlayers();
    }


    //------------PLAYER STATE FUNCTIONS----------------

    public Bookshelf getBookshelf(String game, String player) {
        return games.get(game).getBookshelf(player);
    }

    public Token[] getCommonTokens(String game, String player) {
        return games.get(game).getCommonGoalTokens(player);
    }

    public int getFinishToken(String game, String player) {
        if (games.get(game).getFinishPoint(player)) { return 1;}
        return 0;
    }

    public int getAdjacentScore(String game, String player) {
        return games.get(game).getAdjacentScore(player);
    }

    public int getPersonalScore(String game, String player) {
        return games.get(game).getPersonalScore(player);
    }


    //------------GAME STATE FUNCTIONS----------------

    public GameState getGameState(String game) {
        //public GameState(String gameName, Board board, CommonGoal[] commonGoals, int playerSeat,int turn, int finishedFirst, ArrayList<players
        Board board = GameManager.getInstance().getBoard(game);
        CommonGoal[] commonGoals = GameManager.getInstance().getCommonGoals(game);
        int playerSeat = GameManager.getInstance().getPlayerSeat(game);
        int finishedFirst = GameManager.getInstance().getFinishedfirst(game);
        int turn = GameManager.getInstance().getTurn(game);
        ArrayList<Player> players = GameManager.getInstance().getObjectPlayers(game);

        return new GameState(game, board, commonGoals, playerSeat, turn, finishedFirst, players);
    }

    //all the functions below are used to get the gamestate and may became private later
    public Board getBoard(String game) {
        return games.get(game).getBoard();
    }

    public CommonGoal[] getCommonGoals(String game) {
        return games.get(game).getCommonGoals();
    }

    public int getPlayerSeat(String game) {
        return games.get(game).getPlayerSeat();
    }

    public int getFinishedfirst(String game) {
        return games.get(game).getFinishedFirst();
    }

    public ArrayList<Player> getObjectPlayers(String game) {
        return games.get(game).getPlayers();
    }

    public int getTurn(String game) {
        return games.get(game).getTurn();
    }



}
