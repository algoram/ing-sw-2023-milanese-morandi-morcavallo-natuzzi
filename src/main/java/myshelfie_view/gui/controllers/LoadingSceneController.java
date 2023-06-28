package myshelfie_view.gui.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import myshelfie_controller.Settings;
import myshelfie_view.gui.GuiState;
import myshelfie_view.gui.GuiView;

import java.net.URL;
import java.util.ResourceBundle;

public class LoadingSceneController implements Initializable {

    @FXML private AnchorPane main;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                if (Settings.DEBUG) System.err.println("Interrupted while sleeping");
            }

            GuiView.getInstance().getMainController().changeState(GuiState.LOGIN);
        }).start();
    }

    public void setVisible(boolean visible) {
        main.setVisible(visible);
    }
}
