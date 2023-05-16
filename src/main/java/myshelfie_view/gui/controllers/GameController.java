package myshelfie_view.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import myshelfie_controller.Settings;
import myshelfie_model.GameState;
import myshelfie_model.goal.Token;
import myshelfie_model.player.Player;
import myshelfie_view.gui.controllers.board.BoardController;
import myshelfie_view.gui.controllers.personal_goal.PersonalGoalController;
import myshelfie_view.gui.controllers.tokens.ScoringTokenController;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    @FXML private BoardController boardController;

    @FXML private ScoringTokenController token1Controller;
    @FXML private ScoringTokenController token2Controller;
    @FXML private PersonalGoalController personalGoalController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized GameController");
    }

    public void setGameState(GameState gameState) {
        // set the board state
        boardController.setBoard(gameState.getBoard());

        Player localPlayer = null;
        for (Player p : gameState.getPlayers()) {
            if (p.getUsername().equals(Settings.getInstance().getUsername())) {
                localPlayer = p;
            }
        }

        if (localPlayer == null) {
            if (Settings.DEBUG) System.err.println("GameController ERROR - localPlayer is null");
            return;
        }

        // set the common goals state
        Token[] commonGoalsPoints = localPlayer.getCommonTokens();

        token1Controller.setToken(commonGoalsPoints[0].getPoints());
        token2Controller.setToken(commonGoalsPoints[1].getPoints());

        // set the personal goal state
        personalGoalController.setGoal(localPlayer.getPersonalGoal().getPersonalGoalNumber());
    }
}
