package myshelfie_view.cli.printers;

import myshelfie_controller.Settings;
import myshelfie_model.GameState;
import myshelfie_model.Tile;
import myshelfie_model.goal.common_goal.CommonGoal;
import myshelfie_model.player.Player;
import myshelfie_view.cli.printers.macro.PlotBoardgame;
import myshelfie_view.cli.printers.macro.PlotBookshelf;
import myshelfie_view.cli.printers.macro.PlotCommonGoals;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Printer {
    private final PrintStream out = System.out;

    private char[][] allSetup = new char[40][100];

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
           out.println(allSetup[i]);
       }
    }
    public void DisplayCommonGoal(CommonGoal modelCommonGoal){
        PlotCommonGoals commonGoal = new PlotCommonGoals(modelCommonGoal);
        setOnSetup(commonGoal.getCommongoalCharMatrix(),23,30);
        for (int i = 0; i < allSetup.length; i++) {
            out.println(allSetup[i]);
        }
    }

    /************************ Build Functions **************************************************+**********/
    private void BuildSetup(GameState gameState){
        Player thisPlayer = getThisPlayer(gameState.getPlayers());
        List<Player> otherPlayers = otherPlayer(gameState.getPlayers(), thisPlayer);

        char[][] background = new char[40][100];
        PlotBoardgame boardgame = new PlotBoardgame();
        PlotBookshelf personalGoal = new PlotBookshelf();
        PlotBookshelf bookshelf = new PlotBookshelf();
        List<PlotBookshelf> otherBookshelf = new ArrayList<>();
        for (int i = 0; i < otherPlayers.size(); i++) {
            otherBookshelf.add(new PlotBookshelf());
        }

        //Background
        buildBackground(background, otherPlayers);
        setOnSetup(background, 0, 0);

        //Board
        boardgame.buildBoard(gameState.getBoard(), gameState.getPlayers().size());
        setOnSetup(boardgame.getBoardCharMatrix(), 1, 2);

        //CommonGoal
        updateCommongoalStacks(gameState.getTopCommonGoal()[0].getPoints(),
                                gameState.getTopCommonGoal()[1].getPoints());

        //Your Points
        updateYourPoints(getPoints(thisPlayer,gameState));

        //PersonalGoal
        personalGoal.buildBookshelf(thisPlayer.getPersonalGoal().map_PGoalToBookshelf());
        setOnSetup(personalGoal.getBookshelfCharMatrix(), 8, 49);
        //your Bookshelf
        bookshelf.buildBookshelf(thisPlayer.getBookshelf());
        setOnSetup(bookshelf.getBookshelfCharMatrix(),25,5);

        //otherBookshelf
        for (int i = 0; i < otherPlayers.size(); i++) {
            otherBookshelf.add(new PlotBookshelf());
        }
        for (int i=0; i< otherPlayers.size(); i++){
            otherBookshelf.get(i).buildBookshelf(otherPlayers.get(i).getBookshelf());
            switch (i){
                case 0 -> setOnSetup(otherBookshelf.get(i).getBookshelfCharMatrix(), 25, 31);
                case 1 -> setOnSetup(otherBookshelf.get(i).getBookshelfCharMatrix(), 25, 53);
                case 2 -> setOnSetup(otherBookshelf.get(i).getBookshelfCharMatrix(), 25, 75);
            }
        }
    }
    /**
     * Build the background of the game
     * @param background the matrix of the background
     * @param otherPlayers the list of the other players
     */
    private void buildBackground(char[][] background , List<Player> otherPlayers) {
        List<char[]> otherPlayersUsername = new ArrayList<>();
        //fill background with ░
        for (int i = 0; i < background.length; i++) {
            for (int j = 0; j < background[i].length; j++) {
                background[i][j] = '░';
            }
        }
        //fill commonGoal
        String commonGoalString = """
                 ┌────────┐  ┌────────┐\s
                 │ ┬   A  │  │ ┬┬  B  │\s 
                 │ ┴  ¯¯¯ │  │ ┴┴ ¯¯¯ │\s
                 └────────┘  └────────┘\s
                """;
        char[][] commonGoalChar = new char[4][24];
        commonGoalChar = String2CharMatrix(commonGoalString);
        for (int i = 0; i < commonGoalChar.length; i++) {
            for (int j = 0; j < commonGoalChar[i].length; j++) {
                background[i+3][j+48] = commonGoalChar[i][j];
            }
        }
        //fill YourPoints
        String yourPointsString = """
                 Your Points \s
                     C       \s
                """;
        char[][] yourPointsChar = new char[2][13];
        yourPointsChar = String2CharMatrix(yourPointsString);
        for (int i = 0; i < yourPointsChar.length; i++) {
            for (int j = 0; j < yourPointsChar[i].length; j++) {
                background[i+3][j+77] = yourPointsChar[i][j];
            }
        }
        //fill PersonalGoal
        char[] personalGoalChar = (" Personal Goal  ").toCharArray();
        for (int i = 0; i < personalGoalChar.length; i++) {
            background[21][i+47] = personalGoalChar[i];
        }
        //fill YourBookshelf
        String yourBookshelfString = """
                    Your Bookshelf:   \s
                   1 ░ 2 ░ 3 ░ 4 ░ 5  \s
                """;
        char[][] yourBookshelfChar = new char[3][23];
        yourBookshelfChar = String2CharMatrix(yourBookshelfString);
        for (int i = 0; i < yourBookshelfChar.length; i++) {
            for (int j = 0; j < yourBookshelfChar[i].length; j++) {
                background[i+23][j+4] = yourBookshelfChar[i][j];
            }
        }
        //fill player's username
        for (int i = 0; i < otherPlayers.size(); i++) {
            otherPlayersUsername.add(otherPlayers.get(i).getUsername().toCharArray());
        }
        for (int i = 0; i < otherPlayersUsername.size(); i++) {
            //j has max length of 17 because the space available for username is max 17 char
            for (int j = 0; j < otherPlayersUsername.get(i).length && j<17; j++) {
                switch (i){
                    case 0 -> background[24][j+31] = otherPlayersUsername.get(i)[j];
                    case 1 -> background[24][j+53] = otherPlayersUsername.get(i)[j];
                    case 2 -> background[24][j+75] = otherPlayersUsername.get(i)[j];
                }
            }
        }

    }
    /***
     * this method update points of the player in the allSetup matrix
     * @param yourPoints has to be a number between 0 and 999
     * */
    private void updateYourPoints(int yourPoints){
       char[] yourPointsChar = new char[3];
       yourPointsChar = (String.valueOf(yourPoints)).toCharArray();
       setOnSetup(yourPointsChar,4,82);
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
        char[] commonStack1Char = new char[1];
        commonStack1Char = (String.valueOf(commonStack1)).toCharArray();
        char[] commonStack2Char = new char[1];
        commonStack2Char = (String.valueOf(commonStack2)).toCharArray();
        setOnSetup(commonStack1Char,4,55);
        setOnSetup(commonStack2Char,4,67);
    }
    private void setOnSetup(char [][] image, int x , int y){
        for(int i= 0; i<image.length; i++){
            for(int j=0; j<image[i].length; j++){
                allSetup[x+i][y+j] = image[i][j];
            }
        }
    }
    private void setOnSetup(char[] string, int x , int y){
        for(int i= 0; i<string.length; i++){
            allSetup[x][y+i] = string[i];
        }
    }


    /********************************* Util Functions **********+***************************/
    public static char[][] String2CharMatrix(String multilineString) {
        String[] rows = multilineString.split("\n");
        char[][] charMatrix = new char[rows.length][];
        for (int i = 0; i < rows.length; i++) {
            charMatrix[i] = rows[i].toCharArray();
        }

        return charMatrix;
    }
    public static char Tile2Char(Tile tile) {
        char tileChar = ' ';

        if (tile == null) {
            return tileChar;
        }

        switch (tile.getType())
        {
            case CATS -> tileChar = 'C';
            case BOOKS -> tileChar = 'B';
            case FRAMES -> tileChar = 'F';
            case TROPHIES -> tileChar = 'T';
            case PLANTS -> tileChar = 'P';
            case GAMES -> tileChar = 'G';
        }
        return tileChar;
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
            if(player.getUsername().equals(Settings.getInstance().getUsername())){
                return player;
            }
        }
        return null;
    }

}