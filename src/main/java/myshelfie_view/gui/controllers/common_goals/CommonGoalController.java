package myshelfie_view.gui.controllers.common_goals;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import myshelfie_model.goal.common_goal.*;

public class CommonGoalController {

    @FXML private ImageView image;
    @FXML private Label description;
    @FXML private ImageView token;

    public void setCommonGoal(CommonGoal goal) {
        String filePath = "images/common_goal_cards/";

        if (goal instanceof Cross) {
            filePath += "10";
            description.setText("Five tiles of the same type forming an X.");
        } else if (goal instanceof Diagonal5Tiles) {
            filePath += "11";
            description.setText("Five tiles of the same type forming a diagonal.");
        } else if (goal instanceof EightTiles) {
            filePath += "9";
            description.setText("Eight tiles of the same type. There’s no restriction about the position of these tiles.");
        } else if (goal instanceof FourCorners) {
            filePath += "8";
            description.setText("Four tiles of the same type in the four corners of the bookshelf.");
        } else if (goal instanceof FourGroups4Tiles) {
            filePath += "3";
            description.setText("Four groups each containing at least 4 tiles of the same type (not necessarily in the depicted shape). The tiles of one group can be different from those of another group.");
        } else if (goal instanceof FourLinesMax3) {
            filePath += "7";
            description.setText("Four lines each formed by 5 tiles of maximum three different types. One line can show the same or a different combination of the other line.");
        } else if (goal instanceof Pyramid) {
            filePath += "12";
            description.setText("Five columns of increasing or decreasing height. Starting from the first column on the left or on the right, each next column must be made of exactly one more tile. Tiles can be of any type.");
        } else if (goal instanceof SixGroups2Tiles) {
            filePath += "4";
            description.setText("Six groups each containing at least 2 tiles of the same type (not necessarily in the depicted shape). The tiles of one group can be different from those of another group.");
        } else if (goal instanceof ThreeColumnsMax3) {
            filePath += "5";
            description.setText("Three columns each formed by 6 tiles of maximum three different types. One column can show the same or a different combination of another column.");
        } else if (goal instanceof TwoColumns) {
            filePath += "2";
            description.setText("Two columns each formed by 6 different types of tiles.");
        } else if (goal instanceof TwoLines) {
            filePath += "6";
            description.setText("Two lines each formed by 5 different types of tiles. One line can show the same or a different combination of the other line.");
        } else if (goal instanceof TwoSquares) {
            filePath += "1";
            description.setText("Two groups each containing 4 tiles of the same type in a 2x2 square. The tiles of one square can be different from those of the other square.");
        }

        filePath += ".jpg";

        System.err.println(filePath);
        image.setImage(new Image(filePath));

        if (goal.peekTokens() != null) {
            int points = goal.peekTokens().getPoints();

            token.setImage(new Image("images/scoring_tokens/scoring_" + points + ".jpg"));
            token.setVisible(true);
        } else {
            token.setVisible(false);
        }
    }

}
