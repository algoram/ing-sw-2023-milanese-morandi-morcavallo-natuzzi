package myshelfie_view.gui.controllers.board;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import myshelfie_controller.Settings;
import myshelfie_model.board.Board;

import java.net.URL;
import java.util.ResourceBundle;

public class BoardController implements Initializable {

    @FXML private ImageView background;
    @FXML private GridPane grid;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized BoardController");
    }

    public void setBoard(Board board) {
        if (Settings.DEBUG) System.err.println("BoardController DEBUG - setBoard called");
    }
}
