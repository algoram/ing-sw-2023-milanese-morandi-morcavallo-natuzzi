package myshelfie_view.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

        token1Controller.setToken(2);
        token2Controller.setToken(6);

        personalGoalController.setGoal(1);
    }
}
