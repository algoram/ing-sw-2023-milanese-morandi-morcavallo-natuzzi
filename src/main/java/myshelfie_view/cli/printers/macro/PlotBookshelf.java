package myshelfie_view.cli.printers.macro;

import myshelfie_model.Tile;
import myshelfie_model.player.Bookshelf;

import java.util.HashMap;

import static myshelfie_view.cli.printers.Printer.String2CharMatrix;
import static myshelfie_view.cli.printers.Printer.Tile2Char;

public class PlotBookshelf {
    char[][] bookshelf;
    public PlotBookshelf(char[][] bookshelf) {
        this.bookshelf = bookshelf;
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
        put(0,2);
        put(1,6);
        put(2,10);
        put(3,14);
        put(4,18);
    }};

    /**
     * Versione Basic B&W-> tiles char
     *
     * ╔═══╦═══╦═══╦═══╦═══╗
     * ║   ║   ║   ║   ║   ║
     * ╠═══╬═══╬═══╬═══╬═══╣
     * ║ § ║   ║   ║   ║   ║
     * ╠═══╬═══╬═══╬═══╬═══╣
     * ║ © ║   ║   ║   ║   ║
     * ╠═══╬═══╬═══╬═══╬═══╣
     * ║ ¥ ║   ║   ║   ║   ║
     * ╠═══╬═══╬═══╬═══╬═══╣
     * ║ # ║   ║   ║   ║   ║
     * ╠═══╬═══╬═══╬═══╬═══╣
     * ║ ¶ ║   ║   ║   ║   ║
     * ╚═══╩═══╩═══╩═══╩═══╝
     * CAT:		    ¥
     * BOOKS:		#
     * FRAMES:	    ¶
     * TROPHIES: 	©
     * PLANTS:	    §
     *
     * @return
     */
    public void buildBookshelf(Bookshelf modelBookshelf) {
        String bookshelfImage = """
                ╔═══╦═══╦═══╦═══╦═══╗
                ║   ║   ║   ║   ║   ║
                ╠═══╬═══╬═══╬═══╬═══╣
                ║   ║   ║   ║   ║   ║
                ╠═══╬═══╬═══╬═══╬═══╣
                ║   ║   ║   ║   ║   ║
                ╠═══╬═══╬═══╬═══╬═══╣
                ║   ║   ║   ║   ║   ║
                ╠═══╬═══╬═══╬═══╬═══╣
                ║   ║   ║   ║   ║   ║
                ╠═══╬═══╬═══╬═══╬═══╣
                ║   ║   ║   ║   ║   ║
                ╚═══╩═══╩═══╩═══╩═══╝
                """;
        this.bookshelf = String2CharMatrix(bookshelfImage);
        setTileBookshelf(modelBookshelf.getTiles());
    }

    public char[][] getBookshelfCharMatrix() {
        return this.bookshelf;
    }

    private void setTileBookshelf(Tile[][] tiles) {
        for (int i= 0; i < tiles.length; i++) {
            for (int j=0; j < tiles[i].length; j++) {
                this.bookshelf[bookshelfColModel2CLI.get(i)][bookshelfRowModel2CLI.get(j)] = Tile2Char(tiles[i][j]);;
            }
        }
    }
}
