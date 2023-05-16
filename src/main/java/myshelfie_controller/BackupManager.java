package myshelfie_controller;

import myshelfie_model.Game;
import myshelfie_model.GameState;
import myshelfie_model.goal.Token;

import java.io.*;
import java.util.ArrayList;

public class BackupManager {

    private static BackupManager instance = null;
    private static final String FILENAME = "backup.bin";

    private BackupManager() {}

    public static BackupManager getInstance() {
        if (instance == null) {
            instance = new BackupManager();
        }

        return instance;
    }

    public void backupGames(ArrayList<Game> games) {
        ArrayList<GameState> states = new ArrayList<>();

        for (int i = 0; i < games.size(); i++) {
            Game g = games.get(i);

            Token[] topCommonGoals = new Token[2];
            topCommonGoals[0] = g.getCommonGoals()[0].peekTokens();
            topCommonGoals[1] = g.getCommonGoals()[1].peekTokens();

            GameState s = new GameState(
                    i,
                    g.getBoard(),
                    g.getCommonGoals(),
                    g.getPlayerSeat(),
                    g.getTurn(),
                    g.getFinishedFirst(),
                    g.getPlayers(),
                    topCommonGoals,
                    g.getBag()
            );

            states.add(s);
        }

        synchronized (FILENAME) {
            try {
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILENAME));

                out.writeObject(states);

                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Game> getBackup() {
        ArrayList<GameState> states = null;

        synchronized (FILENAME) {
            try {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILENAME));

                states = (ArrayList<GameState>) in.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                System.err.println("Corrupted backup file, will not restore previous games");
                e.printStackTrace();
            }
        }

        if (states == null) return null;

        ArrayList<Game> games = new ArrayList<>();

        for (GameState s : states) {
            games.add(Game.fromGameState(s));
        }

        return games;
    }

}
