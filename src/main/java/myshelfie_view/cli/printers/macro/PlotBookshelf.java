package myshelfie_view.cli.printers.macro;

import myshelfie_model.Tile;
import myshelfie_model.player.Bookshelf;

import java.util.HashMap;

import static myshelfie_view.cli.printers.Printer.String2CharMatrix;
import static myshelfie_view.cli.printers.Printer.Tile2Char;

public class PlotBookshelf {

    private static final int BOOKSHELF_ROW = 13;
    private static final int BOOKSHELF_COL = 21;
    char[][] bookshelf;

    public PlotBookshelf() {
        this.bookshelf = new char[BOOKSHELF_ROW][BOOKSHELF_COL];
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
                char c = Tile2Char(tiles[i][j]);
                this.bookshelf[bookshelfRowModel2CLI.get(i)][bookshelfColModel2CLI.get(j)] = c;
            }
        }
    }
}
