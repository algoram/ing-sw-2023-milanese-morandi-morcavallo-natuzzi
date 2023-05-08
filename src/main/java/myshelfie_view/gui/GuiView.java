package myshelfie_view.gui;
import myshelfie_model.GameState;
import myshelfie_model.Position;
import myshelfie_view.View;
import java.util.List;

public class GuiView extends View {
    private GuiView() {
        FXApp.initApplication();
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


    public static GuiView getInstance() {
        return new GuiView();
    }

    @Override
    public void start() {

    }
}
