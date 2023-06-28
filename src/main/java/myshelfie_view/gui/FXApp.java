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

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/GameScene.fxml"));

        try {
            root = loader.load();
        } catch (IOException e) {
            if (Settings.DEBUG) System.err.println("FXApp ERROR - Couldn't load fxml file");
        }

        GuiView.getInstance().setGameController(loader.getController());

        Scene scene = new Scene(root);

        showSetup();

        primaryStage.setTitle(TITLE);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0); // TODO: vedere se c'e' qualche opzione migliore di questa
        });
    }

    public void showSetup() {
        Stage stage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/SetupScene.fxml"));

        try {
            Parent setupScene = loader.load();
            GuiView.getInstance().setSetupSceneController(loader.getController());

            stage.setScene(new Scene(setupScene));
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            if (Settings.DEBUG) System.err.println("FXApp ERROR - Couldn't load fxml file");
        }
    }
}
