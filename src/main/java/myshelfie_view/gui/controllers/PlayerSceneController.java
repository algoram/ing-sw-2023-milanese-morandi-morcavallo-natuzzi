package myshelfie_view.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import myshelfie_model.GameState;
import myshelfie_model.player.Player;
import myshelfie_view.gui.GuiState;
import myshelfie_view.gui.GuiView;
import myshelfie_view.gui.controllers.bookshelf.BookshelfController;

import java.net.URL;
import java.util.ResourceBundle;

public class PlayerSceneController implements Initializable {

    private int currentIndex;

    @FXML private AnchorPane main;
    @FXML private ImageView chair;
    @FXML private ImageView token1;
    @FXML private ImageView token2;
    @FXML private ImageView token3;
    @FXML private Label username;

    @FXML private ImageView left;
    @FXML private ImageView right;

    @FXML private BookshelfController bookshelfController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bookshelfController.setVisible(true);
    }

    public void setVisible(boolean visible) {
        main.setVisible(visible);
    }

    public void display(GameState gameState, int playerIndex) {
        Player player = gameState.getPlayers().get(playerIndex);

        if (gameState.getPlayers().size() == 2) {
            left.setVisible(false);
            right.setVisible(false);
        } else {
            left.setVisible(true);
            right.setVisible(true);
        }

        currentIndex = playerIndex; // update current index
        username.setText(player.getUsername()); // get the username
        chair.setVisible(gameState.getPlayerSeat().equals(player.getUsername())); // get the chair
        bookshelfController.setBookshelf(player.getBookshelf()); // get the bookshelf

        // get the tokens
        int tokensSet = 0;
        ImageView[] tokens = new ImageView[] { token1, token2, token3 };

        if (player.getUsername().equals(gameState.getFinishedFirst())) {
            tokens[tokensSet].setImage(new Image("images/scoring_tokens/end game.jpg"));
            tokens[tokensSet].setVisible(true);
            tokensSet++;
        }

        if (player.getCommonTokens()[0] != null) {
            tokens[tokensSet++].setImage(new Image("images/scoring_tokens/scoring_" + player.getCommonTokens()[0].getPoints() + ".jpg"));
            tokens[tokensSet].setVisible(true);
            tokensSet++;
        }

        if (player.getCommonTokens()[1] != null) {
            tokens[tokensSet++].setImage(new Image("images/scoring_tokens/scoring_" + player.getCommonTokens()[1].getPoints() + ".jpg"));
            tokens[tokensSet].setVisible(true);
            tokensSet++;
        }

        while (tokensSet < 3) {
            tokens[tokensSet].setVisible(false);
            tokensSet++;
        }
    }

    @FXML protected void nextPlayer() {
        GuiView.getInstance().getMainController().getGameController().nextPlayer(currentIndex);
    }

    @FXML protected void previousPlayer() {
        GuiView.getInstance().getMainController().getGameController().previousPlayer(currentIndex);
    }

    @FXML protected void goBack() {
        GuiView.getInstance().getMainController().changeState(GuiState.GAME);
    }

}
