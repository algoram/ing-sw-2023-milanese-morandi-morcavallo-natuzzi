package myshelfie_model;

import java.util.Random;

public enum Type {
    CATS,
    BOOKS,
    GAMES,
    FRAMES,
    TROPHIES,
    PLANTS;

    public static Type getRandomType() {
        return values()[(int) (Math.random() * values().length)];
    }

    public static String getImagePath(Type type, int index) {
        StringBuilder filePath = new StringBuilder("images/item_tiles/");

        switch (type) {
            case FRAMES -> filePath.append("Cornici1.");
            case CATS -> filePath.append("Gatti1.");
            case GAMES -> filePath.append("Giochi1.");
            case BOOKS -> filePath.append("Libri1.");
            case PLANTS -> filePath.append("Piante1.");
            case TROPHIES -> filePath.append("Trofei1.");
        }

        filePath.append(index);
        filePath.append(".png");

        return filePath.toString();
    }
}
