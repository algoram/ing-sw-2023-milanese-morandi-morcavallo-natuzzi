package myshelfie_view.cli.printers.macro;

import myshelfie_model.Tile;
import myshelfie_model.board.Board;
import myshelfie_view.cli.printers.Color;

import java.util.HashMap;


public class PlotBoardgame {
    private String[][] boardgame;

    /**
     * Map for the row CORDINATES of the board3Player (and board4Player) in the model
     * into the CORDINATES of the board3Player (and board4Player) in the view
     */
    private final HashMap<Integer, Integer> boardRowModel2CLI = new HashMap<>() {{
        put(8, 2);  //A
        put(7, 4);  //B
        put(6, 6);  //C
        put(5, 8);  //D
        put(4, 10); //E
        put(3, 12); //F
        put(2, 14); //G
        put(1, 16); //H
        put(0, 18); //I
    }};
    /**
     * Map for the row CORDINATES of the board3Player (and board4Player) in the model
     * into the CORDINATES of the board3Player (and board4Player) in the view
     */
    private final HashMap<Integer, Integer> boardColModel2CLI = new HashMap<>() {{
        put(0, 2);
        put(1, 4);
        put(2, 6);
        put(3, 8);
        put(4,10);
        put(5,12);
        put(6,14);
        put(7,16);
        put(8,18);
    }};
    public PlotBoardgame(Board modelBoard, int numPlayers) {
        this.boardgame = boardgame;
        buildBoard(modelBoard, numPlayers);
    }
    public String[][] getBoardStringMatrix() {
        return boardgame;
    }
    /***
     * this method build the boardgame and insert all the tiles in the right position
     * @param modelBoard is the board of the model
     * @param numPlayers is the number of players: has to be 2, 3 or 4 and remain CONSTANT for the whole game
     * */
    private void buildBoard( Board modelBoard, int numPlayers) {
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
        final String[][] boardImage ={
                {"░░░░░░░░ 2 ░ 3 ░ 4 ░ 5 ░ 6 ░ 7 ░ 8 ░░░░░░"},//0
                {"░░                                       "},//1
                {"░░                                       "},//2
                {"░░             ","╔═══╦═══╗","                 "},//3
                {"","","","","",""," B             ","║","   ","║","   ","║","                 "},//4
                {"░░            \s","╠═══╬═══╬═══╗","             "},//5
                {"","","","","",""," C            \s","║","   ","║","   ","║","   ","║","             "},//6
                {"░░        \s","╔═══╬═══╬═══╬═══╬═══╦═══╗","     "},//7
                {"","","",""," D         ","║","   ","║","   ","║","   ","║","   ","║","   ","║","   ","║","     "},//8
                {"░░     ","╔═══╬═══╬═══╬═══╬═══╬═══╬═══╣","     "},//9
                {"",""," E     ","║","   ","║","   ","║","   ","║","   ","║","   ","║","   ","║","   ","║","     "},//10
                {"░░     ","╠═══╬═══╬═══╬═══╬═══╬═══╬═══╝","     "},//11
                {"",""," F     ","║","   ","║","   ","║","   ","║","   ","║","   ","║","   ","║","         "},//12
                {"░░     ","╚═══╩═══╬═══╬═══╬═══╬═══╝","         "},//13
                {"","","","","",""," G            \s","║","   ","║","   ","║","   ","║","             "},//14
                {"░░             ","╚═══╬═══╬═══╣","             "},//15
                {"","","","","","","",""," H                 ","║","   ","║","   ","║","             "},//16
                {"░░                 ","╚═══╩═══╝","             "},//17
                {"░░                                       "},//18
                {"░░                                       "}//19
        };
        this.boardgame = boardImage; //insert the Image
        colorBoard2P(this.boardgame,Color.BROWN); //Set all the color
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
        final String[][] boardImage={
                {"░░░░ 1 ░ 2 ░ 3 ░ 4 ░ 5 ░ 6 ░ 7 ░ 8 ░ 9 ░░"},//0
                {"░░             ","╔═══╗","                     "},//1
                {"","","","","",""," A             ","║","   ","║","                     "},//2
                {"░░             ","╠═══╬═══╗                 "},//3
                {"","","","","",""," B             ","║","   ","║","   ","║","                 "},//4
                {"░░         ","╔═══╬═══╬═══╬═══╦═══╗","         "},//5
                {"","","",""," C         ","║","   ","║","   ","║","   ","║","   ","║","   ","║","         "},//6
                {"░░         ","╠═══╬═══╬═══╬═══╬═══╬═══╦═══╗"," "},//7
                {"","","",""," D         ","║","   ","║","   ","║","   ","║","   ","║","   ","║","   ","║","   ","║"," "},//8
                {"░░     ","╔═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╝ "},//9
                {"",""," E     ","║","   ","║","   ","║","   ","║","   ","║","   ","║","   ","║","   ","║","     "},//10
                {"░░ ","╔═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╝     "},//11
                {" F ","║","   ","║","   ","║","   ","║","   ","║","   ","║","   ","║","   ","║","         "},//12
                {"░░ ","╚═══╩═══╬═══╬═══╬═══╬═══╬═══╣         "},//13
                {"","","",""," G         ","║","   ","║","   ","║","   ","║","   ","║","   ","║","         "},//14
                {"░░         ","╚═══╩═══╬═══╬═══╬═══╝         "},//15
                {"","","","","","","",""," H                 ","║","   ","║","   ","║","             "},//16
                {"░░                 ","╚═══╬═══╣","             "},//17
                {"","","","","","","","","",""," I                     ","║","   ","║","             "},//18
                {"░░                     ","╚═══╝","             "},//19
        };
        this.boardgame = boardImage; //insert the Image
        colorBoard3P(this.boardgame,Color.BROWN); //Set all the color
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
        final String[][] boardImage = {
                {"░░░░ 1 ░ 2 ░ 3 ░ 4 ░ 5 ░ 6 ░ 7 ░ 8 ░ 9 ░░"},//0
                {"░░             ","╔═══╦═══╗","                 "},//1
                {"","","","","",""," A             ","║","   ","║","   ","║","                 "},//2
                {"░░             ","╠═══╬═══╬═══╗","             "},//3
                {"","","","","",""," B             ","║","   ","║","   ","║","   ","║","             "},//4
                {"░░         ","╔═══╬═══╬═══╬═══╬═══╗","         "},//5
                {"","","",""," C         ","║","   ","║","   ","║","   ","║","   ","║","   ","║","         "},//6
                {"░░     ","╔═══╬═══╬═══╬═══╬═══╬═══╬═══╦═══╗"," "},//7
                {"",""," D     ","║","   ","║","   ","║","   ","║","   ","║","   ","║","   ","║","   ","║","   ","║"," "},//8
                {"░░ ","╔═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╣"," "},//9
                {" E ","║","   ","║","   ","║","   ","║","   ","║","   ","║","   ","║","   ","║","   ","║","   ","║"," "},//10
                {"░░ ","╠═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╝"," "},//11
                {" F ","║","   ","║","   ","║","   ","║","   ","║","   ","║","   ","║","   ","║","   ","║","     "},//12
                {"░░ ","╚═══╩═══╬═══╬═══╬═══╬═══╬═══╬═══╝","     "},//13
                {"","","",""," G         ","║","   ","║","   ","║","   ","║","   ","║","   ","║","         "},//14
                {"░░         ","╚═══╬═══╬═══╬═══╬═══╝","         "},//15
                {"","","","","",""," H             ","║","   ","║","   ","║","   ","║","             "},//16
                {"░░             ","╚═══╬═══╬═══╣","             "},//17
                {"","","","","","","",""," I                 ","║","   ","║","   ","║","             "},//18
                {"░░                 ","╚═══╩═══╝","             "},//19
        };
        this.boardgame = boardImage; //insert the Image
        colorBoard4P(this.boardgame,Color.BROWN); //Set all the color
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
    private void setTileBoard(String[][] board, Tile[][] tiles) {
        for (int i= 0; i < tiles.length; i++) {
            for (int j=0; j < tiles[i].length; j++) {
                if(tiles[i][j] != null) {
                    board[boardRowModel2CLI.get(i)][boardColModel2CLI.get(j)] = Color.Tile2String(tiles[i][j]);
                }
            }
        }
    }

    private String[][] colorBoard4P (String[][] board, Color code){
        String[][] coloredBoard=board;
        final HashMap<Integer,Integer[]> boardComponents4P = new HashMap<>(){{
            //Usages map i,j
            put(1,new Integer[]{1});
            put(2,new Integer[]{7,9,11});
            put(3,new Integer[]{1});
            put(4,new Integer[]{7,9,11,13});
            put(5,new Integer[]{1});
            put(6,new Integer[]{5,7,9,11,13,15});
            put(7,new Integer[]{1});
            put(8,new Integer[]{3,5,7,9,11,13,15,17,19});
            put(9,new Integer[]{1});
            put(10,new Integer[]{1,3,5,7,9,11,13,15,17,19});
            put(11,new Integer[]{1});
            put(12,new Integer[]{1,3,5,7,9,11,13,15,17});
            put(13,new Integer[]{1});
            put(14,new Integer[]{5,7,9,11,13,15});
            put(15,new Integer[]{1});
            put(16,new Integer[]{7,9,11,13});
            put(17,new Integer[]{1});
            put(18,new Integer[]{9,11,13});
            put(19,new Integer[]{1});
        }};

        for (int row = 1; row < 20; row++) {
            if(boardComponents4P.containsKey(row)) {
                for (int j : boardComponents4P.get(row)) {
                    coloredBoard[row][j] = Color.getColoredString(board[row][j], code);
                }
            }
        }
        return coloredBoard;
    }

    private String[][] colorBoard3P (String[][] board, Color code) {
        String[][] coloredBoard = board;
        final HashMap<Integer,Integer[]> boardComponents3P = new HashMap<>(){{
            //Usages map i,j
            //boardComponents.put(i,j);
            put(1,new Integer[]{1});
            put(2,new Integer[]{7,9});
            put(3,new Integer[]{1});
            put(4,new Integer[]{7,9,11});
            put(5,new Integer[]{1});
            put(6,new Integer[]{5,7,9,11,13,15});
            put(7,new Integer[]{1});
            put(8,new Integer[]{5,7,9,11,13,15,17,19});
            put(9,new Integer[]{1});
            put(10,new Integer[]{3,5,7,9,11,13,15,17});
            put(11,new Integer[]{1});
            put(12,new Integer[]{1,3,5,7,9,11,13,15});
            put(13,new Integer[]{1});
            put(14,new Integer[]{5,7,9,11,13,15});
            put(15,new Integer[]{1});
            put(16,new Integer[]{9,11,13});
            put(17,new Integer[]{1});
            put(18,new Integer[]{11,13});
            put(19,new Integer[]{1});
        }};

        for (int row = 1; row < 20; row++) {
            if (boardComponents3P.containsKey(row)) {
                for (int j : boardComponents3P.get(row)) {
                    coloredBoard[row][j] = Color.getColoredString(board[row][j], code);
                }
            }
        }
        return coloredBoard;
    };

    private String[][] colorBoard2P (String[][] board, Color code){
        String[][] coloredBoard=board;
        final HashMap<Integer,Integer[]> boardComponents2P = new HashMap<>(){{
            //Usages map i,j
            //boardComponents.put(i,j);
            put(3,new Integer[]{1});
            put(4,new Integer[]{7,9,11});
            put(5,new Integer[]{1});
            put(6,new Integer[]{7,9,11,13});
            put(7,new Integer[]{1});
            put(8,new Integer[]{5,7,9,11,13,15,17});
            put(9,new Integer[]{1});
            put(10,new Integer[]{3,5,7,9,11,13,15,17});
            put(11,new Integer[]{1});
            put(12,new Integer[]{3,5,7,9,11,13,15});
            put(13,new Integer[]{1});
            put(14,new Integer[]{7,9,11,13});
            put(15,new Integer[]{1});
            put(16,new Integer[]{9,11,13});
            put(17,new Integer[]{1});
        }};
        for (int row = 3; row < 18; row++) {
            if(boardComponents2P.containsKey(row)) {
                for (int j : boardComponents2P.get(row)) {
                    coloredBoard[row][j] = Color.getColoredString(board[row][j], code);
                }
            }
        }
        return coloredBoard;
    };
}

























