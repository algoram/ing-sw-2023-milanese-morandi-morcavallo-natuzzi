import myshelfie_controller.*;
import myshelfie_network.rmi.RMIClient;
import myshelfie_network.socket.SocketClient;
import myshelfie_view.View;
import myshelfie_view.gui.FXApp;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("GUI (g) or CLI (c)? [g/c]");

        String viewMode = in.nextLine();
        while (!viewMode.equals("g") && !viewMode.equals("c")) {
            viewMode = in.nextLine();
        }

        switch (viewMode) {
            case "g" -> {
                Settings.getInstance().setViewType(ViewType.GUI);
                FXApp.main(args);
            }
            case "c" -> Settings.getInstance().setViewType(ViewType.CLI);
        }

        View.getInstance().start();
    }

}
