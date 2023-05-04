package myshelfie_model.goal;

import java.io.Serializable;

public class Token implements Serializable {
    private final int points;

    public Token(int points){
        this.points = points;
    }

    public int getPoints(){
        return points;
    }
}
