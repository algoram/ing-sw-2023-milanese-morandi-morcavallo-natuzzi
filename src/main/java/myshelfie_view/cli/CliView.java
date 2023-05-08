package myshelfie_view.cli;

import myshelfie_controller.ConnectionType;
import myshelfie_controller.EventDispatcher;
import myshelfie_controller.Settings;
import myshelfie_model.GameState;
import myshelfie_model.Position;
import myshelfie_network.rmi.RMIClient;
import myshelfie_network.socket.SocketClient;
import myshelfie_view.View;
import myshelfie_view.cli.printers.Printer;

import java.io.IOException;
import java.io.PrintStream;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class CliView extends View {
    private boolean gameIsRunning; //the loop
    private boolean chatIsRunning = false; //the moment in which the chat is available
    private boolean isYourTurn = false; //the moment in which is my turn
    private static CliView instance = null;
    private Scanner scanner;
    private GameState gameState; //is the actual state of the game
    private final PrintStream out;

    private CliView() {
        out = System.out;
        this.scanner = new Scanner(System.in);
        this.gameIsRunning = true;
    }

    public void start() {
        Printer.getInstance().Logo(); //print MyShelfie
        Printer.getInstance().Commands(); //print available Commands

        askConnection();

        try {
            askHostname();
        } catch (Exception e) {
            e.printStackTrace();
        }

        askLogin();
    }

    //******************************************************************************************************************
    //*********************************************** PUBLIC METHODS **************************************************


    public static CliView getInstance() {
        if (instance == null) {
            instance = new CliView();
        }
        return instance;
    }

    @Override
    public void showLogMessage(String message){
        if(message != null){
            out.println(message);
        }
    }
    @Override
    public void connectionSuccessful() {
        this.chatIsRunning = true;
        out.println("Waiting for other players to enter!");
    }
    @Override
    public void connectionFailed(String reason) {
        out.println(reason);
        out.println("Try again!");
        askLogin();
    }
    @Override
    public void initGameState(GameState gameState){
        out.println("Game is starting!");
        this.gameState = gameState;

        Printer.getInstance().DisplayAllSetup(this.gameState);
    }

    @Override
    public void chatIn(String sender, String message, boolean isPublic) {
        if(isPublic)
            out.println("(public: " + sender + "): " + message);
        else
            out.println("(private: " + sender+ "): " + message);
    }

    @Override
    public void chatOut(String to, String message) {
        out.println("You to " + to + ": " + message);
        EventDispatcher.getInstance().chat(to, message);
    }

    @Override
    public void messageSentSuccessfully() {
        out.println("Message sent successfully");
    }

    @Override
    public void messageSentFailure(String errorMessage) {
        out.println("Message sent failure: " + errorMessage);
    }

    @Override
    public void playerDisconnected(String playerOut) {
        out.println(playerOut + " disconnected");
    }

    @Override
    public void yourTurn() {
        out.println("It's your turn!");
        askTiles();
    }

    @Override
    public void takeFailed(String reason) {
        out.println("Take failed: " + reason);
        yourTurn();
    }

    @Override
    public void turnOf(String playerTurn) {
        out.println("It's " + playerTurn + "'s turn!");
    }

    @Override
    public void takeTiles(List<Position> tiles, int column) {
        out.println("Sending your move to the server...");
        try {
            EventDispatcher.getInstance().takeTiles(tiles, column);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void displayNewSetup(GameState gameState){
        this.gameState = gameState;
        Printer.getInstance().DisplayAllSetup(this.gameState);
    }

    @Override
    public void gameFinished(String winner) {
        if( winner.equals(Settings.getInstance().getUsername())) out.println("You won!");
        else out.println(winner + " won!");
        out.println("Game is finished!");
        closeCliView();
    }

    @Override
    public void gameFinishedForYou() {
        out.println("Your Bookshelf is full!");
        out.println("Please wait for the other players to finish the game!");
    }

    @Override
    public void closeCliView() {
        gameIsRunning = false;
    }

    //******************************************************************************************************************
    //**************************************************PRIVATE METHODS*************************************************
    private void askConnection(){
        String input;
        while(gameIsRunning) {
            out.println("Digit 's' to socket 'r' to rmi");
            input = readSafe();

            if (input.startsWith("/") && commandAvailable(input) ){
                out.println("command not valid");
            } else if ( !input.matches("[a-zA-Z0-9 ]+")) {
                out.println("input not valid: only letters and numbers are allowed");
            }
            else if (input.equals("s")) {
                Settings.getInstance().setConnectionType(ConnectionType.SOCKET);
                break;
            }
            else if (input.equals("r")) {
                Settings.getInstance().setConnectionType(ConnectionType.RMI);
                break;
            }
            else {
                out.println("input not valid");
            }
        }
    }

    private void askHostname() throws Exception {
        String input = null;

        while (gameIsRunning) {
            out.println("Insert the server address:");
            input = readSafe();

            if (input.startsWith("/") && commandAvailable(input)) {
                out.println("Command not valid");
            } else {
                break;
            }
        }

        if (Settings.getInstance().getConnectionType() == ConnectionType.RMI) {
            try {
                RMIClient.getInstance().connect("localhost");
            } catch (RemoteException | NotBoundException e) {
                throw new RuntimeException(e);
            }
        } else if (Settings.getInstance().getConnectionType() == ConnectionType.SOCKET) {
            try {
                SocketClient.getInstance().start("localhost", 19736);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new Exception("Unknown connection type");
        }
        //todo: add a timer that wait for the connection to be established and then ask the login again (in case server is not running)
    }

    /***
     * restart the init method from the user setup
     *param startIsRunning flag to decide if the start phase is running or not
     * */
    private void askLogin(){
        String input = null;
        //choose nickname
        while(gameIsRunning ) {
            out.println("Digit your nicknmame:");
            input = readSafe();

            if (input.startsWith("/") && commandAvailable(input) ){
                out.println("Back to the game...");
            } else if ( !input.matches("[a-zA-Z0-9 ]+")) {
                out.println("input not valid: only letters and numbers are allowed");
            } else if (input.length()>17) {
                //17 is the max length of a nickname for the right print on CLI view
                out.println("Nickname too long");
            } else if (notAvailableUsername(input)) {
               //not available username are: empty string, string with only spaces, string with only spaces and tabs and "ALL" or "all"
                // we could use this method to check if the username is already taken
                out.println("Nickname not available");
            }else {
                Settings.getInstance().setUsername(input);
                break;
            }
        }
        //choose number of players
        while(gameIsRunning) {
            out.println("Digit the number of players [2/3/4]");
            input = readSafe();

            if (input.startsWith("/") && commandAvailable(input) ){
                out.println("Back to the game...");
            } else if ( !input.matches("[a-zA-Z0-9 ]+")) {
                out.println("input not valid: only letters and numbers are allowed");
            }
            else if (!input.equals("2") && !input.equals("3") && !input.equals("4")) {
                out.println("input not valid");
            }
            else {
                EventDispatcher.getInstance().connect(Integer.parseInt(input));
                break;
            }
        }
    }

    private boolean notAvailableUsername(String input) {
        return input.equals("") || input.trim().equals(" ") || input.trim().equalsIgnoreCase("ALL") || input.startsWith("/");
    }

    private void askTiles(){
        String input;
        List<Position> modelPositions = new ArrayList<>();
        int columnChoosen = 0;
        //choose positions
        while(gameIsRunning ) {
            out.println("Choose tiles positions (1 to 3) from the board: [ex: A1 B2 C3, ex: A1 B2, ex: A1]");
            input = readSafe();
            String[] positions = input.trim().toUpperCase().split("\\s+"); //split the input in an array of strings withouth spaces ex:"    a3 A4 a6    " -> ["A3", "A4", "A6"]
            if (input.startsWith("/") && commandAvailable(input) ) {
                out.println("Back to the game...");
            } else if ( !input.matches("[a-zA-Z0-9 ]+")) {
                out.println("input not valid: only letters and numbers are allowed");
            }
            else if(input.length() > 12){
                out.println("Too long input for a move");
            } else if (checkCoordinates(positions,gameState.getPlayers().size())) {
                out.println("Try another tiles!");
            } else if (!(positions.length == 1 || positions.length == 2 || positions.length == 3) ) {
                out.println("input not valid");
            } else {
                //convert string to the positions in the list modelPositions for the server
                buildPositionList(positions, modelPositions);
                break;
            }

        }
        //choose column
        while(gameIsRunning ) {
            out.println("Choose column number (1 to 5) from the board: [1/2/3/4/5]");
            input = readSafe();

            if (input.startsWith("/") && commandAvailable(input) ) {
                out.println("command not valid");
            } else if ( !input.matches("[a-zA-Z0-9 ]+" )) {
                out.println("input not valid: only letters and numbers are allowed");
            } else if (!input.equals("1") && !input.equals("2") && !input.equals("3") && !input.equals("4") && !input.equals("5") ) {
                    out.println("input not valid");
            } else {
                columnChoosen = Integer.parseInt(input);
                //Send the move to the server
                takeTiles(modelPositions, columnChoosen);
                break;
            }
        }

    }
    /***
     * Check if the command is available, if it is, it will execute it
     * If the commando corresponds to exit, it will set the gameIsRunning flag to false
     *
     * @param command the command to check
     * @return true if the command is available, false otherwise
     */
    private boolean commandAvailable(String command){
        if(chatIsRunning){
            switch (command) {
                case "/help" -> {
                    help();
                    return true;
                }
                case "/chat" -> {
                    chatOut();
                    return true;
                }
                case "/exit" -> {
                    exit();
                    return true;
                }
                default -> {
                    return false;
                }
            }
        }
        else{
            switch (command) {
                case "/help" -> {
                    help();
                    return true;
                }
                case "/exit" -> {
                    exit();
                    return true;
                }
                default -> {
                    return false;
                }
            }
        }
    }
    /***
     * Read a string from the console, if the string is empty or null, it will ask again
    */
    private String readSafe() {
        String input;
        do {
            input = scanner.nextLine();
        } while (input.equals(""));
        return input;
    }
    /***
     * Ask the user to digit the receiver and the message, then it will send the message to the server
     */
    private void chatOut(){
        out.println("Digit the receiver, digit 'all' to send to all");
        String receiver = readSafe();

        out.println("Digit the message");
        String message = readSafe();
        System.out.println("CliView-> chatout: Sending message to " + receiver + ": " + message);
        EventDispatcher.getInstance().chat(receiver.equals("all") ? null : receiver, message);
    }
    private void help(){
        out.println("Available commands:");
        out.println("/help: show the list of available commands");
        if (chatIsRunning) out.println("/chat: send a message to the other players");
        out.println("/exit: exit the game");
    }
    private void exit(){
        out.println("Bye Bye");
        gameIsRunning = false;
        EventDispatcher.getInstance().playerDisconnect();
        //todo notify to server client the disconnection
    }
    /***
     * This function check if the input is a valid coordinate for all the possible implemented Board sizes.
     * @param positions the preprocessed input String of coordinates to check
     * @param numPlayers the number of players in the game : MUST BE 2,3 or 4
     * @return true if the input is a valid coordinate for a move, false otherwise
     * */
    private boolean checkCoordinates(String[] positions, int numPlayers){
        HashMap<String,Integer> possibleCoordinate = new HashMap<>(){{
            //A
            put("A4",3);
            put("A5",4);
            //B
            put("B4",2);
            put("B5",2);
            put("B6",4);
            //C
            put("C3",2);
            put("C4",2);
            put("C5",2);
            put("C6",2);
            put("C7",3);
            //D
            put("D2",4);
            put("D3",2);
            put("D4",2);
            put("D5",2);
            put("D6",2);
            put("D7",2);
            put("D8",2);
            put("D9",3);
            //E
            put("E1",4);
            put("E2",2);
            put("E3",2);
            put("E4",2);
            put("E5",2);
            put("E6",2);
            put("E7",2);
            put("E8",2);
            put("E9",4);
            //F
            put("F1",3);
            put("F2",2);
            put("F3",2);
            put("F4",2);
            put("F5",2);
            put("F6",2);
            put("F7",2);
            put("F8",4);
            //G
            put("G3",4);
            put("G4",2);
            put("G5",2);
            put("G6",2);
            put("G7",3);
            //H
            put("H4",4);
            put("H5",2);
            put("H6",2);
            //I
            put("I5",4);
            put("I6",3);
        }};

        if(positions.length > 3){
            out.println("Too many tiles selected");
            return false;
        }
        for (String coordinate : positions) {
            //check  if the input has a valid length for a coordinate
            if(coordinate == null || coordinate.length() != 2){
                out.println("Wrong input: coordinate is null or has a wrong length...");
                return false;
            }
            //check if the input is a valid coordinate
            switch (numPlayers){
                case 2 -> {
                    if(possibleCoordinate.get(coordinate) <= 2){
                        return false;
                    }
                    else{
                        out.println("Wrong input: " + coordinate + " is not a valid coordinate...");
                        return true;
                    }
                }
                case 3 -> {
                    if(possibleCoordinate.get(coordinate) <= 3){
                        return false;
                    }
                    else{
                        out.println("Wrong input: " + coordinate + " is not a valid coordinate...");
                        return true;
                    }
                }
                case 4 -> {
                    if(possibleCoordinate.get(coordinate) <= 4){
                        return true;
                    }
                    else{
                        out.println("Wrong input: " + coordinate + " is not a valid coordinate...");
                        return false;
                    }
                }
                default -> {
                    out.println("Wrong input: " + numPlayers + " is not a valid number of players...");
                    return false;
                }
            }

        }
        return false;
    }

    /***
     * This function build a list of Position from a list of String coordinates
     * @param positions the preprocessed input String of coordinates to use
     * @param modelPositions the list of Position that will be sent to the server
     */
    private void buildPositionList(String[] positions, List<Position> modelPositions){

        // Map for the row CORDINATES of the board in the view
            //into the CORDINATES of the board in the model
         final HashMap<Character, Integer> boardCLI2Model;
        boardCLI2Model = new HashMap<>() {{
           put('1', 0);
           put('2', 1);
           put('3', 2);
           put('4', 3);
           put('5', 4);
           put('6', 5);
           put('7', 6);
           put('8', 7);
           put('A', 8);
           put('B', 7);
           put('C', 6);
           put('D', 5);
           put('E', 4);
           put('F', 3);
           put('G', 2);
           put('H', 1);
           put('I', 0);
       }};

        for (String coordinate : positions) {
            modelPositions.add(new Position(boardCLI2Model.get(coordinate.charAt(0)), boardCLI2Model.get(coordinate.charAt(1)) ));
        }

    }

}