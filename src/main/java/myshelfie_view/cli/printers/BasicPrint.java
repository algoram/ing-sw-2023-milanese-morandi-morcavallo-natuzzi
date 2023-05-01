package myshelfie_view.cli.printers;

import myshelfie_model.GameState;
import myshelfie_model.Tile;
import myshelfie_model.board.Board;
import myshelfie_model.player.Bookshelf;
import myshelfie_model.player.Player;

import java.io.PrintStream;
import java.util.List;

public class BasicPrint {
    private final PrintStream out;

    private char[][] AllSetup ;

    public BasicPrint( PrintStream out) {
        this.out = out;
        AllSetup = new char[100][100];
    }
    public void Logo(){
        out.println("Welcome to MyShelfie!");
        out.print("""
                
                
                
                
                
                """
        );

    }
    public void Commands(){

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
     * ░░░░    Your Bookshelf:    ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
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
    public void DisplayAll(GameState gameState){


    }

    private void Board(Board board, int numPlayers){
        switch (numPlayers) {
            case 2 -> Board2Players();
            case 3 -> Board3Players();
            default -> Board4Players();
        }
    }



    /***
     Version 4 players
     ░░░░ 1 ░ 2 ░ 3 ░ 4 ░ 5 ░ 6 ░ 7 ░ 8 ░ 9 ░░░░░░░ //r0
     ░░             ╔═══╦═══╗                 ░░░░░ //r1
     A              ║   ║   ║                 ░░░░░ //r2
     ░░             ╠═══╬═══╬═══╗             ░░░░░ //r3
     B              ║   ║   ║   ║             ░░░░░ //r4
     ░░         ╔═══╬═══╬═══╬═══╬═══╗         ░░░░░ //r5
     C          ║   ║   ║   ║   ║   ║         ░░░░░ //r6
     ░░     ╔═══╬═══╬═══╬═══╬═══╬═══╬═══╦═══╗ ░░░░░ //r7
     D      ║   ║   ║   ║   ║   ║   ║   ║   ║ ░░░░░ //r8
     ░░ ╔═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╣ ░░░░░ //r9
     E  ║   ║   ║   ║   ║   ║   ║   ║   ║   ║ ░░░░░ //r10
     ░░ ╠═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╝ ░░░░░ //r11
     F  ║   ║   ║ █ ║   ║   ║   ║   ║   ║     ░░░░░ //r12
     ░░ ╚═══╩═══╬═══╬═══╬═══╬═══╬═══╬═══╝     ░░░░░ //r13
     G          ║ █ ║   ║   ║   ║   ║         ░░░░░ //r14
     ░░         ╚═══╬═══╬═══╬═══╬═══╝         ░░░░░ //r15
     H              ║   ║   ║   ║             ░░░░░ //r16
     ░░             ╚═══╬═══╬═══╣             ░░░░░ //r17
     I                  ║   ║   ║             ░░░░░ //r18
     ░░                 ╚═══╩═══╝             ░░░░░ //r19
     ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░

     CAT:		¥
     BOOKS:		#
     FRAMES:	¶
     TROPHIES: 	©
     PLANTS:	§

    */

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
    public void setTilesBoard(Tile tile, int letter, int col , int num_players){
        
        switch (num_players)
        {
            case 2:
                setTiles2P(tile, letter, col);
                break;  
            case 3:
                setTiles3P(tile, letter, col);
                break;
            case 4:
                setTiles4P(tile, letter, col);

                break;
        }
    }
    
    //todo implement setTiles2P, setTiles3P, setTiles4P
    private void setTiles4P(char tile, int letter, int col){
        if (letter==1 && col == 4) boardImage[0][1] = String.valueOf(tile);  //A4
    }


    private void Board4Players(){
        //tile pattern " ©  ║"

        //todo implement board_image as object in order to print it with other objects (bookshelfs, cards, etc) horizontally
        boardImage = new String[][]{
                {"░░░░ 1 ░ 2 ░ 3 ░ 4 ░ 5 ░ 6 ░ 7 ░ 8 ░ 9 ░░░░░░░"}, //r0
                {"░░             ╔═══╦═══╗                 ░░░░░"}, //r1
                {"A              ║ ", " ", " ║ ", " ", " ║                 ░░░░░"}, //r2
                {"░░             ╠═══╬═══╬═══╗             ░░░░░"}
        };




    };
    private void Board3Players(){

    }
    private void Board2Players(){

    }







    public void Bookshelfs(List<Player> players){
        for (Player player : players) {
            Bookshelf(player.getBookshelf());
        }
    }

    /**
     *	 Versione Basic B&W-> tiles char
     *
     * 	 ╔═══╦═══╦═══╦═══╦═══╗
     * 	 ║   ║	 ║   ║   ║   ║
     * 	 ╠═══╬═══╬═══╬═══╬═══╣
     * 	 ║ § ║	 ║   ║   ║   ║
     * 	 ╠═══╬═══╬═══╬═══╬═══╣
     * 	 ║ © ║	 ║   ║   ║   ║
     * 	 ╠═══╬═══╬═══╬═══╬═══╣
     * 	 ║ ¥ ║	 ║   ║   ║   ║
     * 	 ╠═══╬═══╬═══╬═══╬═══╣
     * 	 ║ # ║	 ║   ║   ║   ║
     * 	 ╠═══╬═══╬═══╬═══╬═══╣
     * 	 ║ ¶ ║	 ║   ║   ║   ║
     * 	 ╚═══╩═══╩═══╩═══╩═══╝
     *
     * 	 CAT:		¥
     * 	 BOOKS:		#
     * 	 FRAMES:	¶
     * 	 TROPHIES: 	©
     * 	 PLANTS:	§
     * **/
    private void Bookshelf(Bookshelf bookshelf){
        //todo implement bookshelfs as object in order to print it with other objects (board, cards, etc) horizontally

    }





}
