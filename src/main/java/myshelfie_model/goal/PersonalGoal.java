package myshelfie_model.goal;
import myshelfie_model.Position;
import myshelfie_model.Tile;
import myshelfie_model.Type;
import myshelfie_model.player.Bookshelf;
import java.util.Map;
import java.util.HashMap;

public class PersonalGoal extends Goal{

    public static final int MAX_CARDS = 12;

    private final Map<Position,Type> positions;

    /**
     * The function allocates the 'positions' attribute
     * and adds to the map all the key-value pairs corresponding
     * to the card number.
     * @param numCard is the identifier of the personalGoal card
     *                1 <= numCard <= 12
     */
    public PersonalGoal(int numCard){

        Position p1,p2,p3,p4,p5,p6;
        Type t1,t2,t3,t4,t5,t6;
        this.positions = new HashMap<>();

        switch (numCard) {
            case 1 -> {
                p1 = new Position(5, 0);
                t1 = Type.PLANTS;
                p2 = new Position(2, 1);
                t2 = Type.GAMES;
                p3 = new Position(5, 2);
                t3 = Type.FRAMES;
                p4 = new Position(0, 2);
                t4 = Type.TROPHIES;
                p5 = new Position(3, 3);
                t5 = Type.BOOKS;
                p6 = new Position(4, 4);
                t6 = Type.CATS;
            }
            case 2 -> {
                p1 = new Position(4, 1);
                t1 = Type.PLANTS;
                p2 = new Position(3, 2);
                t2 = Type.GAMES;
                p3 = new Position(0, 4);
                t3 = Type.FRAMES;
                p4 = new Position(1, 3);
                t4 = Type.TROPHIES;
                p5 = new Position(2, 4);
                t5 = Type.BOOKS;
                p6 = new Position(3, 0);
                t6 = Type.CATS;
            }
            case 3 -> {
                p1 = new Position(3, 2);
                t1 = Type.PLANTS;
                p2 = new Position(4, 3);
                t2 = Type.GAMES;
                p3 = new Position(4, 0);
                t3 = Type.FRAMES;
                p4 = new Position(2, 4);
                t4 = Type.TROPHIES;
                p5 = new Position(0, 0);
                t5 = Type.BOOKS;
                p6 = new Position(2, 1);
                t6 = Type.CATS;
            }
            case 4 -> {
                p1 = new Position(2, 3);
                t1 = Type.PLANTS;
                p2 = new Position(5, 4);
                t2 = Type.GAMES;
                p3 = new Position(3, 2);
                t3 = Type.FRAMES;
                p4 = new Position(3, 0);
                t4 = Type.TROPHIES;
                p5 = new Position(1, 1);
                t5 = Type.BOOKS;
                p6 = new Position(1, 2);
                t6 = Type.CATS;
            }
            case 5 -> {
                p1 = new Position(1, 4);
                t1 = Type.PLANTS;
                p2 = new Position(0, 0);
                t2 = Type.GAMES;
                p3 = new Position(2, 1);
                t3 = Type.FRAMES;
                p4 = new Position(4, 1);
                t4 = Type.TROPHIES;
                p5 = new Position(2, 2);
                t5 = Type.BOOKS;
                p6 = new Position(0, 3);
                t6 = Type.CATS;
            }
            case 6 -> {
                p1 = new Position(0, 0);
                t1 = Type.PLANTS;
                p2 = new Position(1, 1);
                t2 = Type.GAMES;
                p3 = new Position(1, 3);
                t3 = Type.FRAMES;
                p4 = new Position(5, 2);
                t4 = Type.TROPHIES;
                p5 = new Position(3, 3);
                t5 = Type.BOOKS;
                p6 = new Position(5, 4);
                t6 = Type.CATS;
            }
            case 7 -> {
                p1 = new Position(3, 1);
                t1 = Type.PLANTS;
                p2 = new Position(1, 4);
                t2 = Type.GAMES;
                p3 = new Position(4, 3);
                t3 = Type.FRAMES;
                p4 = new Position(2, 0);
                t4 = Type.TROPHIES;
                p5 = new Position(0, 2);
                t5 = Type.BOOKS;
                p6 = new Position(5, 0);
                t6 = Type.CATS;
            }
            case 8 -> {
                p1 = new Position(2, 0);
                t1 = Type.PLANTS;
                p2 = new Position(0, 3);
                t2 = Type.GAMES;
                p3 = new Position(5, 4);
                t3 = Type.FRAMES;
                p4 = new Position(3, 2);
                t4 = Type.TROPHIES;
                p5 = new Position(1, 3);
                t5 = Type.BOOKS;
                p6 = new Position(4, 1);
                t6 = Type.CATS;
            }
            case 9 -> {
                p1 = new Position(1, 4);
                t1 = Type.PLANTS;
                p2 = new Position(5, 2);
                t2 = Type.GAMES;
                p3 = new Position(0, 0);
                t3 = Type.FRAMES;
                p4 = new Position(1, 1);
                t4 = Type.TROPHIES;
                p5 = new Position(2, 4);
                t5 = Type.BOOKS;
                p6 = new Position(3, 2);
                t6 = Type.CATS;
            }
            case 10 -> {
                p1 = new Position(0, 3);
                t1 = Type.PLANTS;
                p2 = new Position(4, 1);
                t2 = Type.GAMES;
                p3 = new Position(1, 1);
                t3 = Type.FRAMES;
                p4 = new Position(5, 4);
                t4 = Type.TROPHIES;
                p5 = new Position(3, 0);
                t5 = Type.BOOKS;
                p6 = new Position(2, 3);
                t6 = Type.CATS;
            }
            case 11 -> {
                p1 = new Position(5, 2);
                t1 = Type.PLANTS;
                p2 = new Position(3, 0);
                t2 = Type.GAMES;
                p3 = new Position(2, 2);
                t3 = Type.FRAMES;
                p4 = new Position(0, 3);
                t4 = Type.TROPHIES;
                p5 = new Position(4, 1);
                t5 = Type.BOOKS;
                p6 = new Position(1, 4);
                t6 = Type.CATS;
            }
            case 12 -> {
                p1 = new Position(4, 1);
                t1 = Type.PLANTS;
                p2 = new Position(1, 4);
                t2 = Type.GAMES;
                p3 = new Position(3, 2);
                t3 = Type.FRAMES;
                p4 = new Position(2, 3);
                t4 = Type.TROPHIES;
                p5 = new Position(5, 2);
                t5 = Type.BOOKS;
                p6 = new Position(0, 0);
                t6 = Type.CATS;
            }
            default -> {
                System.out.println("Numero di PersonalGoalCard non valido!");
                return;
            }
        }

        positions.put(p1,t1);
        positions.put(p2,t2);
        positions.put(p3,t3);
        positions.put(p4,t4);
        positions.put(p5,t5);
        positions.put(p6,t6);

    }

    public Map<Position, Type> getPositions() {
        return new HashMap<>(positions);
    }


    /**
     * @param b the player's bookshelf
     * @return an integer: the points of the personal Goal
     */
    public int getPersonalGoalPoints(Bookshelf b){
        int match = 0; //saves the number of tile in the position of the personal goal
        Tile[][] tiles = b.getTiles();
        Position position;
        Type type;

        for (Map.Entry<Position, Type> entry : positions.entrySet()){
            position = entry.getKey();
            type = entry.getValue();
            // if (tiles[position.getColumn()][position.getRow()].getType() == type){
            if (tiles[position.getRow()][position.getColumn()]!=null && tiles[position.getRow()][position.getColumn()].getType() == type ){
                match++;
            }
        }
        if (match <= 2){
            return match;
        } else if (match == 3) {
            return 4;
        }
        else if (match == 4) {
            return 6;
        }
        else if (match == 5) {
            return 9;
        }
        else {
            return 12;
        }
    }

    public Bookshelf map_PGoalToBookshelf(){
        Bookshelf b = new Bookshelf();
        Tile[][] tiles = new Tile[6][5];

        for (Map.Entry<Position, Type> entry : positions.entrySet()){
            tiles = b.getTiles();
            tiles[entry.getKey().getRow()][entry.getKey().getColumn()] = new Tile(entry.getValue());
        }

        b.setTiles(tiles);
        return b;
    }
}