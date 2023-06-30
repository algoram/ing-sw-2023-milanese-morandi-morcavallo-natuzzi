package myshelfie_view.gui.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import myshelfie_controller.EventDispatcher;
import myshelfie_controller.Settings;
import myshelfie_model.GameState;
import myshelfie_model.Position;
import myshelfie_model.Tile;
import myshelfie_model.Type;
import myshelfie_model.goal.PersonalGoal;
import myshelfie_model.goal.Token;
import myshelfie_model.goal.common_goal.CommonGoal;
import myshelfie_model.player.Bookshelf;
import myshelfie_model.player.Player;
import myshelfie_view.gui.GuiState;
import myshelfie_view.gui.GuiView;
import myshelfie_view.gui.controllers.board.BoardController;
import myshelfie_view.gui.controllers.bookshelf.BookshelfController;
import myshelfie_view.gui.controllers.chat.ChatController;
import myshelfie_view.gui.controllers.common_goals.CommonGoalController;
import myshelfie_view.gui.controllers.personal_goal.PersonalGoalController;
import myshelfie_view.gui.controllers.tokens.FirstPlayerTokenController;
import myshelfie_view.gui.controllers.tokens.PersonalGoalTokenController;
import myshelfie_view.gui.controllers.tokens.ScoringTokenController;

import javax.tools.Tool;
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
    @FXML private AnchorPane main;
//    @FXML private VBox chosenTiles;

    @FXML private Label username;
    @FXML private Label points;

    @FXML private ImageView tileTaken1;
    @FXML private ImageView tileTaken2;
    @FXML private ImageView tileTaken3;

    @FXML private ImageView col1;
    @FXML private ImageView col2;
    @FXML private ImageView col3;
    @FXML private ImageView col4;
    @FXML private ImageView col5;

    @FXML private StackPane tileTakenContainer1;
    @FXML private StackPane tileTakenContainer2;
    @FXML private StackPane tileTakenContainer3;
    @FXML private StackPane personalContainer;
    @FXML private StackPane common1Container;
    @FXML private StackPane common2Container;
    @FXML private StackPane finishContainer;

    @FXML private ImageView endToken;

    @FXML private AnchorPane chair;

    // controllers
    @FXML private BoardController boardController;

    @FXML private ScoringTokenController token1Controller;
    @FXML private ScoringTokenController token2Controller;
//    @FXML private PersonalGoalController personalGoalController;

    @FXML private BookshelfController player1Controller; // local player
//    @FXML private BookshelfController player2Controller;
//    @FXML private BookshelfController player3Controller;
//    @FXML private BookshelfController player4Controller;

    @FXML private PersonalGoalTokenController personalController;

    @FXML private FirstPlayerTokenController chairController;

    private ChatController chatController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized GameController");
        player1Controller.setVisible(true); // show the local player
        chosenIndexes = new ArrayList<>();

        token1Controller.setVisible(false);
        token2Controller.setVisible(false);

        personalController.setVisible(false);

        chairController.setVisible(false);

        Tooltip.install(tileTakenContainer1, new Tooltip("First chosen tile"));
        Tooltip.install(tileTakenContainer2, new Tooltip("Second chosen tile"));
        Tooltip.install(tileTakenContainer3, new Tooltip("Third chosen tile"));
        Tooltip.install(personalContainer, new Tooltip("Personal goal token"));
        Tooltip.install(common1Container, new Tooltip("First common goal token"));
        Tooltip.install(common2Container, new Tooltip("Second common goal token"));
        Tooltip.install(finishContainer, new Tooltip("Finished first token"));
        Tooltip.install(chair, new Tooltip("You are the first player in order"));

        ImageView[] columns = new ImageView[] { col1, col2, col3, col4, col5 };

        for (int i = 0; i < columns.length; i++) {
            final int colIndex = i;

            columns[i].setOnMouseClicked(e -> takeTiles(colIndex));
        }
    }

    public void setUsername(String username) {
        this.username.setText(username);
    }

    public void setVisible(boolean visible) {
        main.setVisible(visible);
    }

    public void setArrowsVisible(boolean visible) {
        col1.setVisible(visible);
        col2.setVisible(visible);
        col3.setVisible(visible);
        col4.setVisible(visible);
        col5.setVisible(visible);
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

        int totalPoints = localPlayer.getPersonalGoalPoints()
                + localPlayer.getCommonGoalPoints()
                + localPlayer.getAdjacentPoints()
                + (localPlayer.getFinishedFirst() ? 1 : 0);

        points.setText("Total Points: " + totalPoints);

        if (gameState.getPlayerSeat().equals(localPlayer.getUsername())) {
            chairController.setVisible(true);
        }

        // set the number of players
        // TODO: maybe avoid doing this every time there is an update
        int numPlayers = gameState.getPlayers().size();

//        if (numPlayers >= 2) {
//            player2Controller.setVisible(true);
//            player2Controller.setUsername(remotePlayers.get(0).getUsername());
//        }
//
//        if (numPlayers >= 3) {
//            player3Controller.setVisible(true);
//            player3Controller.setUsername(remotePlayers.get(1).getUsername());
//        }
//
//        if (numPlayers >= 4) {
//            player4Controller.setVisible(true);
//            player4Controller.setUsername(remotePlayers.get(2).getUsername());
//        }

        // set the board state
        boardController.setBoard(gameState.getBoard());
        boardController.setEndTokenVisible(gameState.getFinishedFirst() == null);
        endToken.setVisible(Settings.getInstance().getUsername().equals(gameState.getFinishedFirst()));

        // set the common goals state
        Token[] commonGoalsPoints = localPlayer.getCommonTokens();

        if (commonGoalsPoints[0] != null) {
            token1Controller.setToken(commonGoalsPoints[0].getPoints());
            token1Controller.setVisible(true);
        } else {
            token1Controller.setVisible(false);
        }

        if (commonGoalsPoints[1] != null) {
            token2Controller.setToken(commonGoalsPoints[1].getPoints());
            token2Controller.setVisible(true);
        } else {
            token2Controller.setVisible(false);
        }

        // set the personal goal state
//        personalGoalController.setGoal(localPlayer.getPersonalGoal().getPersonalGoalNumber());

        // set personal goal points
        if (localPlayer.getPersonalGoalPoints() != 0) {
            personalController.setTokenPoints(localPlayer.getPersonalGoalPoints());
            personalController.setVisible(true);
        } else {
            personalController.setVisible(false);
        }

        // set the bookshelves
        Bookshelf bookshelf = localPlayer.getBookshelf();
        player1Controller.setBookshelf(bookshelf);

//        if (numPlayers >= 2) {
//            player2Controller.setBookshelf(remotePlayers.get(0).getBookshelf());
//        }
//
//        if (numPlayers >= 3) {
//            player3Controller.setBookshelf(remotePlayers.get(1).getBookshelf());
//        }
//
//        if (numPlayers >= 4) {
//            player4Controller.setBookshelf(remotePlayers.get(2).getBookshelf());
//        }
    }

    public void setChosenTiles(ArrayList<int[]> tileIndexes) {
//        chosenTiles.getChildren().clear();
        ImageView[] tilesTaken = new ImageView[] { tileTaken1, tileTaken2, tileTaken3 };

        for (ImageView imageView : tilesTaken) {
            imageView.setImage(null);
        }

        chosenIndexes.clear();
        tileIndexes.forEach(index -> {
            chosenIndexes.add(new Position( 8 - index[0], index[1]));
        });

        ArrayList<ImageView> images = new ArrayList<>();
        Tile[][] boardTiles = lastGameState.getBoard().getTiles();



        for (int i = 0; i < tileIndexes.size(); i++) {
            int r = tileIndexes.get(i)[0];
            int c = tileIndexes.get(i)[1];
            int img = tileIndexes.get(i)[2];

            tilesTaken[i].setImage(new Image(Type.getImagePath(boardTiles[8-r][c].getType(), img)));
        }

        // hide the columns when you don't have enough free space
        int numberOfChosenTiles = tileIndexes.size();
        ImageView[] columns = new ImageView[] { col1, col2, col3, col4, col5 };
        lastGameState.getPlayers().forEach(p -> {
            if (p.getUsername().equals(Settings.getInstance().getUsername())) {
                int[] freeTiles = p.getBookshelf().emptyCol();

                for (int i = 0; i < columns.length; i++) {
                    if (freeTiles[i] >= numberOfChosenTiles) {
                        columns[i].setVisible(true);
                    } else {
                        columns[i].setVisible(false);
                    }
                }
            }
        });

//        chosenTiles.getChildren().addAll(images);
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

    public void nextPlayer(int currentIndex) {
        int newIndex = (currentIndex + 1) % lastGameState.getPlayers().size();

        while (lastGameState.getPlayers().get(newIndex).getUsername().equals(Settings.getInstance().getUsername())) {
            newIndex = (newIndex + 1) % lastGameState.getPlayers().size();
        }

        GuiView.getInstance().getMainController().getPlayersController().display(lastGameState, newIndex);
    }

    public void previousPlayer(int currentIndex) {
        int size = lastGameState.getPlayers().size();
        int newIndex = (currentIndex + size - 1) % size;

        while (lastGameState.getPlayers().get(newIndex).getUsername().equals(Settings.getInstance().getUsername())) {
            newIndex = (newIndex + size - 1) % size;
        }

        GuiView.getInstance().getMainController().getPlayersController().display(lastGameState, newIndex);
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

    @FXML protected void displayPersonalGoal() {
        lastGameState.getPlayers().forEach(p -> {
            if (p.getUsername().equals(Settings.getInstance().getUsername())) {
                displayPersonalGoal(p.getPersonalGoal());
            }
        });
    }

    private void displayPersonalGoal(PersonalGoal personalGoal) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, null, ButtonType.CLOSE);

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/personal_goal/PersonalGoal.fxml"));

            try {
                Parent root = loader.load();
                ((PersonalGoalController) loader.getController()).setGoal(personalGoal.getPersonalGoalNumber());

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

    @FXML protected void disconnect() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to disconnect?");

            alert.setTitle("MyShelfie");

            var res = alert.showAndWait();

            res.ifPresent(buttonType -> {
                if (buttonType.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                    EventDispatcher.getInstance().stopPinging();
                    Platform.exit();
                    System.exit(0);
                }
            });
        });
    }

    @FXML protected void showPlayers() {
        nextPlayer(-1); // this way it will show the first player
        GuiView.getInstance().getMainController().changeState(GuiState.PLAYERS);
    }
}
