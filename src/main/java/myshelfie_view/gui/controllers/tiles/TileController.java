package myshelfie_view.gui.controllers.tiles;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import myshelfie_model.Type;

import java.util.Random;

public class TileController {

    @FXML private ImageView tile;

    public void setTile(Type type) {
        if (type == null) {
            tile.setVisible(false);
            return;
        }

        int randomIndex = new Random().nextInt(3) + 1; // number from 1 to 3
        StringBuilder filePath = new StringBuilder("images/item_tiles/");

        switch (type) {
            case FRAMES -> filePath.append("Cornici1.");
            case CATS -> filePath.append("Gatti1.");
            case GAMES -> filePath.append("Giochi1.");
            case BOOKS -> filePath.append("Libri1.");
            case PLANTS -> filePath.append("Piante1.");
            case TROPHIES -> filePath.append("Trofei1.");
        }

        filePath.append(randomIndex);
        filePath.append(".png");

        tile.setImage(new Image(filePath.toString()));
        tile.setVisible(true);
    }

}
