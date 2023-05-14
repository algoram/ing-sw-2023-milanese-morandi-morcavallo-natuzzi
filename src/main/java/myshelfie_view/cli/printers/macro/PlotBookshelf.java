package myshelfie_view.cli.printers.macro;

import myshelfie_model.Tile;
import myshelfie_model.player.Bookshelf;
import myshelfie_view.cli.printers.Color;

import java.util.HashMap;

public class PlotBookshelf {

    String[][] bookshelf;

    public PlotBookshelf(Bookshelf modelBookshelf) {
        final String[][] bookshelfImage = {
                {"╔═══╦═══╦═══╦═══╦═══╗"},//0
                {"║","   ","║","   ","║","   ","║","   ","║","   ","║"},//1
                {"╠═══╬═══╬═══╬═══╬═══╣"},//2
                {"║","   ","║","   ","║","   ","║","   ","║","   ","║"},//3
                {"╠═══╬═══╬═══╬═══╬═══╣"},//4
                {"║","   ","║","   ","║","   ","║","   ","║","   ","║"},//5
                {"╠═══╬═══╬═══╬═══╬═══╣"},//6
                {"║","   ","║","   ","║","   ","║","   ","║","   ","║"},//7
                {"╠═══╬═══╬═══╬═══╬═══╣"},//8
                {"║","   ","║","   ","║","   ","║","   ","║","   ","║"},//9
                {"╠═══╬═══╬═══╬═══╬═══╣"},//10
                {"║","   ","║","   ","║","   ","║","   ","║","   ","║"},//11
                {"╚═══╩═══╩═══╩═══╩═══╝"}//12
        };

        this.bookshelf = bookshelfImage;
        colorBookshelf(Color.BROWN);
        setTileBookshelf(modelBookshelf.getTiles());
    }
    private final HashMap<Integer,Integer> bookshelfRowModel2CLI = new HashMap<>(){{
        put(0,11);
        put(1,9);
        put(2,7);
        put(3,5);
        put(4,3);
        put(5,1);
    }};

    private final HashMap<Integer,Integer> bookshelfColModel2CLI = new HashMap<>(){{
        put(0,1);
        put(1,3);
        put(2,5);
        put(3,7);
        put(4,9);
    }};


    public String[][] getBookshelfStringMatrix() {
        return this.bookshelf;
    }

    private void colorBookshelf(Color code) {
        final HashMap<Integer,Integer[]> bookComponents = new HashMap<>() {{
                //Usages map i,j
                put(0, new Integer[]{0});
                put(1, new Integer[]{0, 2, 4, 6, 8, 10});
                put(2, new Integer[]{0});
                put(3, new Integer[]{0, 2, 4, 6, 8, 10});
                put(4, new Integer[]{0});
                put(5, new Integer[]{0, 2, 4, 6, 8, 10});
                put(6, new Integer[]{0});
                put(7, new Integer[]{0, 2, 4, 6, 8, 10});
                put(8, new Integer[]{0});
                put(9, new Integer[]{0, 2, 4, 6, 8, 10});
                put(10, new Integer[]{0});
                put(11, new Integer[]{0, 2, 4, 6, 8, 10});
                put(12, new Integer[]{0});
            }};

        for (int i = 0; i < this.bookshelf.length; i++) {
            if(bookComponents.containsKey(i)) {
                for(int j : bookComponents.get(i)) {
                    this.bookshelf[i][j] = Color.getColoredString(this.bookshelf[i][j],code );
                }
            }
        }
    };

    private void setTileBookshelf(Tile[][] tiles) {
        for (int i= 0; i < tiles.length; i++) {
            for (int j=0; j < tiles[i].length; j++) {
                String c = Color.Tile2String(tiles[i][j]);
                this.bookshelf[bookshelfRowModel2CLI.get(i)][bookshelfColModel2CLI.get(j)] = c;
            }
        }
    }
}
