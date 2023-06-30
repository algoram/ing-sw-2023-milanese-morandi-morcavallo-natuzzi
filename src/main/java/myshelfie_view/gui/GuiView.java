package myshelfie_view.gui;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Stage;
import myshelfie_controller.ConnectionType;
import myshelfie_controller.EventDispatcher;
import myshelfie_controller.Settings;
import myshelfie_model.GameState;
import myshelfie_model.Position;
import myshelfie_network.rmi.RMIClient;
import myshelfie_network.socket.SocketClient;
import myshelfie_view.View;
import javafx.scene.control.Alert;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import myshelfie_view.gui.controllers.MainSceneController;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Optional;

public class GuiView extends View {
    private MainSceneController mainSceneController;
    private static GuiView instance = null;

    private GuiView() {}

    public void setMainController(MainSceneController controller) {
        this.mainSceneController = controller;
    }

    public MainSceneController getMainController() {
        return mainSceneController;
    }

    @Override
    public void showLogMessage(String message) {
        if (message != null) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Show Log Message");
                alert.setHeaderText(null);
                alert.setContentText(message);
                alert.showAndWait();
            });
        }
    }

    @Override
    public void connectionSuccessful() {
        Platform.runLater(() -> {
            mainSceneController.changeState(GuiState.QUEUE);
        });
    }

    @Override
    public void connectionFailed(String reason) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("MyShelfie");
            alert.setHeaderText("ERROR");
            alert.setContentText("Connection failed - " + reason);
            alert.showAndWait();

            mainSceneController.changeState(GuiState.LOGIN);
        });
    }

    @Override
    public void initGameState(GameState gameState) {
        Platform.runLater(() -> {
            mainSceneController.getGameController().setGameState(gameState);
            mainSceneController.changeState(GuiState.GAME);
        });
    }

    @Override
    public void chatIn(String sender, String message, boolean isPublic) {
        Platform.runLater(() -> mainSceneController.getGameController().receivedMessage(sender, message, isPublic));
    }

    @Override
    public void messageSentSuccessfully() {
        Platform.runLater(() -> mainSceneController.getGameController().confirmSend());
    }

    @Override
    public void messageSentFailure(String errorMessage) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("MyShelfie");
            alert.setHeaderText("ERROR");
            alert.setContentText("Couldn't send chat message - " + errorMessage);
            alert.showAndWait();
        });
    }

    @Override
    public void playerDisconnected(String playerOut) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Player Disconnected");
            alert.setHeaderText(null);
            alert.setContentText(playerOut + " disconnected");
            alert.showAndWait();
        });
    }

    @Override
    public void yourTurn() {
        // show all column choices arrows
        mainSceneController.getGameController().setArrowsVisible(true);

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "It's you turn! Pick some tiles and choose a column to put them into.");
            alert.setTitle("MyShelfie");
            alert.showAndWait();
        });
    }

    @Override
    public void takeFailed(String reason) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("MyShelfie");
            alert.setHeaderText("ERROR");
            alert.setContentText("Couldn't take tiles - " + reason);
            alert.showAndWait();
        });
    }

    @Override
    public void takeSuccess(){
        // TODO: implement, maybe refresh view
    }

    @Override
    public void turnOf(String playerTurn) {
        // hide all column choices arrows
        mainSceneController.getGameController().setArrowsVisible(false);
    }

    @Override
    public void takeTiles(List<Position> tiles, int column) {
        // TODO: implement
    }

    @Override
    public void closeCliView() {

    }

    @Override
    public void displayNewSetup(GameState gameState) {
        Platform.runLater(() -> mainSceneController.getGameController().setGameState(gameState));
    }

    @Override
    public void gameFinished(String winner) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("MyShelfie");
            alert.setHeaderText(null);

            if (winner.equals(Settings.getInstance().getUsername())) {
                alert.setContentText("You WON!!!");
            } else {
                alert.setContentText(winner + " has won the game");
            }

            alert.showAndWait();
        });
    }

    @Override
    public void gameFinishedForYou() {
        // TODO: implement, maybe show an info dialog
    }
    public void askLogin() {
        Stage loginStage = new Stage();
        loginStage.setTitle("Login");

        // Create components
        TextField nicknameField = new TextField();
        nicknameField.setPromptText("Enter your nickname");

        CheckBox twoPlayersCheckBox = new CheckBox("2 players");
        CheckBox threePlayersCheckBox = new CheckBox("3 players");
        CheckBox fourPlayersCheckBox = new CheckBox("4 players");

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            String nickname = nicknameField.getText();
            if (nickname.startsWith("/")){
                // handle command input
                return;
            }
            if (!nickname.matches("[a-zA-Z0-9 ]+")) {
                // handle invalid nickname input
                return;
            }
            if (notAvailableUsername(nickname)) {
                // handle not available username
                return;
            }
            Settings.getInstance().setUsername(nickname);

            /*if (twoPlayersCheckBox.isSelected()) {
                EventDispatcher.getInstance().connect(2);
            } else if (threePlayersCheckBox.isSelected()) {
                EventDispatcher.getInstance().connect(3);
            } else if (fourPlayersCheckBox.isSelected()) {
                EventDispatcher.getInstance().connect(4);
            } else {
                // handle no checkbox selected
                return;
            }*/

            EventDispatcher.getInstance().connect();

            // continue with game logic
            loginStage.close();
        });

        VBox inputContainer = new VBox();
        inputContainer.setAlignment(Pos.CENTER);
        inputContainer.setSpacing(10);
        inputContainer.getChildren().addAll(nicknameField, twoPlayersCheckBox, threePlayersCheckBox, fourPlayersCheckBox, submitButton);

        Scene scene = new Scene(inputContainer, 400, 300);
        loginStage.setScene(scene);
        loginStage.showAndWait();
    }
    private boolean notAvailableUsername(String nickname) {
        return nickname.isEmpty() || nickname.trim().isEmpty() || nickname.trim().equalsIgnoreCase("ALL") || nickname.startsWith("/");
    }
    public void askHostname() {
        Platform.runLater(() -> {
            TextInputDialog dialog = new TextInputDialog("localhost");
            dialog.setTitle("Connect to server");
            dialog.setHeaderText(null);
            dialog.setContentText("Insert the server address:");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                String serverAddress = result.get();
                if (Settings.getInstance().getConnectionType() == ConnectionType.RMI) {
                    try {
                        RMIClient.getInstance().connect(serverAddress);
                    } catch (RemoteException | NotBoundException e) {
                        if (Settings.DEBUG) System.err.println("GuiView ERROR - Failed to connect to RMI Server");
                    }
                } else if (Settings.getInstance().getConnectionType() == ConnectionType.SOCKET) {
                    try {
                        SocketClient.getInstance().start(serverAddress, 19736);
                    } catch (IOException e) {
                        if (Settings.DEBUG) System.err.println("GuiView ERROR - Failed to connect to socket server");
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Unknown connection type");
                    alert.showAndWait();
                }

            }
        });
    }

    public void askConnection() {
        // Creazione della checkbox per selezionare la connessione
        CheckBox socketCheckBox = new CheckBox("Socket");
        CheckBox rmiCheckBox = new CheckBox("RMI");
        VBox vbox = new VBox(socketCheckBox, rmiCheckBox);
        Scene scene = new Scene(vbox);

        // Creazione del button per confermare la selezione
        Button confirmButton = new Button("Confirm");
        confirmButton.setOnAction(e -> {
            if (socketCheckBox.isSelected()) {
                Settings.getInstance().setConnectionType(ConnectionType.SOCKET);
            } else if (rmiCheckBox.isSelected()) {
                Settings.getInstance().setConnectionType(ConnectionType.RMI);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Please select a connection type");
                alert.showAndWait();
                return;

                
            }
            // Avvio la connessione al server
            try {
                askHostname();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore");
                alert.setHeaderText("Si è verificato un errore");
                //alert.setContentText(e.getClass());
                alert.showAndWait();
            }
        });

        // Aggiunta della scena e del button alla finestra
        vbox.getChildren().add(confirmButton);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.showAndWait();
    }
    public void askLogin_fullwindows() {
        Stage loginStage = new Stage();
        loginStage.setTitle("Login");

        // Create components
        TextField nicknameField = new TextField();
        nicknameField.setPromptText("Enter your nickname");

        RadioButton twoPlayersRadioButton = new RadioButton("2 players");
        RadioButton threePlayersRadioButton = new RadioButton("3 players");
        RadioButton fourPlayersRadioButton = new RadioButton("4 players");
        ToggleGroup playerToggleGroup = new ToggleGroup();
        playerToggleGroup.getToggles().addAll(twoPlayersRadioButton, threePlayersRadioButton, fourPlayersRadioButton);

        RadioButton socketCheckBox = new RadioButton("Socket");
        RadioButton rmiCheckBox = new RadioButton("RMI");
        ToggleGroup connectionToggleGroup = new ToggleGroup();
        connectionToggleGroup.getToggles().addAll(socketCheckBox, rmiCheckBox);

        TextField hostnameField = new TextField();
        hostnameField.setPromptText("Enter server hostname");

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            String nickname = nicknameField.getText();
            if (nickname.startsWith("/")){
                // handle command input
                return;
            }
            if (!nickname.matches("[a-zA-Z0-9 ]+")) {
                // handle invalid nickname input
                return;
            }
            if (notAvailableUsername(nickname)) {
                // handle not available username
                return;
            }
            Settings.getInstance().setUsername(nickname);

            RadioButton selectedPlayerRadioButton = (RadioButton) playerToggleGroup.getSelectedToggle();
            if (selectedPlayerRadioButton == null) {
                // handle no player selection
                return;
            }
            int numPlayers = Integer.parseInt(selectedPlayerRadioButton.getText().split(" ")[0]);
            EventDispatcher.getInstance().connect();

            RadioButton selectedConnectionCheckBox = (RadioButton) connectionToggleGroup.getSelectedToggle();
            if (selectedConnectionCheckBox == null) {
                // handle no connection selection
                return;
            }
            ConnectionType connectionType = (selectedConnectionCheckBox == socketCheckBox) ? ConnectionType.SOCKET : ConnectionType.RMI;
            Settings.getInstance().setConnectionType(connectionType);

            String serverAddress = hostnameField.getText();
            if (serverAddress.isEmpty()) {
                // handle empty hostname
                return;
            }
            if (connectionType == ConnectionType.RMI) {
                try {
                    RMIClient.getInstance().connect(serverAddress);
                } catch (RemoteException | NotBoundException ex) {
                    if (Settings.DEBUG) System.err.println("GuiView ERROR - Failed to connect to RMI Server");
                }
            } else if (connectionType == ConnectionType.SOCKET) {
                try {
                    SocketClient.getInstance().start(serverAddress, 19736);
                } catch (IOException ex) {
                    if (Settings.DEBUG) System.err.println("GuiView ERROR - Failed to connect to socket server");
                }
            }

            // continue with game logic
            loginStage.close();
        });

        VBox inputContainer = new VBox();
        inputContainer.setAlignment(Pos.CENTER);
        inputContainer.setSpacing(10);
        inputContainer.getChildren().addAll(nicknameField, twoPlayersRadioButton, threePlayersRadioButton, fourPlayersRadioButton, socketCheckBox, rmiCheckBox, hostnameField, submitButton);

        Scene scene = new Scene(inputContainer, 400, 400);
        loginStage.setScene(scene);
        loginStage.showAndWait();
    }



    public static GuiView getInstance() {
        if (instance == null) {
            instance = new GuiView();
        }

        return instance;
    }

    @Override
    public void start() {

    }

    @Override
    public void gameStarted() {
        Platform.runLater(() -> mainSceneController.changeState(GuiState.WAITING));
    }

    @Override
    public void updateQueuePosition(int newPosition) {
        System.out.println("Updating queue position: " + newPosition);
        Platform.runLater(() -> {
            if (newPosition == 0) {
                mainSceneController.changeState(GuiState.CREATE_GAME);
            } else {
                mainSceneController.changeState(GuiState.QUEUE);
                mainSceneController.getQueueController().setQueuePosition(newPosition);
            }
        });
    }

    @Override
    public void gameCreateFailure(String reason) {

    }

    @Override
    public boolean getIsMyTurn() {
        // TODO: molti di questi metodi servono solo alla CLI, bisogna toglierli dall'interface View perche' non servono alla GUI
        return false;
    }
}
