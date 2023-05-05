package myshelfie_view;

import myshelfie_controller.Settings;
import myshelfie_controller.ViewType;
import myshelfie_model.GameState;
import myshelfie_model.Position;
import myshelfie_view.cli.CliView;
import myshelfie_view.gui.GuiView;

import java.util.List;

public abstract class View {

    public static View getInstance() {
        if (Settings.getInstance().getViewType().equals(ViewType.CLI)) {
            return CliView.getInstance();
        } else {
            return GuiView.getInstance();
        }
    }

    public abstract void start();

    public abstract void showLogMessage(String message);
    public abstract void connectionSuccessful();
    public abstract void connectionFailed(String reason);
    public abstract void initGameState(GameState gameState);//this function is called when all
       //players join the game and it starts
    public abstract void chatIn(String sender, String Message, boolean isPublic);

    public abstract void chatOut(String to,String message);
    public abstract void messageSentSuccessfully();
    public abstract void messageSentFailure(String errorMessage);
    public abstract void playerDisconnected(String playerOut);
    public abstract void yourTurn();
    public abstract void takeFailed(String reason);
    public abstract void turnOf(String playerTurn);

    public abstract void takeTiles(List<Position> tiles, int column);
    public abstract void closeCliView();

    public abstract void displayNewSetup(GameState gameState);
}
