package myshelfie_view.gui.controllers.tiles;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import myshelfie_model.Type;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class TileController implements Initializable {

    @FXML private ImageView tile;

    private int imageIndex;

    public void setTile(Type type) {
        if (type == null) {
            tile.setVisible(false);
            return;
        }

        String filePath = Type.getImagePath(type, imageIndex);

        tile.setImage(new Image(filePath));
        tile.setVisible(true);

        setSelected(false);
    }

    public void setSelected(boolean selected) {
        tile.setOpacity(selected ? 0.5 : 1);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imageIndex = new Random().nextInt(3) + 1;
    }

    public int getImageIndex() {
        return imageIndex;
    }
}
