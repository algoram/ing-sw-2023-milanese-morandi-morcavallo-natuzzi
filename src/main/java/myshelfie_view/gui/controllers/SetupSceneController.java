package myshelfie_view.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jdk.jshell.spi.ExecutionControl;
import myshelfie_controller.ConnectionType;
import myshelfie_controller.EventDispatcher;
import myshelfie_controller.Settings;
import myshelfie_network.rmi.RMIClient;
import myshelfie_network.socket.SocketClient;
import myshelfie_view.gui.GuiView;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class SetupSceneController {

    @FXML private TextField username;
    @FXML private TextField hostname;

    @FXML private RadioButton socket;
    @FXML private RadioButton rmi;

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
                if (Settings.DEBUG) System.err.println("SetupSceneController ERROR - Could not connect to the server");
                System.out.println("Couldn't connect to the server. Try again later...");
            }
        } else if (Settings.getInstance().getConnectionType() == ConnectionType.SOCKET) {
            try {
                SocketClient.getInstance().start(host, 19736);
            } catch (IOException e) {
                if (Settings.DEBUG) System.err.println("SetupSceneController ERROR - Could not connect to the server");
                System.out.println("Couldn't connect to the server. Try again later...");
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Unknown connection type");
            alert.showAndWait();
        }

        EventDispatcher.getInstance().connect();

        GuiView.getInstance().getGameController().setLocalUsername(user);

        Stage stage = (Stage) username.getScene().getWindow();
        stage.close();
    }

    private boolean notAvailableUsername(String nickname) {
        return nickname.isEmpty() || nickname.trim().isEmpty() || nickname.trim().equalsIgnoreCase("ALL") || nickname.startsWith("/");
    }
}
