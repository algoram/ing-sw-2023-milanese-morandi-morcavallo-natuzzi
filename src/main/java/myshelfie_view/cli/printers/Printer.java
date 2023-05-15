package myshelfie_view.cli.printers;

import myshelfie_controller.Settings;
import myshelfie_model.GameState;
import myshelfie_model.goal.common_goal.CommonGoal;
import myshelfie_model.player.Player;
import myshelfie_view.cli.printers.macro.PlotBoardgame;
import myshelfie_view.cli.printers.macro.PlotBookshelf;
import myshelfie_view.cli.printers.macro.PlotCommonGoals;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static myshelfie_view.cli.printers.Color.colorChar;


public class Printer {
    private final PrintStream out = System.out;










    /***
     * ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * ░░X                                       Y░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * ░░                                         ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * ░░                                         ░░░░░X┌────────┐  ┌────────┐Y░░░░░XYour PointsY░░░░░░░░░░
     * ░░                                         ░░░░░ │ ┬   A  │  │ ┬┬  B  │ ░░░░░Z    C      W░░░░░░░░░░
     * ░░                                         ░░░░░ │ ┴  ¯¯¯ │  │ ┴┴ ¯¯¯ │ ░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * ░░                                         ░░░░░Z└────────┘  └────────┘W░░░░░░░░░░░░░░░░░░░░░░░░░░░░
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
     * ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ Personal Goal  ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
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
     * */
    private String[][] allSetup = {
            {"░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░"},//0
            {"░░","X                                       Y","░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░"},//1
            {"░░","                                         ","░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░"},//2
            {"░░","                                         ","░░░░░"," ┌────────┐  ┌────────┐ ","░░░░░"," Your POINTS ","░░░░░░░░░░"},//3
            {"░░","                                         ","░░░░░"," │ ┬  "," 1 "," │  │ ┬┬ "," 2 "," │ ","░░░░░"," ","  P3  ","      ","░░░░░░░░░░"},//4
            {"░░","                                         ","░░░░░"," │ ┴  ¯¯¯ │  │ ┴┴ ¯¯¯ │ ","░░░░░░░░░░░░░░░░░░░░░░░░░░░░"},//5
            {"░░","                                         ","░░░░░"," └────────┘  └────────┘ ","░░░░░░░░░░░░░░░░░░░░░░░░░░░░"},//6
            {"░░","                                         ","░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░"},//7
            {"░░","                                         ","░░░░░ ","X                   Y"," ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░"},//8
            {"░░","                                         ","░░░░░ ","                     "," ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░"},//9
            {"░░","                                         ","░░░░░ ","                     "," ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░"},//10
            {"░░","               (Board)                   ","░░░░░ ","                     "," ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░"},//11
            {"░░","                                         ","░░░░░ ","  (Personal)         "," ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░"},//12
            {"░░","                                         ","░░░░░ ","                     "," ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░"},//13
            {"░░","                                         ","░░░░░ ","                     "," ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░"},//14
            {"░░","                                         ","░░░░░ ","                     "," ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░"},//15
            {"░░","                                         ","░░░░░ ","                     "," ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░"},//16
            {"░░","                                         ","░░░░░ ","                     "," ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░"},//17
            {"░░","                                         ","░░░░░ ","                     "," ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░"},//18
            {"░░","                                         ","░░░░░ ","                     "," ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░"},//19
            {"░░","Z                                       W","░░░░░ ","Z                   W"," ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░"},//20
            {"░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░"," Personal Goal ","░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░"},//21
            {"░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░"},//22
            {"░░░░","    Your Bookshelf:    ","░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░"},//23
            {"░░░░","   1 | 2 | 3 | 4 | 5   ","░░░░░","░░░░░░░░░░░░░░░░░░","░░░░","░░░░░░░░░░░░░░░░░░","░░░░","░░░░░░░░░░░░░░░░░░","░░░░░░"},//24
            {"░░░░ ","X                   Y"," ░░░ ","░░░░░░░░░░░░░░░░░░░░░"," ","░░░░░░░░░░░░░░░░░░░░░","░","░░░░░░░░░░░░░░░░░░░░░","░░░░"},//25
            {"░░░░ ","                     "," ░░░ ","░░░░░░░░░░░░░░░░░░░░░"," ","░░░░░░░░░░░░░░░░░░░░░","░","░░░░░░░░░░░░░░░░░░░░░","░░░░"},//26
            {"░░░░ ","                     "," ░░░ ","░░░░░░░░░░░░░░░░░░░░░"," ","░░░░░░░░░░░░░░░░░░░░░","░","░░░░░░░░░░░░░░░░░░░░░","░░░░"},//27
            {"░░░░ ","                     "," ░░░ ","░░░░░░░░░░░░░░░░░░░░░"," ","░░░░░░░░░░░░░░░░░░░░░","░","░░░░░░░░░░░░░░░░░░░░░","░░░░"},//28
            {"░░░░ ","                     "," ░░░ ","░░░░░░░░░░░░░░░░░░░░░"," ","░░░░░░░░░░░░░░░░░░░░░","░","░░░░░░░░░░░░░░░░░░░░░","░░░░"},//29
            {"░░░░ ","    (Bookshelf)      "," ░░░ ","░░░░░░░░░░░░░░░░░░░░░"," ","░░░░░░░░░░░░░░░░░░░░░","░","░░░░░░░░░░░░░░░░░░░░░","░░░░"},//30
            {"░░░░ ","                     "," ░░░ ","░░░░░░░░░░░░░░░░░░░░░"," ","░░░░░░░░░░░░░░░░░░░░░","░","░░░░░░░░░░░░░░░░░░░░░","░░░░"},//31
            {"░░░░ ","                     "," ░░░ ","░░░░░░░░░░░░░░░░░░░░░"," ","░░░░░░░░░░░░░░░░░░░░░","░","░░░░░░░░░░░░░░░░░░░░░","░░░░"},//32
            {"░░░░ ","                     "," ░░░ ","░░░░░░░░░░░░░░░░░░░░░"," ","░░░░░░░░░░░░░░░░░░░░░","░","░░░░░░░░░░░░░░░░░░░░░","░░░░"},//33
            {"░░░░ ","                     "," ░░░ ","░░░░░░░░░░░░░░░░░░░░░"," ","░░░░░░░░░░░░░░░░░░░░░","░","░░░░░░░░░░░░░░░░░░░░░","░░░░"},//34
            {"░░░░ ","                     "," ░░░ ","░░░░░░░░░░░░░░░░░░░░░"," ","░░░░░░░░░░░░░░░░░░░░░","░","░░░░░░░░░░░░░░░░░░░░░","░░░░"},//35
            {"░░░░ ","                     "," ░░░ ","░░░░░░░░░░░░░░░░░░░░░"," ","░░░░░░░░░░░░░░░░░░░░░","░","░░░░░░░░░░░░░░░░░░░░░","░░░░"},//36
            {"░░░░ ","Z                   W"," ░░░ ","░░░░░░░░░░░░░░░░░░░░░"," ","░░░░░░░░░░░░░░░░░░░░░","░","░░░░░░░░░░░░░░░░░░░░░","░░░░"},//37
            {"░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░"},//38
    };

    private static Printer instance = null;




    private Printer() {}

    public static Printer getInstance(){
        return Objects.requireNonNullElseGet(instance, Printer::new);
    }

    /******************************* Game Functions ********************************/
    public void Logo() {
        out.println("Welcome to MyShelfie!");
        out.print("""  
                        ███╗░░░███╗██╗░░░██╗  ░██████╗██╗░░██╗███████╗██╗░░░░░███████╗██╗███████╗
                        ████╗░████║╚██╗░██╔╝  ██╔════╝██║░░██║██╔════╝██║░░░░░██╔════╝██║██╔════╝
                        ██╔████╔██║░╚████╔╝░  ╚█████╗░███████║█████╗░░██║░░░░░█████╗░░██║█████╗░░
                        ██║╚██╔╝██║░░╚██╔╝░░  ░╚═══██╗██╔══██║██╔══╝░░██║░░░░░██╔══╝░░██║██╔══╝░░
                        ██║░╚═╝░██║░░░██║░░░  ██████╔╝██║░░██║███████╗███████╗██║░░░░░██║███████╗
                        ╚═╝░░░░░╚═╝░░░╚═╝░░░  ╚═════╝░╚═╝░░╚═╝╚══════╝╚══════╝╚═╝░░░░░╚═╝╚══════╝               
                """
        );
    }

    /***
     * Coordinate system of the Game displayed on the CLI
     *           1         2         3         4         5         6         7         8         9
     * 0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789
     * ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * ░░X                                       Y░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * ░░                                         ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * ░░                                         ░░░░░X┌────────┐  ┌────────┐Y░░░░░XYour PointsY░░░░░░░░░░
     * ░░                                         ░░░░░ │ ┬   A  │  │ ┬┬  B  │ ░░░░░Z    C      W░░░░░░░░░░
     * ░░                                         ░░░░░ │ ┴  ¯¯¯ │  │ ┴┴ ¯¯¯ │ ░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * ░░                                         ░░░░░Z└────────┘  └────────┘W░░░░░░░░░░░░░░░░░░░░░░░░░░░░
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
     * ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ Personal Goal  ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
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
     * Board: X = 1,2; Y = 1,42; Z = 20,2; W = 20,42
     * CommonGoal: X = 3,48 Y = 3,61; Z = 6,48; W = 6,61
     * Personal: X = 8,49; Y = 8,69; Z = 20,49; W = 20,69
     * Your Points: X = 3,77; Y = 3,89; Z = 4,77; W = 4,89
     * Bookshelf: X = 25,5 ; Y = 25,25; Z = 37,5; W = 37,25
     * P1: X = 25,31; Y = 25,51; Z = 37,31; W = 37,51
     * P2: X = 25,53; Y = 25,73; Z = 37,53; W = 37,73
     * P3: X = 25,75; Y = 25,95; Z = 37,75; W = 37,95
     *
     * Text:
     *  Personal Goal: U = 2,2; V = 2,22
     *  Your Bookshelf: U = 21,47 ; V = 21,63
     *  player_1: U = 24,31; V = 24,50
     *  player_2: U = 24,53; V = 24,72
     *  player_3: U = 24,75; V = 24,94
     *
     *
     * */
    public void DisplayAllSetup(GameState gameState) {
       BuildSetup(gameState); //build the matrix setup from gameState

        for (int i = 0; i < allSetup.length; i++) {
            for (int j = 0; j < allSetup[i].length; j++) {
                out.print(allSetup[i][j]);
            }
            System.out.println();
        }
    }
    //todo: add the commongoal to allSetup
    public void DisplayCommonGoal(CommonGoal modelCommonGoal){
        PlotCommonGoals commonGoal = new PlotCommonGoals(modelCommonGoal);
        char [][] commonGoalMatrix = commonGoal.getCommongoalCharMatrix();
        for (int i = 0; i < commonGoalMatrix.length; i++) {
            out.println(commonGoalMatrix[i]);
        }
    }

    /************************ Build Functions **************************************************+**********/
    private void BuildSetup(GameState gameState){
        Player thisPlayer = getThisPlayer(gameState.getPlayers());
        List<Player> otherPlayers = otherPlayer(gameState.getPlayers(), thisPlayer);


        String[][] background = allSetup;
        PlotBoardgame boardgame = new PlotBoardgame(gameState.getBoard(), gameState.getPlayers().size());
        PlotBookshelf personalGoal = new PlotBookshelf(thisPlayer.getPersonalGoal().map_PGoalToBookshelf());
        PlotBookshelf bookshelf = new PlotBookshelf(thisPlayer.getBookshelf());
        List<PlotBookshelf> otherBookshelf = new ArrayList<>();


        //Other player username
        buildOtherUsername(allSetup, otherPlayers);

        //Board

        setOnSetup(boardgame.getBoardStringMatrix(), 1, 1);

        //CommonGoal
        updateCommongoalStacks(gameState.getTopCommonGoal()[0].getPoints(), gameState.getTopCommonGoal()[1].getPoints());

        //Your Points
        updateYourPoints(getPoints(thisPlayer,gameState));
        //PersonalGoal
        setOnSetup(personalGoal.getBookshelfStringMatrix(), 8, 3);
        //your Bookshelf
        setOnSetup(bookshelf.getBookshelfStringMatrix(),25,1);

        //otherBookshelf
        for (int i = 0; i < otherPlayers.size(); i++) {
            otherBookshelf.add(new PlotBookshelf(otherPlayers.get(i).getBookshelf()));
            switch (i){
                case 0 -> setOnSetup(otherBookshelf.get(i).getBookshelfStringMatrix(), 25, 3);
                case 1 -> setOnSetup(otherBookshelf.get(i).getBookshelfStringMatrix(), 25, 5);
                case 2 -> setOnSetup(otherBookshelf.get(i).getBookshelfStringMatrix(), 25, 7);
            }
        }
        //build the background
        buildBackground(background, gameState.getPlayers().size());

    }
    //todo add function to allineate the last column of background
    private void buildBackground(String[][] background,int numPlayers) {
        //build the background
        switch (numPlayers){
            case 3->{
                for (int i = 25; i <38 ; i++) {
                    allSetup[i][4] = " ";
                }
            }
            case 4->{
                for (int i = 25; i <38 ; i++) {
                    allSetup[i][4] = " ";
                    allSetup[i][6] = " ";
                }
            }
        }
        for (int i=0; i<allSetup.length; i++){
            for (int j=0; j<allSetup[i].length; j++){
                allSetup[i][j] = colorChar(allSetup[i][j], List.of('░'), Color.PARQUET);
            }
        }

    }
    public static String changeBack(String string, char special,char nuovo, Color color){

        StringBuilder output = new StringBuilder(); // stringa di output

        // ciclo sulla stringa di input
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);

            if (special==(c)) {
                // se il carattere è quello da colorare, aggiungi il codice colore
                output.append(color.getCode()); // aggiungi il codice colore
                while (i < string.length()-1 && special==string.charAt(i+1)){
                    output.append(nuovo); // aggiungi il carattere
                    i++;
                };
                output.append(string.charAt(nuovo)).append(Color.RESET.getCode()) ;
            } else {
                // altrimenti aggiungi il carattere normale
                output.append(c);
            }
        }
        return output.toString();

    };
    /**
     * Build the string for the other players username
     * @param background the matrix of the background
     * @param otherPlayers the list of the other players
     */
    private void buildOtherUsername(String[][] background , List<Player> otherPlayers) {
        List<String> otherPlayersUsername = new ArrayList<>();

        //fill player's username
        for (int i = 0; i < otherPlayers.size(); i++) {
            otherPlayersUsername.add(otherPlayers.get(i).getUsername());
        }
        for (int i = 0; i < otherPlayersUsername.size(); i++) {
            //j has max length of 17 because the space available for username is max 17 char
           if(otherPlayersUsername.get(i).length() <17){
                switch (i) {
                    case 0 -> background[24][3] = centerString(otherPlayersUsername.get(i), 18);
                    case 1 -> background[24][5] = centerString(otherPlayersUsername.get(i), 18);
                    case 2 -> background[24][7] =centerString(otherPlayersUsername.get(i), 18);
                }
            }
        }

    }
    /***
     * this method update points of the player in the allSetup matrix
     * @param yourPoints has to be a number between 0 and 999
     * */
    private void updateYourPoints(int yourPoints){
        String numStr = String.format("%02d", yourPoints); // trasforma l'intero in una stringa di due cifre con zero a sinistra se necessario
        numStr = String.format("%6s", numStr).replace(' ', ' ');
        allSetup[4][10] = numStr ;
    }
    private int getPoints(Player player, GameState gameState){

        int sumOfPoints = 0;

        sumOfPoints += player.getCommonGoalPoints();

        sumOfPoints += player.getPersonalGoalPoints();

        if(player.getFinishedFirst()) { sumOfPoints += 1;}

        sumOfPoints += player.getAdjacentPoints();

        return sumOfPoints;
    }
    /***
     * this method update the stack of the common goals directly in the allSetup matrix
     * @param commonStack1 has to be a number between 0 and 9 {0,2,4,8}
     * @param commonStack2 has to be a number between 0 and 9 {0,2,4,8}
     * */
    private void updateCommongoalStacks(int commonStack1, int commonStack2){
        String common1 = String.format("%02d", commonStack1); // trasforma l'intero in una stringa di due cifre con zero a sinistra se necessario
        common1 = String.format("%3s", common1).replace(' ', ' '); // aggiunge spazi bianchi a sinistra e a destra della stringa fino a renderla lunga tre caratteri
        String common2 = String.format("%02d", commonStack2); // trasforma l'intero in una stringa di due cifre con zero a sinistra se necessario
        common2 = String.format("%3s", common2).replace(' ', ' '); // aggiunge spazi bianchi a sinistra e a destra della stringa fino a renderla lunga tre caratteri
        allSetup[4][4] =common1;
        allSetup[4][6] =common2;
    }
    private void setOnSetup(String[][] image , int row, int col){

        for (int i = 0; i <image.length; i++) {
            allSetup[i+row][col] = Color.aggregateStrings(image[i]);
        }
    }




    /********************************* Util Functions **********+***************************/

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
            if(player.getUsername().equals(Settings.getInstance().getUsername())){
                return player;
            }
        }
        return null;
    }

    /**
     * Center a string in a string of a given length
     * @param str the string to center
     * @param totalLength the length of the string to return, HAS TO BE >= 4
     * @return the centered string
     */
    private String centerString(String str, int totalLength) {
        if (str == null || totalLength <= str.length()) {
            return str;
        }
        int leftPadding = (totalLength - str.length()) / 2;
        int rightPadding = totalLength - str.length() - leftPadding;
        String result = String.format("%" + leftPadding + "s%s%" + rightPadding + "s", "", str, "");
        return result;
    }

}