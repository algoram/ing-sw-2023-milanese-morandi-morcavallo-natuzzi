package myshelfie_view.gui.controllers.tokens;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class PersonalGoalTokenController {

    @FXML private AnchorPane main;
    @FXML private ImageView image;

    public void setVisible(boolean visible) {
        main.setVisible(visible);
    }

    public void setTokenPoints(int tokenPoints) {
        image.setImage(new Image("images/scoring_tokens/scoring_" + tokenPoints + ".jpg"));
    }

}
