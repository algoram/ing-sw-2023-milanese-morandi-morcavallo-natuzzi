package myshelfie_view.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jdk.jshell.spi.ExecutionControl;
import myshelfie_controller.ConnectionType;
import myshelfie_controller.Settings;
import myshelfie_network.rmi.RMIClient;
import myshelfie_network.socket.SocketClient;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
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
        if (user.startsWith("/")){
            // handle command input
            return;
        }
        if (!user.matches("[a-zA-Z0-9 ]+")) {
            // handle invalid nickname input
            return;
        }
        if (notAvailableUsername(user)) {
            // handle not available username
            return;
        }
        Settings.getInstance().setUsername(user);



        // TODO: check  and hostname are valid

        Settings.getInstance().setUsername(user);

        if (socket.isSelected()) {
            Settings.getInstance().setConnectionType(ConnectionType.SOCKET);
        } else if (rmi.isSelected()) {
            Settings.getInstance().setConnectionType(ConnectionType.RMI);
        } else {
            System.err.println("SetupSceneController ERROR - Unknown connection type");
        }

        if (Settings.getInstance().getConnectionType() == ConnectionType.RMI) {
            try {
                RMIClient.getInstance().connect(host);
            } catch (RemoteException | NotBoundException e) {
                throw new RuntimeException(e);
            }
        } else if (Settings.getInstance().getConnectionType() == ConnectionType.SOCKET) {
            try {
                SocketClient.getInstance().start(host, 19736);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Unknown connection type");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        for (int i = 2; i <= 4; i++) {
            players.getItems().add(i);
        }
    }

    private boolean notAvailableUsername(String nickname) {
        return nickname.isEmpty() || nickname.trim().isEmpty() || nickname.trim().equalsIgnoreCase("ALL") || nickname.startsWith("/");
    }
}
