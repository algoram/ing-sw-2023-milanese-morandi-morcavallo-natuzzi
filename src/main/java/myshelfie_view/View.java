package myshelfie_view;

import myshelfie_controller.EventDispatcher;
import myshelfie_controller.Settings;
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

    //TODO no exceptions
    public abstract void init() throws Exception;



}
