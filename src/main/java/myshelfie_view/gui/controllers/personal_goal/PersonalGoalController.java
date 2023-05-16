package myshelfie_view.gui.controllers.personal_goal;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class PersonalGoalController implements Initializable {

    @FXML private ImageView goal;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized PersonalGoalController");
    }

    public void setGoal(int personalGoal) {

        if (personalGoal < 1 || personalGoal > 12) return;

        String filePath = "images/personal_goal_cards/Personal_Goals" + personalGoal + ".png";

        goal.setImage(new Image(filePath));
    }
}
