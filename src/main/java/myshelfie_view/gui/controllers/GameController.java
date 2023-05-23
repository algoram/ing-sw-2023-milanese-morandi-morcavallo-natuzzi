package myshelfie_view.gui.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import myshelfie_controller.EventDispatcher;
import myshelfie_controller.Settings;
import myshelfie_model.GameState;
import myshelfie_model.Position;
import myshelfie_model.Tile;
import myshelfie_model.Type;
import myshelfie_model.goal.Token;
import myshelfie_model.goal.common_goal.CommonGoal;
import myshelfie_model.player.Bookshelf;
import myshelfie_model.player.Player;
import myshelfie_view.gui.controllers.board.BoardController;
import myshelfie_view.gui.controllers.bookshelf.BookshelfController;
import myshelfie_view.gui.controllers.chat.ChatController;
import myshelfie_view.gui.controllers.common_goals.CommonGoalController;
import myshelfie_view.gui.controllers.personal_goal.PersonalGoalController;
import myshelfie_view.gui.controllers.tokens.ScoringTokenController;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class GameController implements Initializable {

    // attributes
    private GameState lastGameState;
    private List<Position> chosenIndexes;
    private ArrayList<ChatMessage> messages = new ArrayList<>();
    private Queue<ChatMessage> sentMessages = new LinkedList<>(); // waiting for confirmation

    // components
    @FXML private VBox chosenTiles;

    // controllers
    @FXML private BoardController boardController;

    @FXML private ScoringTokenController token1Controller;
    @FXML private ScoringTokenController token2Controller;
    @FXML private PersonalGoalController personalGoalController;

    @FXML private BookshelfController player1Controller; // local player
    @FXML private BookshelfController player2Controller;
    @FXML private BookshelfController player3Controller;
    @FXML private BookshelfController player4Controller;

    private ChatController chatController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized GameController");
        player1Controller.setVisible(true); // show the local player
        chosenIndexes = new ArrayList<>();
    }

    public void setGameState(GameState gameState) {
        System.out.println("GameController::setGameState called");

        lastGameState = gameState;

        // separate local and remote players
        Player localPlayer = null;
        ArrayList<Player> remotePlayers = new ArrayList<>();
        for (Player p : gameState.getPlayers()) {
            if (p.getUsername().equals(Settings.getInstance().getUsername())) {
                localPlayer = p;
            } else {
                remotePlayers.add(p);
            }
        }

        if (localPlayer == null) {
            if (Settings.DEBUG) System.err.println("GameController ERROR - localPlayer is null");
            return;
        }

        // set the number of players
        // TODO: maybe avoid doing this every time there is an update
        int numPlayers = gameState.getPlayers().size();

        if (numPlayers >= 2) {
            player2Controller.setVisible(true);
            player2Controller.setUsername(remotePlayers.get(0).getUsername());
        }

        if (numPlayers >= 3) {
            player3Controller.setVisible(true);
            player3Controller.setUsername(remotePlayers.get(1).getUsername());
        }

        if (numPlayers >= 4) {
            player4Controller.setVisible(true);
            player4Controller.setUsername(remotePlayers.get(2).getUsername());
        }

        // set the board state
        boardController.setBoard(gameState.getBoard());

        // set the common goals state
        Token[] commonGoalsPoints = localPlayer.getCommonTokens();

        token1Controller.setToken(commonGoalsPoints[0] != null ? commonGoalsPoints[0].getPoints() : 0);
        token2Controller.setToken(commonGoalsPoints[1] != null ? commonGoalsPoints[1].getPoints() : 0);

        // set the personal goal state
        personalGoalController.setGoal(localPlayer.getPersonalGoal().getPersonalGoalNumber());

        // set the bookshelves
        Bookshelf bookshelf = localPlayer.getBookshelf();
        player1Controller.setBookshelf(bookshelf);

        if (numPlayers >= 2) {
            player2Controller.setBookshelf(remotePlayers.get(0).getBookshelf());
        }

        if (numPlayers >= 3) {
            player3Controller.setBookshelf(remotePlayers.get(1).getBookshelf());
        }

        if (numPlayers >= 4) {
            player4Controller.setBookshelf(remotePlayers.get(2).getBookshelf());
        }
    }

    public void setLocalUsername(String username) {
        player1Controller.setUsername(username);
    }

    public void setChosenTiles(ArrayList<int[]> tileIndexes) {
        chosenTiles.getChildren().clear();

        chosenIndexes.clear();
        tileIndexes.forEach(index -> {
            chosenIndexes.add(new Position( 8 - index[0], index[1]));
        });

        ArrayList<ImageView> images = new ArrayList<>();
        Tile[][] boardTiles = lastGameState.getBoard().getTiles();

        for (int i = tileIndexes.size() - 1; i >= 0; i--) {
            int r = tileIndexes.get(i)[0];
            int c = tileIndexes.get(i)[1];
            int img = tileIndexes.get(i)[2];

            ImageView imageView = new ImageView(new Image(Type.getImagePath(boardTiles[8-r][c].getType(), img)));
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);

            images.add(imageView);
        }

        chosenTiles.getChildren().addAll(images);
    }

    public void takeTiles(int column) {
        if (chosenIndexes.size() > 0) {
            EventDispatcher.getInstance().takeTiles(chosenIndexes, column + 1);
            boardController.clearChosen();
        }
    }

    public void receivedMessage(String from, String message, boolean isPublic) {
        messages.add(new ChatMessage(
                from,
                isPublic ? null : Settings.getInstance().getUsername(),
                message,
                new Date()
        ));

        chatController.setMessages(messages);
    }

    public void sendMessage(String to, String message) {
        sentMessages.add(new ChatMessage(
                Settings.getInstance().getUsername(),
                to,
                message,
                new Date()
        ));
    }

    public void confirmSend() {
        messages.add(sentMessages.poll());

        chatController.setMessages(messages);
    }

    @FXML protected void displayChat() {
        Stage stage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/chat/ChatScene.fxml"));
        Parent chatScene = null;

        try {
            chatScene = loader.load();

            chatController = loader.getController();

            ArrayList<String> usernames = new ArrayList<>();
            for (Player p : lastGameState.getPlayers()) {
                if (!p.getUsername().equals(Settings.getInstance().getUsername())) {
                    usernames.add(p.getUsername());
                }
            }

            chatController.setUsernames(usernames);
            chatController.setMessages(messages);

            stage.setScene(new Scene(chatScene));
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            if (Settings.DEBUG) System.err.println("GameController ERROR - Couldn't load FXML file");
        }
    }

    @FXML protected void displayCommonGoal1() {
        displayCommonGoal(lastGameState.getCommonGoals()[0]);
    }

    @FXML protected void displayCommonGoal2() {
        displayCommonGoal(lastGameState.getCommonGoals()[1]);
    }

    private void displayCommonGoal(CommonGoal goal) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, null, ButtonType.CLOSE);

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/common_goals/CommonGoal.fxml"));

            try {
                Parent root = loader.load();
                ((CommonGoalController) loader.getController()).setCommonGoal(goal);

                alert.getDialogPane().setContent(root);
                alert.setTitle("MyShelfie");
                alert.setHeaderText(null);
                alert.setContentText(null);
                alert.setGraphic(null);

                alert.showAndWait();
            } catch (IOException e) {
                System.err.println("GameController ERROR - Couldn't load FXML file");
            }
        });
    }
}
