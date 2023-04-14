package myshelfie_model;

import java.io.Serializable;

public class Position implements Serializable {

    private final int row;
    private final int col;


    public Position(int row, int col){
        this.row= row;
        this.col= col;
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() { return this.col; }

    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }
}