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
import myshelfie_view.cli.CliView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class GuiView extends View {
    Thread ListenerThread;
    private final Object lockInOut = new Object();
    private boolean gameIsRunning; //the loop
    private boolean chatIsRunning = false; //the moment in which the chat is available

    private Scanner scanner;
    private GameState gameState; //is the actual state of the game
    private GuiView() {
        showLogMessage("Hello World!");
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
        this.chatIsRunning = true;
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Myshelfie");
            alert.setHeaderText(null);
            alert.setContentText("Waiting for other players");
            alert.showAndWait();
        });

    }

    @Override
    public void connectionFailed(String reason) {

    }

    @Override
    public void initGameState(GameState gameState) {

    }

    @Override
    public void chatIn(String sender, String Message, boolean isPublic) {

    }

    @Override
    public void messageSentSuccessfully() {

    }

    @Override
    public void messageSentFailure(String errorMessage) {

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
        Label yourTurnLabel = new Label();
        Scene yourTurnScene = new Scene(new VBox());

        if (Settings.getInstance().DEBUG) System.out.println("lock try");

        // Aggiornare il label con il messaggio "It's your turn!"
        Platform.runLater(() -> {
            yourTurnLabel.setText("It's your turn!");
        });

        synchronized (lockInOut) {
            // Creare un bottone "Ask tiles" che richiama il metodo askTiles()
            // e successivamente invoca lockInOut.notifyAll()
            Platform.runLater(() -> {
                Button askTilesButton = new Button("Ask tiles");
                askTilesButton.setOnAction(e -> {
                    //askTiles();
                    lockInOut.notifyAll();
                });
                // Aggiungere il bottone alla finestra
                VBox vbox = new VBox(yourTurnLabel, askTilesButton);
                yourTurnScene.setRoot(vbox);
            });
        }
    }

    @Override
    public void takeFailed(String reason) {

    }

    @Override
    public void turnOf(String playerTurn) {

    }

    @Override
    public void takeTiles(List<Position> tiles, int column) {

    }

    @Override
    public void closeCliView() {

    }

    @Override
    public void displayNewSetup(GameState gameState) {

    }

    @Override
    public void gameFinished(String winner) {

    }

    @Override
    public void gameFinishedForYou() {

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

            if (twoPlayersCheckBox.isSelected()) {
                EventDispatcher.getInstance().connect(2);
            } else if (threePlayersCheckBox.isSelected()) {
                EventDispatcher.getInstance().connect(3);
            } else if (fourPlayersCheckBox.isSelected()) {
                EventDispatcher.getInstance().connect(4);
            } else {
                // handle no checkbox selected
                return;
            }

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
    private void askHostname() {
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
                        throw new RuntimeException(e);
                    }
                } else if (Settings.getInstance().getConnectionType() == ConnectionType.SOCKET) {
                    try {
                        SocketClient.getInstance().start(serverAddress, 19736);
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
        });
    }

    private void askConnection() {
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



    public static GuiView getInstance() {
        return new GuiView();
    }

    @Override
    public void start() {

    }
}
