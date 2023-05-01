package myshelfie_view.cli.printers;

import myshelfie_model.Tile;
import myshelfie_model.board.Board;
import myshelfie_model.player.Bookshelf;
import myshelfie_model.player.Player;

import java.io.PrintStream;
import java.util.List;

public class BasicPrint {
    private final PrintStream out;

    private String[][] boardImage;

    public BasicPrint( PrintStream out) {
        this.out = out;
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

    public void Board(Board board, int numPlayers){
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
