package myshelfie_view;

import myshelfie_controller.EventDispatcher;
import myshelfie_controller.Settings;
import myshelfie_model.GameState;
import myshelfie_model.GameUpdate;
import myshelfie_view.cli.CliView;
import myshelfie_view.gui.GuiView;

public abstract class View {

    public static View getInstance() {
        if (Settings.getInstance().getViewType().equals("cli")) {
            return CliView.getInstanceCli();
        } else {
            return GuiView.getInstance();
        }
    }

    public abstract void showMessage(String message);
    public abstract void connectionSuccessful();
    public abstract void connectionFailed(String reason);
    public abstract void chatIn(String sender, String Message, boolean isPublic);
    public abstract void playerDisconnected(String playerOut);
    public abstract void yourTurn();
    public abstract void turnOf(String playerTurn);
    public abstract void messageSentSuccessfully();
    public abstract void messageSentFailure(String errorMessage);
    public abstract void initGameState(GameState gameState);//this function is called when all
                                                            //players join the game and it starts
    public abstract void showGameUpdate(GameUpdate gameUpdate);
    public abstract void takeFailed(String reason);

    //TODO no exceptions
    public abstract void init() throws Exception;



}
