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

    private final HashMap<Integer, Game> games = new HashMap<>();
    private final HashMap<String, Integer> playerToGame = new HashMap<>();

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

    public boolean removePlayer(Integer game, String player) {
        return games.get(game).removePlayer(player);
    }

    public String[] getPlayers(Integer game) {
        return games.get(game).getPlayersUsernames();
    }

    /**
     * This function is calles from event handler when a player connects to a game.
     * if the game doesn't exist it will be created.
     * @param newPlayer the player to add
     * @return true if the player was added, false if the addplayer failed
     */
    public boolean addPlayer(String newPlayer, int players) {

        Integer index = 0;

        // if there is no game with empty seats, create a new game with the given number of players
        for (Map.Entry<Integer, Game> entry : games.entrySet()) {

            if (index < entry.getKey()){ index = entry.getKey(); } //save the largest index

            if (entry.getValue().getNumberOfPlayers() > entry.getValue().getPlayers().size()){

                playerToGame.put(newPlayer, entry.getKey());  //added player to game in GameManager
                return entry.getValue().addPlayer(newPlayer);         //added player to game in Game

            }
        }
        // if there is no game with empty seats, create a new game with the given number of players

        Game newGame = new Game();
        newGame.startGame(players);
        games.put(index+1, newGame);
        playerToGame.put(newPlayer, index+1);
        return newGame.addPlayer(newPlayer);

    }


    public boolean takeTiles(Integer game, String player, int column, List<Position> tiles) {
        return games.get(game).takeTiles(player, tiles, column);
    }

    public int getNumberOfPlayers(Integer game) {
        return games.get(game).getNumberOfPlayers();
    }


    public Integer lostConnection(String player) {
        Integer game = playerToGame.get(player);
        //if (game == null) { return false; }
        games.get(game).lostConnection(player);
        return game;
    }


    //------------PLAYER STATE FUNCTIONS----------------

    public Bookshelf getBookshelf(Integer game, String player) {
        return games.get(game).getBookshelf(player);
    }

    public Token[] getCommonTokens(Integer game, String player) {
        return games.get(game).getCommonGoalTokens(player);
    }

    public int getFinishToken(Integer game, String player) {
        if (games.get(game).getFinishPoint(player)) { return 1;}
        return 0;
    }

    public int getAdjacentScore(Integer game, String player) {
        return games.get(game).getAdjacentScore(player);
    }

    public int getPersonalScore(Integer game, String player) {
        return games.get(game).getPersonalScore(player);
    }


    //------------GAME STATE FUNCTIONS----------------

    public GameState getGameState(Integer game) {
        Board board = GameManager.getInstance().getBoard(game);
        CommonGoal[] commonGoals = GameManager.getInstance().getCommonGoals(game);
        int playerSeat = GameManager.getInstance().getPlayerSeat(game);
        int finishedFirst = GameManager.getInstance().getFinishedfirst(game);
        int turn = GameManager.getInstance().getTurn(game);
        ArrayList<Player> players = GameManager.getInstance().getObjectPlayers(game);

        return new GameState(game, board, commonGoals, playerSeat, turn, finishedFirst, players);
    }

    public GameUpdate getGameUpdate(Integer game,String player) {
        Board board = GameManager.getInstance().getBoard(game);
        Bookshelf bookshelf = GameManager.getInstance().getBookshelf(game, player);
        Token[] commontokens = GameManager.getInstance().getCommonTokens(game, player);
        int finishPoint = GameManager.getInstance().getFinishToken(game, player);
        int adjacentScore = GameManager.getInstance().getAdjacentScore(game, player);
        int personalScore = GameManager.getInstance().getPersonalScore(game, player);
        return new GameUpdate(board, bookshelf, commontokens, finishPoint, adjacentScore,personalScore)
    }

    //all the functions below are used to get the gamestate and may became private later
    public Board getBoard(Integer game) {
        return games.get(game).getBoard();
    }

    public CommonGoal[] getCommonGoals(Integer game) {
        return games.get(game).getCommonGoals();
    }

    public int getPlayerSeat(Integer game) {
        return games.get(game).getPlayerSeat();
    }

    public int getFinishedfirst(Integer game) {
        return games.get(game).getFinishedFirst();
    }

    public ArrayList<Player> getObjectPlayers(String game) {
        return games.get(game).getPlayers();
    }

    public int getTurn(Integer game) {
        return games.get(game).getTurn();
    }



}
