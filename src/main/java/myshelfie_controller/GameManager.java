package myshelfie_controller;

import myshelfie_model.Game;
import myshelfie_model.GameState;
import myshelfie_model.GameUpdate;
import myshelfie_model.Position;
import myshelfie_model.board.Board;
import myshelfie_model.goal.Token;
import myshelfie_model.goal.common_goal.CommonGoal;

import myshelfie_model.player.Player;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class GameManager {

    private final ArrayList<Game> games = new ArrayList<>();
    private final HashMap<String, Integer> playerToGame = new HashMap<>();

    private static GameManager instance = null;

    private GameManager() {
    }

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
     *
     * the function checks if there is a player with the same username
     * the server should not allow that 2 different players have the same username
     * it would mean that the player had lost connection
     *
     * otherwise it checks if there is a game with a free spot
     * if there is it adds the player to the game
     * otherwise it creates a new game and adds the player to it
     *
     * @param newPlayer the player to add
     * @param players   the number of players in the game
     * @return true if the player was added, false if the addPlayer failed
     */
    public Boolean addPlayer(String newPlayer, int players) {

        if (playerToGame.containsKey(newPlayer)) { // if the player is already in a game

                //todo this function could return a string so that the client knows why the player was not added

                int numGame = playerToGame.get(newPlayer);
                int numPlayerInGame = games.get(numGame).findPlayer(newPlayer);

                int state = games.get(numGame).getPlayerStates().get(numPlayerInGame);

                switch (state) {
                    case 1: //the player is already connected
                        return false;

                    case 0: //the player lost connection

                        return games.get(numGame).addPlayer(newPlayer);

                    case -1: //the player disconnected voluntarily
                        return false;

                    default:
                        return false;
                }
        }


        //check if there is a game missing some players otherwise create a new one
        for (int i = 0; i < games.size(); i++) {

            if (games.get(i).getPlayers().size() < games.get(i).getNumberOfPlayers()) { // if the game is not full

                if (games.get(i).addPlayer(newPlayer)) {
                    playerToGame.put(newPlayer, i); // save which game the player is playing
                    return true;
                }
                else { return false;}
            }
        }

        Game game = new Game(); //otherwise create a new game
        game.startGame(players); // start it with the number of players specified

        if (game.addPlayer(newPlayer)) {
            games.add(game);
            playerToGame.put(newPlayer, games.size() - 1); // save which game the player is playing
            return true;
        }
        else { return false;}
    }


    public boolean takeTiles(String player, int column, List<Position> tiles) {
        return games.get(playerToGame.get(player)).takeTiles(player, tiles, column);
    }

    public int getNumberOfPlayers(String player) {
        return games.get(playerToGame.get(player)).getNumberOfPlayers();
    }


    /**
     * This function is called from event handler when a player disconnects from a game.
     * @param player
     */
    public void lostConnection(String player) {
        Integer game = playerToGame.get(player);
        games.get(game).lostConnection(player);
    }


    //------------PLAYER STATE FUNCTIONS----------------


    //------------GAME STATE FUNCTIONS----------------

    /**
     * Called when there is a Connect to send the entire state of the game
     * @param player the player that connected is used to know which game to return
     * @return GameState
     */
    public GameState getGameState(String player) {
        Integer game = playerToGame.get(player);
        Board board = GameManager.getInstance().getBoard(game);
        CommonGoal[] commonGoals = GameManager.getInstance().getCommonGoals(game);
        String playerSeat = games.get(game).getPlayerSeat();
        String finishedFirst = games.get(game).getFinishedFirst();
        String playerTurn = GameManager.getInstance().getTurn(player);
        ArrayList<Player> players = GameManager.getInstance().getObjectPlayers(game);

        Token[] topCommonGoals = new Token[2];

        topCommonGoals[0] = commonGoals[0].peekTokens();
        topCommonGoals[1] = commonGoals[1].peekTokens();

        GameState gameState = new GameState(game, board, commonGoals, playerSeat, playerTurn, finishedFirst, players, topCommonGoals);

        System.out.println(gameState);

        return gameState;
    }


    //all the functions below are used to get the gameState and may become private later
    private Board getBoard(Integer game) {
        return games.get(game).getBoard();
    }

    private CommonGoal[] getCommonGoals(Integer game) {
        return games.get(game).getCommonGoals();
    }

    private ArrayList<Player> getObjectPlayers(Integer game) {
        return games.get(game).getPlayers();
    }

    public String getTurn(String player){
        return games.get(playerToGame.get(player)).getTurn();
    }

    public boolean hasFinishedFirst(String player){
        return (games.get(playerToGame.get(player)).getFinishedFirst().equals(player));
    }

}
