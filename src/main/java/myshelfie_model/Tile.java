package myshelfie_model;

import java.io.Serializable;

public class Tile implements Serializable {
    private final Type type;

    public Tile(Type t){
        this.type = t;
    }
    public Type getType(){
        return this.type;
    }
}
