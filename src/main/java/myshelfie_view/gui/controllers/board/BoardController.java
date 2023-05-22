package myshelfie_view.gui.controllers.board;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import myshelfie_controller.Settings;
import myshelfie_model.Tile;
import myshelfie_model.board.Board;
import myshelfie_view.gui.controllers.tiles.TileController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BoardController implements Initializable {

    @FXML private ImageView background;
    @FXML private GridPane grid;

    private TileController[][] tileControllers;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized BoardController");

        tileControllers = new TileController[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/tiles/Tile.fxml"));
                Parent tile = null;

                try {
                    tile = loader.load();
                } catch (IOException e) {
                    if (Settings.DEBUG) System.err.println("BoardController ERROR - Couldn't load FXML file");
                }

                grid.add(tile, j, i);

                tileControllers[i][j] = loader.getController();
            }
        }
    }

    public void setBoard(Board board) {
        if (Settings.DEBUG) System.err.println("BoardController DEBUG - setBoard called");

        Tile[][] tiles = board.getTiles();

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if (tiles[i][j] != null) {
                    tileControllers[8-i][j].setTile(tiles[i][j].getType());
                } else {
                    tileControllers[8-1][j].setTile(null);
                }
            }
        }
    }
}
