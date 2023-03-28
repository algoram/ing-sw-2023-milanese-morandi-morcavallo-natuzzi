package myshelfie_model.goal;
import myshelfie_model.Tile;
import myshelfie_model.Type;
import myshelfie_model.player.Bookshelf;
import java.util.Map;
import java.util.HashMap;

public class PersonalGoal extends Goal{

    private Map<Position,Type> positions;


    public PersonalGoal() {

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
            if (tiles[position.getColumn()][position.getRow()].getType() == type){
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
}