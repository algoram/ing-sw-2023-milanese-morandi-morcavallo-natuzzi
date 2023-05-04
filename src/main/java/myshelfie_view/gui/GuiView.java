package myshelfie_view.gui;

import myshelfie_controller.EventDispatcher;
import myshelfie_model.GameState;
import myshelfie_model.Position;
import myshelfie_view.View;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.List;

public class GuiView extends View {
    private GuiView() {

    }

    @Override
    public void showLogMessage(String message) {

    }

    @Override
    public void connectionSuccessful() {

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
    public void chatOut(String to, String message) {

    }

    @Override
    public void messageSentSuccessfully() {

    }

    @Override
    public void messageSentFailure(String errorMessage) {

    }

    @Override
    public void playerDisconnected(String playerOut) {

    }

    @Override
    public void yourTurn() {

    }

    @Override
    public void takeFailed(String reason) {

    }

    @Override
    public void turnOf(String playerTurn) {

    }

    @Override
    public void takeTile(List<Position> tiles, int column) {

    }

    @Override
    public void closeCliView() {

    }

    @Override
    public void displayNewSetup(GameState gameState) {

    }


    public static GuiView getInstance() {
        return new GuiView();
    }
}
