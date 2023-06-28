package myshelfie_view.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import myshelfie_controller.EventDispatcher;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateGameSceneController implements Initializable {

    @FXML private AnchorPane main;
    @FXML private ChoiceBox<Integer> players;

    public void setVisible(boolean visible) {
        main.setVisible(visible);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        players.getItems().addAll(2, 3, 4);

        players.setValue(4);
    }

    @FXML protected void handleCreate() {
        int numberOfPlayers = players.getSelectionModel().getSelectedItem();

        EventDispatcher.getInstance().createGame(numberOfPlayers);
    }
}
