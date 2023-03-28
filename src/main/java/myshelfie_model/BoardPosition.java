package myshelfie_model;

public class BoardPosition{

    private int row;
    private int col;


    public BoardPosition(int row, int col){
        this.row= row;
        this.col= col;
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() { return this.col; }

    public void setPosition(int row, int col) {
        this.col = col;
        this.row = row;
    }

}