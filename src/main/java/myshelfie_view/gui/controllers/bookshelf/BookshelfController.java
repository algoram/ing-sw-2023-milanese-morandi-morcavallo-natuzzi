package myshelfie_view.gui.controllers.bookshelf;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class BookshelfController implements Initializable {

    @FXML private VBox main;

    @FXML private Label usernameLabel;

    public void setUsername(String username) {
        usernameLabel.setText(username);
    }

    public void setVisible(boolean visible) {
        main.setVisible(visible);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        main.setVisible(false);
    }
}
