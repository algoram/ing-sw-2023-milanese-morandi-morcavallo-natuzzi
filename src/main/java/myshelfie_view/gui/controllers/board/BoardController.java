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
import myshelfie_view.gui.GuiView;
import myshelfie_view.gui.controllers.tiles.TileController;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BoardController implements Initializable {

    @FXML private ImageView background;
    @FXML private GridPane grid;

    private TileController[][] tileControllers;

    private ArrayList<int[]> chosenTiles = new ArrayList<>();

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

                assert tile != null;
                int finalI = i;
                int finalJ = j;
                tile.setOnMouseClicked(event -> {
                    // already selected, remove it
                    if (chosenTiles.stream().anyMatch(t -> t[0] == finalI && t[1] == finalJ)) {
                        chosenTiles.removeIf(t -> t[0] == finalI && t[1] == finalJ);
                        System.out.println("Removed " + finalI + " " + finalJ);
                    }
                    // TODO: check if tile is empty, avoid taking tiles in illegal positions
                    else if (chosenTiles.size() < 3) {
                        // not selected, insert it
                        chosenTiles.add(new int[] { finalI, finalJ, tileControllers[finalI][finalJ].getImageIndex() });
                        System.out.println("Inserted " + finalI + " " + finalJ);
                    }

                    GuiView.getInstance().getGameController().setChosenTiles(chosenTiles);
                });
            }
        }
    }

    public void clearChosen() {
        chosenTiles.clear();
        GuiView.getInstance().getGameController().setChosenTiles(chosenTiles);
    }

    public void setBoard(Board board) {
        if (Settings.DEBUG) System.err.println("BoardController DEBUG - setBoard called");

        Tile[][] tiles = board.getTiles();

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                Tile tile = tiles[8-i][j];

                if (tiles[8-i][j] != null) {
                    System.out.print(tile.getType().toString());
                    tileControllers[i][j].setTile(tile.getType());
                } else {
                    System.out.print("X");
                    tileControllers[i][j].setTile(null);
                }
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}
