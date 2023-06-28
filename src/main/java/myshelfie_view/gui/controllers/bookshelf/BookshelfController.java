package myshelfie_view.gui.controllers.bookshelf;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import myshelfie_controller.Settings;
import myshelfie_model.Tile;
import myshelfie_model.player.Bookshelf;
import myshelfie_view.gui.GuiView;
import myshelfie_view.gui.controllers.tiles.TileController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BookshelfController implements Initializable {

    @FXML private VBox main;
    @FXML private Label usernameLabel;
    @FXML private GridPane grid;

    private TileController[][] tileControllers;

    public void setUsername(String username) {
        usernameLabel.setText(username);
    }

    public void setVisible(boolean visible) {
        main.setVisible(visible);
    }

    public void setBookshelf(Bookshelf bookshelf) {
        Tile[][] tiles = bookshelf.getTiles();

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                Tile tile = tiles[5-i][j];

                if (tile != null) {
                    tileControllers[i][j].setTile(tile.getType());
                } else {
                    tileControllers[i][j].setTile(null);
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        main.setVisible(false);

        tileControllers = new TileController[6][5];

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/tiles/Tile.fxml"));
                Parent tile = null;

                try {
                    tile = loader.load();
                } catch (IOException e) {
                    if (Settings.DEBUG) System.err.println("BookshelfController ERROR - Couldn't load FXML file");
                }

                grid.add(tile, j, i);

                assert tile != null;
                int column = j;
                tile.setOnMouseClicked(event -> {
                    // try to insert the tiles into the column
                    GuiView.getInstance().getMainController().getGameController().takeTiles(column);
                });

                tileControllers[i][j] = loader.getController();
            }
        }
    }
}
