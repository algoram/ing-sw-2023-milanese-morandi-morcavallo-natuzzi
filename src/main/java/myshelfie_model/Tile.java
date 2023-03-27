package myshelfie_model;

public enum Type{
    EMPTY,
    CATS,
    BOOKS,
    GAMES,
    FRAMES,
    TROPHIES,
    PLANTS
}
public class Tile {
    private Type type;

    public Type getType(){
        return this.type;
    };

}
