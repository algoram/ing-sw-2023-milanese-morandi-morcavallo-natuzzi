package myshelfie_view.gui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import myshelfie_controller.Settings;
import myshelfie_view.gui.controllers.GameController;
import myshelfie_view.gui.controllers.SetupSceneController;

import java.io.IOException;

public class FXApp extends Application {
    private static final String TITLE = "MyShelfie";

    public static void main(String[] args) {
        for (String s : args) {
            System.out.println(s);
        }
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
