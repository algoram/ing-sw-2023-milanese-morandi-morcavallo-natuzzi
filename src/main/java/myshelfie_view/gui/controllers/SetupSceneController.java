package myshelfie_view.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jdk.jshell.spi.ExecutionControl;
import myshelfie_controller.ConnectionType;
import myshelfie_controller.Settings;

import java.net.URL;
import java.util.ResourceBundle;

public class SetupSceneController implements Initializable {

    @FXML private TextField username;
    @FXML private TextField hostname;

    @FXML private RadioButton socket;
    @FXML private RadioButton rmi;

    @FXML private ChoiceBox<Integer> players;

    @FXML protected void handleStart(ActionEvent event) {
        String user = username.getText();
        String host = hostname.getText();



        // TODO: check if username and hostname are valid

        Settings.getInstance().setUsername(user);

        if (socket.isSelected()) {
            Settings.getInstance().setConnectionType(ConnectionType.SOCKET);
        } else if (rmi.isSelected()) {
            Settings.getInstance().setConnectionType(ConnectionType.RMI);
        } else {
            System.err.println("SetupSceneController ERROR - Unknown connection type");
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        for (int i = 2; i <= 4; i++) {
            players.getItems().add(i);
        }
    }

}
