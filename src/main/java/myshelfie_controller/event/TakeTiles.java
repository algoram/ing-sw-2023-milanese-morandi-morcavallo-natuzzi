package myshelfie_controller.event;

import myshelfie_model.Position;

import java.util.List;

public class TakeTiles extends Event{
    private List<Position> tiles;
    private int column;

    public TakeTiles(String player, String game, List<Position> tiles, int column) {
        super(player, game);
        this.tiles = tiles;
        this.column = column;
    }

    public List<Position> getTiles() {
        return tiles;
    }

    public int getColumn() {
        return column;
    }
}
