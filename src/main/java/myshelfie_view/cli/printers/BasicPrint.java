package myshelfie_view.cli.printers;

import myshelfie_controller.EventDispatcher;
import myshelfie_model.GameState;
import myshelfie_model.Tile;
import myshelfie_model.board.Board;
import myshelfie_model.player.Bookshelf;
import myshelfie_model.player.Player;

import java.io.PrintStream;
import java.lang.foreign.Addressable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BasicPrint {
    private final PrintStream out;

    private char[][] allSetup;

    public BasicPrint(PrintStream out) {
        this.out = out;
        allSetup = new char[40][100];
    }

    public void Logo() {
        out.println("Welcome to MyShelfie!");
        out.print("""
                                
                                
                                
                                
                                
                """
        );

    }

    public void Commands() {

        out.println("Digit '/help' to see the list of available commands.");
        out.println("Digit '/exit' to exit the game.");

    }


    /***
     * Coordinate system of the Game displayed on the CLI
     *           1         2         3         4         5         6         7         8         9
     * 0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789
     * ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * ░░X                                       Y░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * ░░                                         ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * ░░                                         ░░░░░ ┌────────┐  ┌────────┐ ░░░░░ Your POINTS ░░░░░░░░░░
     * ░░                                         ░░░░░ │ ┬   Y  │  │ ┬┬  Z  │ ░░░░░     X       ░░░░░░░░░░
     * ░░                                         ░░░░░ │ ┴  ¯¯¯ │  │ ┴┴ ¯¯¯ │ ░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * ░░                                         ░░░░░ └────────┘  └────────┘ ░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * ░░                                         ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * ░░                                         ░░░░░ X                   Y ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * ░░                                         ░░░░░                       ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * ░░                                         ░░░░░                       ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * ░░               (Board)                   ░░░░░                       ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * ░░                                         ░░░░░   (Personal)          ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * ░░                                         ░░░░░                       ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * ░░                                         ░░░░░                       ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * ░░                                         ░░░░░                       ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * ░░                                         ░░░░░                       ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * ░░                                         ░░░░░                       ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * ░░                                         ░░░░░                       ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * ░░                                         ░░░░░                       ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * ░░Z                                       W░░░░░ Z                   W ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ Personal Goal ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * ░░░░U   Your Bookshelf:   V░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * ░░░░   1 ░ 2 ░ 3 ░ 4 ░ 5   ░░░░░ player_1          ░░░ player_2          ░░░ player_3          ░░░░░
     * ░░░░ X                   Y ░░░ X                   Y X                   Y X                   Y ░░░
     * ░░░░                       ░░░                                                                   ░░░
     * ░░░░                       ░░░                                                                   ░░░
     * ░░░░                       ░░░                                                                   ░░░
     * ░░░░                       ░░░     (P1)                 (P2)                    (P3)             ░░░
     * ░░░░     (Bookshelf)       ░░░                                                                   ░░░
     * ░░░░                       ░░░                                                                   ░░░
     * ░░░░                       ░░░                                                                   ░░░
     * ░░░░                       ░░░                                                                   ░░░
     * ░░░░                       ░░░                                                                   ░░░
     * ░░░░                       ░░░                                                                   ░░░
     * ░░░░                       ░░░                                                                   ░░░
     * ░░░░ Z                   W ░░░ Z                   W Z                   W Z                   W ░░░
     * ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * Board:
     * X = 1,2
     * Y = 1,42
     * Z = 20,2
     * W = 20,42
     * Personal:
     * X = 8,49
     * Y = 8,69
     * Z = 20,49
     * W = 20,69
     * Bookshelf:
     * X = 25,5
     * Y = 25,25
     * Z = 37,5
     * W = 37,25
     * P1:
     * X = 25,31
     * Y = 25,51
     * Z = 37,31
     * W = 37,51
     *
     *
     * P2:
     * X = 25,53
     * Y = 25,73
     * Z = 37,53
     * W = 37,73
     *
     * P3:
     * X = 25,75
     * Y = 25,95
     * Z = 37,75
     * W = 37,95
     *
     *
     *
     *
     * */
    public void DisplayAll(GameState gameState) {
       allSetup= BuildSetup(gameState);
       out.println(allSetup);
    }

    private char[][] BuildSetup(GameState gameState){
        Player thisPlayer = getThisPlayer(gameState.getPlayers());
        List<Player> otherPlayers = otherPlayer(gameState.getPlayers(), thisPlayer);

        char[][] background = new char[40][100];
        char[][] board = new char[20][20];;
        char[][] commonGoals = new char[4][24];
        char[][] yourPoints = new char[2][13];
        char[][] bookshelf = new char[13][21];
        char[][] personalGoal = new char[13][21];
        List<char[][]> otherBookshelf = new ArrayList<>();

        buildBackground(background);
        buildBoard(board, gameState.getBoard(), gameState.getGameNumber());
        buildCommonGoals(commonGoals, gameState.getCommonGoals());
        buildYourPoints(yourPoints, thisPlayer.getPoints());
        buildPersonalGoal(personalGoal,thisPlayer.getPersonalGoal());
        buildBookshelf(bookshelf,thisPlayer.getBookshelf());


        for (int i = 0; i < otherPlayers.size(); i++) {
            otherBookshelf.add(buildBookshelf(otherPlayers.get(i).getBookshelf()));
        }
    }


    private void buildBackground(char[][] background) {
        for (int i = 0; i < background.length; i++) {
            for (int j = 0; j < background[i].length; j++) {
                background[i][j] = '░';
            }
        }
    }

    private void buildBoard(char[][] board,Board modelBoard, int numPlayers) {

        switch (numPlayers) {
            case 2 -> Board2Players(board);

            case 3 -> Board3Players(board);

            case 4 -> Board4Players(board);

        }
    }




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




         */

    private void Board4Players(char[][] board) {
    /*
    //todo implement board_image as object in order to print it with other objects (bookshelfs, cards, etc) horizontally
    boardImage = new String[][]{
            {"░░░░ 1 ░ 2 ░ 3 ░ 4 ░ 5 ░ 6 ░ 7 ░ 8 ░ 9 ░░"}, //r0
            {"░░             ╔═══╦═══╗                 "}, //r1
            {"A              ║ ", " ", " ║ ", " ", " ║     "}, //r2
            {"░░             ╠═══╬═══╬═══╗             "},
            {"B              ║ ", " ", " ║ ", " ", " ║ ", " ", " ║             "},
            {"░░         ╔═══╬═══╬═══╬═══╬═══╗         "},
            {"C          ║ ", " ", " ║ ", " ", " ║ ", " ", " ║ ", " ", " ║ ", " ", " ║         "},
            {"░░     ╔═══╬═══╬═══╬═══╬═══╬═══╬═══╦═══╗ "},
            {"D      ║ ", " ", " ║ ", " ", " ║ ", " ", " ║ ", " ", " ║ ", " ", " ║ ", " ", " ║ ", " ", " ║ ", " ", " ║ "},
            {"░░ ╔═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╣ "},
            {"E  ║ ", " ", "  ║ ", " ", " ║ ", " ", " ║ ", " ", " ║ ", " ", " ║ ", " ", " ║ ", " ", " ║ ", " ", " ║   ║ "},
            {"░░ ╠═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╝ "},
            {"F  ║ ", " ", " ║ ", " ", " ║ ", " ", " ║ ", " ", " ║ ", " ", " ║ ", " ", " ║ ", " ", " ║ ", " ", " ║     "},
            {"░░ ╚═══╩═══╬═══╬═══╬═══╬═══╬═══╬═══╝     "},
            {"G          ║ ", " ", " ║ ", " ", " ║ ", " ", " ║ ", " ", " ║ ", " ", " ║         "},
            {"░░         ╚═══╬═══╬═══╬═══╬═══╝         "},
            {"H              ║ ", " ", " ║ ", " ", " ║ ", " ", " ║             "},
            {"░░             ╚═══╬═══╬═══╣             "},
            {"I                  ║ ", " ", " ║ ", " ", " ║             "},
            {"░░                 ╚═══╩═══╝             "},
            {"░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░"}
    }
     */
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
        board = String2CharMatrix(boardImage);
    }


        ;

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

    private char[][] Board3Players(char[][] board) {

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
        return String2CharMatrix(boardImage);
    };


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
    private char[][] Board2Players(char[][] board) {

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
        return String2CharMatrix(boardImage);
    };

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
    private void buildBookshelf(char [][] bookshelf, Bookshelf modelBookshelf) {
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
        bookshelf = String2CharMatrix(bookshelfImage);
    }

    //todo implement setTiles2P, setTiles3P, setTiles4P

    /***
     *
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
    public void setTileBoard(char[][] board, char tile, int letter, int col, int num_players) {
        switch (num_players) {
            case 2 -> setTile2P(board, tile, letter, col);
            case 3 -> setTile3P(board, tile, letter, col);
            case 4 -> setTile4P(board, tile, letter, col);
        }
    }

    private void setTile2P(char[][] board, Tile[][] tiles, int letter, int col) {
        //todo
    }
    private void setTile3P(char[][] board, char tile, int letter, int col) {

    }
    private void setTile4P(char[][] board, char tile, int letter, int col) {

    }

    /********************************* Util Functions **********+***************************/
    private char[][] String2CharMatrix(String multilineString ) {
        String[] rows = multilineString.split("\n");
        char[][] charMatrix = new char[rows.length][];
        for (int i = 0; i < rows.length; i++) {
            charMatrix[i] = rows[i].toCharArray();
        }
        return charMatrix;
    }

    private char Tile2Char(Tile tile) {
        char tileChar = ' ';
        switch (tile.getType())
        {
            case CATS -> tileChar = '¥';
            case BOOKS -> tileChar = '#';
            case FRAMES -> tileChar = '¶';
            case TROPHIES -> tileChar = '©';
            case PLANTS -> tileChar = '§';
        }
        return tileChar;
    }

    /***
     *        ╔═══╦═══╦═══╦═══╦═══╗
     *      F ║   ║   ║   ║   ║   ║
     *        ╠═══╬═══╬═══╬═══╬═══╣
     *      E ║ § ║   ║   ║   ║   ║
     *        ╠═══╬═══╬═══╬═══╬═══╣
     *      D ║ © ║   ║   ║   ║   ║
     *        ╠═══╬═══╬═══╬═══╬═══╣
     *      C ║ ¥ ║   ║   ║   ║   ║
     *        ╠═══╬═══╬═══╬═══╬═══╣
     *      B ║ # ║   ║   ║   ║   ║
     *        ╠═══╬═══╬═══╬═══╬═══╣
     *      A ║ ¶ ║   ║   ║   ║   ║
     *        ╚═══╩═══╩═══╩═══╩═══╝
     *
     * */
    private int[] bookshelfMap(int row, char col) {
        int[] bookshelf = new int[2];
        HashMap<Character,Integer> coordinateToPositionMap = new HashMap<Character, Integer>();
        char letter = (char) (row + 65); //Convert the row to a letter

        // Add mappings for the colums
        coordinateToPositionMap.put('0',3);
        coordinateToPositionMap.put('1',6);
        coordinateToPositionMap.put('2',10);
        coordinateToPositionMap.put('3',14);
        coordinateToPositionMap.put('4',18);

        // Add mappings for the rows
        coordinateToPositionMap.put('F',2);
        coordinateToPositionMap.put('E',4);
        coordinateToPositionMap.put('D',6);
        coordinateToPositionMap.put('C',8);
        coordinateToPositionMap.put('B',10);
        coordinateToPositionMap.put('A',12);


        bookshelf[0] = coordinateToPositionMap.get(col);
        bookshelf[1] = coordinateToPositionMap.get(letter);
        return bookshelf;
    }
    /***
     * This function is used to map the coordinates to the position on the board for the 4 and 3 players version
     * */
    private int[] boardMap43(char letter, char col) {
        HashMap<Character, Integer> coordinateToPositionMap =new HashMap<Character, Integer>();
        // Add mappings for the colums
        coordinateToPositionMap.put('1', 5);
        coordinateToPositionMap.put('2', 9);
        coordinateToPositionMap.put('3', 13);
        coordinateToPositionMap.put('4', 17);
        coordinateToPositionMap.put('5',21);
        coordinateToPositionMap.put('6',25);
        coordinateToPositionMap.put('7',29);
        coordinateToPositionMap.put('8',33);
        coordinateToPositionMap.put('9',37);

        // Add mappings for the rows
        coordinateToPositionMap.put('A',2);
        coordinateToPositionMap.put('B', 4);
        coordinateToPositionMap.put('C', 6);
        coordinateToPositionMap.put('D', 8);
        coordinateToPositionMap.put('E', 10);
        coordinateToPositionMap.put('F', 12);
        coordinateToPositionMap.put('G', 14);
        coordinateToPositionMap.put('H', 16);
        coordinateToPositionMap.put('I', 18);

        int[] coordinate = new int[2];
        coordinate[0] = coordinateToPositionMap.get(letter);
        coordinate[1] = coordinateToPositionMap.get(col);
        return coordinate;
    }

    /***
     * This function is used to map the coordinates to the position on the board for the 2 players version
     * */
    private int[] boardMap2(char letter, char col) {
        HashMap<Character, Integer> coordinateToPositionMap =new HashMap<Character, Integer>();
        // Add mappings for the colums
        coordinateToPositionMap.put('1', 9);
        coordinateToPositionMap.put('2', 13);
        coordinateToPositionMap.put('3', 17);
        coordinateToPositionMap.put('4',21);
        coordinateToPositionMap.put('5',25);
        coordinateToPositionMap.put('6',29);
        coordinateToPositionMap.put('7',33);

        // Add mappings for the rows
        coordinateToPositionMap.put('A', 4);
        coordinateToPositionMap.put('B', 6);
        coordinateToPositionMap.put('C', 8);
        coordinateToPositionMap.put('D', 10);
        coordinateToPositionMap.put('E', 12);
        coordinateToPositionMap.put('F', 14);
        coordinateToPositionMap.put('G', 16);

        int[] coordinate = new int[2];
        coordinate[0] = coordinateToPositionMap.get(letter);
        coordinate[1] = coordinateToPositionMap.get(col);
        return coordinate;
    }

    private List<Player> otherPlayer(List<Player> players, Player thisPlayer) {
        List<Player> otherPlayers = new ArrayList<>();
        for (Player player : players) {
            if (!player.equals(thisPlayer)){
                otherPlayers.add(player);
            }
        }
        return otherPlayers;
    }
    private Player getThisPlayer(List<Player> players){
        for(Player player : players){
            if(player.getUsername().equals(EventDispatcher.getInstance().getUsername())){
                return player;
            }
        }
        return null;
    }
}

