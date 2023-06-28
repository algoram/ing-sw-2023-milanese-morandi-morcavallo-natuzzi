package myshelfie_view.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class QueueSceneController {

    @FXML private AnchorPane main;
    @FXML private Label message;

    public void setVisible(boolean visible) {
        main.setVisible(visible);
    }

    public void setQueuePosition(int position) {
        message.setText("Waiting in the queue: " + position + " people are in front of you");
    }

}
