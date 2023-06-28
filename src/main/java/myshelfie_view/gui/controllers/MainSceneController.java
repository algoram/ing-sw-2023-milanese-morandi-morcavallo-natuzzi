package myshelfie_view.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import myshelfie_model.GameState;
import myshelfie_view.gui.GuiState;

import java.net.URL;
import java.util.ResourceBundle;

public class MainSceneController implements Initializable {
    @FXML private LoadingSceneController loadingController;
    @FXML private LoginSceneController loginController;
    @FXML private QueueSceneController queueController;
    @FXML private CreateGameSceneController createController;
    @FXML private WaitingSceneController waitingController;
    @FXML private GameController gameController;

    private GuiState guiState;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        guiState = GuiState.LOADING;

        loginController.setVisible(false);
        queueController.setVisible(false);
        createController.setVisible(false);
        waitingController.setVisible(false);
        gameController.setVisible(false);
    }

    public void changeState(GuiState state) {
        // show only the requested state
        switch (state) {
            case LOADING -> loadingController.setVisible(true);
            case LOGIN -> loginController.setVisible(true);
            case QUEUE -> queueController.setVisible(true);
            case CREATE_GAME -> createController.setVisible(true);
            case WAITING -> waitingController.setVisible(true);
            case GAME -> gameController.setVisible(true);
        }

        // hide the old state
        switch (this.guiState) {
            case LOADING -> loadingController.setVisible(false);
            case LOGIN -> loginController.setVisible(false);
            case QUEUE -> queueController.setVisible(false);
            case CREATE_GAME -> createController.setVisible(false);
            case WAITING -> waitingController.setVisible(false);
            case GAME -> gameController.setVisible(false);
        }

        // change internal state
        this.guiState = state;
    }

    public GameController getGameController() {
        return gameController;
    }

    public QueueSceneController getQueueController() {
        return queueController;
    }
}
