package myshelfie_model;

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
}
