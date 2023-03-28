package myshelfie_model;

public class Tile {
    private final Type type;
    private BoardPosition position;


    public Tile(Type t){
        this.type = t;
        this.position = new BoardPosition(-1,-1);
    }
    public Type getType(){
        return this.type;
    };
    public BoardPosition getPosition(){ return this.position;};
    public void setPosition(int row,int col){
        this.position.setPosition(row,col);
    }

}
