package myshelfie_view.cli.printers.macro;

import myshelfie_model.Tile;
import myshelfie_model.board.Board;
import java.util.HashMap;

import static myshelfie_view.cli.printers.Printer.String2CharMatrix;
import static myshelfie_view.cli.printers.Printer.Tile2Char;

public class PlotBoardgame {
    private char[][] boardgame;
    private static final int BOARDGAME_COL = 41;
    private static final int BOARDGAME_ROW = 20;
    /**
     * Map for the row CORDINATES of the board3Player (and board4Player) in the model
     * into the CORDINATES of the board3Player (and board4Player) in the view
     */
    private final HashMap<Integer, Integer> boardRowModel2CLI = new HashMap<>() {{
        put(8, 2);
        put(7, 4);
        put(6, 6);
        put(5, 8);
        put(4, 10);
        put(3, 12);
        put(2, 14);
        put(1, 16);
        put(0, 18);
    }};
    /**
     * Map for the row CORDINATES of the board3Player (and board4Player) in the model
     * into the CORDINATES of the board3Player (and board4Player) in the view
     */
    private final HashMap<Integer, Integer> boardColModel2CLI = new HashMap<>() {{
        put(0, 5);
        put(1, 9);
        put(2, 13);
        put(3, 17);
        put(4,21);
        put(5,25);
        put(6,29);
        put(7,33);
        put(8,37);
    }};

    public PlotBoardgame() {
        this.boardgame = new char[BOARDGAME_ROW][BOARDGAME_COL];
        this.boardgame = boardgame;
    }
    public char[][] getBoardCharMatrix() {
        return boardgame;
    }
    /***
     * this method build the boardgame and insert all the tiles in the right position
     * @param modelBoard is the board of the model
     * @param numPlayers is the number of players: has to be 2, 3 or 4 and remain CONSTANT for the whole game
     * */
    public void buildBoard( Board modelBoard, int numPlayers) {
        switch (numPlayers)
        {
            case 2 -> Board2Players(modelBoard);
            case 3 -> Board3Players(modelBoard);
            case 4 -> Board4Players(modelBoard);
        }
    }
    /***
     ░░░░░░░░ 2 ░ 3 ░ 4 ░ 5 ░ 6 ░ 7 ░ 8 ░░░░░
     ░░
     ░░
     ░░             ╔═══╦═══╗
     B              ║   ║   ║
     ░░             ╠═══╬═══╬═══╗
     C              ║   ║   ║   ║
     ░░         ╔═══╬═══╬═══╬═══╬═══╦═══╗
     D          ║   ║   ║   ║   ║   ║   ║
     ░░     ╔═══╬═══╬═══╬═══╬═══╬═══╬═══╣
     E      ║   ║   ║   ║   ║   ║   ║   ║
     ░░     ╠═══╬═══╬═══╬═══╬═══╬═══╬═══╝
     F      ║   ║   ║   ║   ║   ║   ║
     ░░     ╚═══╩═══╬═══╬═══╬═══╬═══╝
     G              ║   ║   ║   ║
     ░░             ╚═══╬═══╬═══╣
     H                  ║   ║   ║
     ░░                 ╚═══╩═══╝
     ░░
     ░░
     *
     * */
    private void Board2Players(Board modelBoard) {
        String boardImage = """
                ░░░░░░░░ 2 ░ 3 ░ 4 ░ 5 ░ 6 ░ 7 ░ 8 ░░░░░
                ░░                                       \s
                ░░                                       \s
                ░░             ╔═══╦═══╗                 \s
                 B             ║   ║   ║                 \s
                ░░             ╠═══╬═══╬═══╗             \s
                 C             ║   ║   ║   ║             \s
                ░░         ╔═══╬═══╬═══╬═══╬═══╦═══╗     \s
                 D         ║   ║   ║   ║   ║   ║   ║     \s
                ░░     ╔═══╬═══╬═══╬═══╬═══╬═══╬═══╣     \s
                 E     ║   ║   ║   ║   ║   ║   ║   ║     \s
                ░░     ╠═══╬═══╬═══╬═══╬═══╬═══╬═══╝     \s
                 F     ║   ║   ║   ║   ║   ║   ║         \s
                ░░     ╚═══╩═══╬═══╬═══╬═══╬═══╝         \s
                 G             ║   ║   ║   ║             \s
                ░░             ╚═══╬═══╬═══╣             \s
                 H                 ║   ║   ║             \s
                ░░                 ╚═══╩═══╝             \s
                ░░                                       \s
                ░░                                       \s
                """;
        this.boardgame = String2CharMatrix(boardImage); //insert the Image
        setTileBoard(this.boardgame,modelBoard.getTiles()); //Set all the Tile
    };
    /***
     ░░░░ 1 ░ 2 ░ 3 ░ 4 ░ 5 ░ 6 ░ 7 ░ 8 ░ 9 ░
     ░░             ╔═══╗
     A              ║   ║
     ░░             ╠═══╬═══╗
     B              ║   ║   ║
     ░░         ╔═══╬═══╬═══╬═══╦═══╗
     C          ║   ║   ║   ║   ║   ║
     ░░         ╠═══╬═══╬═══╬═══╬═══╬═══╦═══╗
     D          ║   ║   ║   ║   ║   ║   ║   ║
     ░░     ╔═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╝
     E      ║   ║   ║   ║   ║   ║   ║   ║
     ░░ ╔═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╝
     F  ║   ║   ║   ║   ║   ║   ║   ║
     ░░ ╚═══╩═══╬═══╬═══╬═══╬═══╬═══╣
     G          ║   ║   ║   ║   ║   ║
     ░░         ╚═══╩═══╬═══╬═══╬═══╝
     H                  ║   ║   ║
     ░░                 ╚═══╬═══╣
     I                      ║   ║
     ░░                     ╚═══╝

     */
    private void Board3Players(Board modelBoard) {
        String boardImage = """
                 ░░░░ 1 ░ 2 ░ 3 ░ 4 ░ 5 ░ 6 ░ 7 ░ 8 ░ 9 ░
                 ░░             ╔═══╗                    \s
                 A              ║   ║                    \s
                 ░░             ╠═══╬═══╗                \s
                 B              ║   ║   ║                \s
                 ░░         ╔═══╬═══╬═══╬═══╦═══╗        \s
                 C          ║   ║   ║   ║   ║   ║        \s
                 ░░         ╠═══╬═══╬═══╬═══╬═══╬═══╦═══╗\s
                 D          ║   ║   ║   ║   ║   ║   ║   ║\s
                 ░░     ╔═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╝\s
                 E      ║   ║   ║   ║   ║   ║   ║   ║    \s
                 ░░ ╔═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╝    \s
                 F  ║   ║   ║   ║   ║   ║   ║   ║        \s
                 ░░ ╚═══╩═══╬═══╬═══╬═══╬═══╬═══╣        \s
                 G          ║   ║   ║   ║   ║   ║        \s
                 ░░         ╚═══╩═══╬═══╬═══╬═══╝        \s
                 H                  ║   ║   ║            \s
                 ░░                 ╚═══╬═══╣            \s
                 I                      ║   ║            \s
                 ░░                     ╚═══╝            \s
                 """;
        this.boardgame = String2CharMatrix(boardImage); //insert the Image
        setTileBoard(this.boardgame,modelBoard.getTiles());
    };
    /***
     ░░░░ 1 ░ 2 ░ 3 ░ 4 ░ 5 ░ 6 ░ 7 ░ 8 ░ 9 ░
     ░░             ╔═══╦═══╗
     A              ║   ║   ║
     ░░             ╠═══╬═══╬═══╗
     B              ║   ║   ║   ║
     ░░         ╔═══╬═══╬═══╬═══╬═══╗
     C          ║   ║   ║   ║   ║   ║
     ░░     ╔═══╬═══╬═══╬═══╬═══╬═══╬═══╦═══╗
     D      ║   ║   ║   ║   ║   ║   ║   ║   ║
     ░░ ╔═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╣
     E  ║   ║   ║   ║   ║   ║   ║   ║   ║   ║
     ░░ ╠═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╝
     F  ║   ║   ║   ║   ║   ║   ║   ║   ║
     ░░ ╚═══╩═══╬═══╬═══╬═══╬═══╬═══╬═══╝
     G          ║   ║   ║   ║   ║   ║
     ░░         ╚═══╬═══╬═══╬═══╬═══╝
     H              ║   ║   ║   ║
     ░░             ╚═══╬═══╬═══╣
     I                  ║   ║   ║
     ░░                 ╚═══╩═══╝

     /****
     * this method
     *
     */
    private void Board4Players(Board modelBoard) {
        String boardImage = """
                ░░░░ 1 ░ 2 ░ 3 ░ 4 ░ 5 ░ 6 ░ 7 ░ 8 ░ 9 ░
                ░░             ╔═══╦═══╗                \s
                A              ║   ║   ║                \s
                ░░             ╠═══╬═══╬═══╗            \s
                B              ║   ║   ║   ║            \s
                ░░         ╔═══╬═══╬═══╬═══╬═══╗        \s
                C          ║   ║   ║   ║   ║   ║        \s
                ░░     ╔═══╬═══╬═══╬═══╬═══╬═══╬═══╦═══╗\s
                D      ║   ║   ║   ║   ║   ║   ║   ║   ║\s
                ░░ ╔═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╣\s
                E  ║   ║   ║   ║   ║   ║   ║   ║   ║   ║\s
                ░░ ╠═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╝\s
                F  ║   ║   ║   ║   ║   ║   ║   ║   ║    \s
                ░░ ╚═══╩═══╬═══╬═══╬═══╬═══╬═══╬═══╝    \s
                G          ║   ║   ║   ║   ║   ║        \s
                ░░         ╚═══╬═══╬═══╬═══╬═══╝        \s
                H              ║   ║   ║   ║            \s
                ░░             ╚═══╬═══╬═══╣            \s
                I                  ║   ║   ║            \s
                ░░                 ╚═══╩═══╝            \s
                """;
        this.boardgame = String2CharMatrix(boardImage); //insert the Image
        setTileBoard(this.boardgame,modelBoard.getTiles());
    }
    /***
     * this method is used to set the tiles on the board
     *         A:1
     *         B:2
     *         C:3
     *         D:4
     *         E:5
     *         F:6
     *         G:7
     *         H:8
     *         I:9
     * */
    private void setTileBoard(char[][] board, Tile[][] tiles) {
        for (int i= 0; i < tiles.length; i++) {
            for (int j=0; j < tiles[i].length; j++) {
                board[boardRowModel2CLI.get(i)][boardColModel2CLI.get(j)] = Tile2Char(tiles[i][j]);
            }
        }
    }

}
