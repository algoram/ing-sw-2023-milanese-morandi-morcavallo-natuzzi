package myshelfie_view.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import myshelfie_controller.Settings;
import myshelfie_model.GameState;
import myshelfie_model.goal.Token;
import myshelfie_model.player.Player;
import myshelfie_view.gui.controllers.board.BoardController;
import myshelfie_view.gui.controllers.bookshelf.BookshelfController;
import myshelfie_view.gui.controllers.personal_goal.PersonalGoalController;
import myshelfie_view.gui.controllers.tokens.ScoringTokenController;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    @FXML private BoardController boardController;

    @FXML private ScoringTokenController token1Controller;
    @FXML private ScoringTokenController token2Controller;
    @FXML private PersonalGoalController personalGoalController;

    @FXML private BookshelfController player1Controller; // local player
    @FXML private BookshelfController player2Controller;
    @FXML private BookshelfController player3Controller;
    @FXML private BookshelfController player4Controller;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized GameController");
        player1Controller.setVisible(true); // show the local player
    }

    public void setGameState(GameState gameState) {
        System.out.println("GameController::setGameState called");

        // set the number of players
        int numPlayers = gameState.getPlayers().size();

        if (numPlayers >= 2) {
            player2Controller.setVisible(true);
        }

        if (numPlayers >= 3) {
            player3Controller.setVisible(true);
        }

        if (numPlayers >= 4) {
            player4Controller.setVisible(true);
        }

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

        token1Controller.setToken(commonGoalsPoints[0] != null ? commonGoalsPoints[0].getPoints() : 0);
        token2Controller.setToken(commonGoalsPoints[1] != null ? commonGoalsPoints[1].getPoints() : 0);

        // set the personal goal state
        personalGoalController.setGoal(localPlayer.getPersonalGoal().getPersonalGoalNumber());
    }

    public void setLocalUsername(String username) {
        player1Controller.setUsername(username);
    }
}
