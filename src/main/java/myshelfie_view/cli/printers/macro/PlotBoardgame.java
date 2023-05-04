package myshelfie_view.cli.printers.macro;

import myshelfie_model.Tile;
import myshelfie_model.board.Board;
import java.util.HashMap;

import static myshelfie_view.cli.printers.Printer.String2CharMatrix;
import static myshelfie_view.cli.printers.Printer.Tile2Char;

public class PlotBoardgame {
    private char[][] boardgame;
    /**
     * Map for the row CORDINATES of the board3Player (and board4Player) in the model
     * into the CORDINATES of the board3Player (and board4Player) in the view
     */
    private final HashMap<Integer, Integer> boardRowModel2CLI = new HashMap<>() {{
        put(0, 2);
        put(1, 4);
        put(2, 6);
        put(3, 8);
        put(4, 10);
        put(5, 12);
        put(6, 14);
        put(7, 16);
        put(8, 18);
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

    private final HashMap<Character, Integer> board2PCLI2Model = new HashMap<>() {{
        put('1', 1);
        put('2', 2);
        put('3', 3);
        put('4', 4);
        put('5', 5);
        put('6', 6);
        put('7', 7);
        put('A', 1);
        put('B', 2);
        put('C', 3);
        put('D', 4);
        put('E', 5);
        put('F', 6);
        put('G', 7);
    }};
    private final HashMap<Character, Integer> board34PCLI2Model = new HashMap<>() {{
        put('1', 0);
        put('2', 1);
        put('3', 2);
        put('4', 3);
        put('5', 4);
        put('6', 5);
        put('7', 6);
        put('8', 7);
        put('A', 0);
        put('B', 1);
        put('C', 2);
        put('D', 3);
        put('E', 4);
        put('F', 5);
        put('G', 6);
        put('H', 7);
        put('I', 8);
    }};
    public PlotBoardgame(char[][] chars) {
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
     * ░░░░░░░░ 1 ░ 2 ░ 3 ░ 4 ░ 5 ░ 6 ░ 7 ░ 8 ░░░
     * ░░
     * ░░
     * ░░             ╔═══╦═══╗
     *  A             ║   ║   ║
     * ░░         ╔═══╬═══╬═══╬═══╗
     *  B         ║   ║   ║   ║   ║
     * ░░         ╠═══╬═══╬═══╬═══╬═══╦═══╗
     *  C         ║   ║   ║   ║   ║   ║   ║
     * ░░     ╔═══╬═══╬═══╬═══╬═══╬═══╬═══╣
     *  D     ║   ║   ║   ║   ║   ║   ║   ║
     * ░░     ╠═══╬═══╬═══╬═══╬═══╬═══╬═══╝
     *  E     ║   ║   ║   ║   ║   ║   ║
     * ░░     ╚═══╩═══╬═══╬═══╬═══╬═══╝
     *  F             ║   ║   ║   ║
     * ░░             ╚═══╬═══╬═══╣
     *  G                 ║   ║   ║
     * ░░                 ╚═══╩═══╝
     * ░░
     * ░░
     *
     * */
    private void Board2Players(Board modelBoard) {
        String boardImage = """
                ░░░░░░░░ 1 ░ 2 ░ 3 ░ 4 ░ 5 ░ 6 ░ 7 ░░░░░░░
                ░░                                       \s
                ░░                                       \s
                ░░             ╔═══╦═══╗                 \s
                 A             ║   ║   ║                 \s
                ░░         ╔═══╬═══╬═══╬═══╗             \s
                 B         ║   ║   ║   ║   ║             \s
                ░░         ╠═══╬═══╬═══╬═══╬═══╦═══╗     \s
                 C         ║   ║   ║   ║   ║   ║   ║     \s
                ░░     ╔═══╬═══╬═══╬═══╬═══╬═══╬═══╣     \s
                 D     ║   ║   ║   ║   ║   ║   ║   ║     \s
                ░░     ╠═══╬═══╬═══╬═══╬═══╬═══╬═══╝     \s
                 E     ║   ║   ║   ║   ║   ║   ║         \s
                ░░     ╚═══╩═══╬═══╬═══╬═══╬═══╝         \s
                 F             ║   ║   ║   ║             \s
                ░░             ╚═══╬═══╬═══╣             \s
                 G                 ║   ║   ║             \s
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
