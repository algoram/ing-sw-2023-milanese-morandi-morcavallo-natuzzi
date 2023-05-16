package myshelfie_view.gui.controllers.tokens;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class ScoringTokenController implements Initializable {

    @FXML private ImageView token;

    public void setToken(int points) {
        if (points == 2) {
            token.setImage(new Image("images/scoring_tokens/scoring_2.jpg"));
        } else if (points == 4) {
            token.setImage(new Image("images/scoring_tokens/scoring_4.jpg"));
        } else if (points == 6) {
            token.setImage(new Image("images/scoring_tokens/scoring_6.jpg"));
        } else if (points == 8) {
            token.setImage(new Image("images/scoring_tokens/scoring_8.jpg"));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized ScoringTokenController");
    }
}
