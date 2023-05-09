package myshelfie_view.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FXApp extends Application {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final String TITLE = "MyShelfie";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/GameScene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root, WIDTH, HEIGHT);

        //showSetup();

        //GuiView.getInstance().askConnection();

        //GuiView.getInstance().askHostname();

        //GuiView.getInstance().askLogin();

        primaryStage.setTitle(TITLE);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void showSetup() {
        Stage stage = new Stage();

        try {
            Parent setupScene = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/SetupScene.fxml"));

            stage.setScene(new Scene(setupScene));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
