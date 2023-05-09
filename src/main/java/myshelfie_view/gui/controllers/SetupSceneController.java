package myshelfie_view.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jdk.jshell.spi.ExecutionControl;
import myshelfie_controller.ConnectionType;
import myshelfie_controller.Settings;

public class SetupSceneController {

    @FXML private TextField username;
    @FXML private TextField hostname;

    @FXML private RadioButton socket;
    @FXML private RadioButton rmi;

    @FXML private RadioButton players2;
    @FXML private RadioButton players3;
    @FXML private RadioButton players4;

    @FXML protected void handleStart(ActionEvent event) {
        String user = username.getText();
        String host = hostname.getText();

        // TODO: change from radio buttons to choice box
        int players = 0;

        if (players2.isSelected()) players = 2;
        if (players3.isSelected()) players = 3;
        if (players4.isSelected()) players = 4;

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

}
