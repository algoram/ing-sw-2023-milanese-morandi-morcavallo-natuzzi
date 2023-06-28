package myshelfie_view.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import myshelfie_controller.ConnectionType;
import myshelfie_controller.Settings;
import myshelfie_network.rmi.RMIClient;
import myshelfie_network.socket.SocketClient;
import myshelfie_view.View;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;

public class FXApp extends Application {
    private static final String TITLE = "MyShelfie";

    public static void startUI(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Parent root = null;

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/MainScene.fxml"));

        try {
            root = loader.load();
        } catch (IOException e) {
            if (Settings.DEBUG) System.err.println("FXApp ERROR - Couldn't load fxml file");
        }

        GuiView.getInstance().setMainController(loader.getController());

        Scene scene = new Scene(root);

        primaryStage.setTitle(TITLE);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0); // TODO: vedere se c'e' qualche opzione migliore di questa
        });
    }
}
