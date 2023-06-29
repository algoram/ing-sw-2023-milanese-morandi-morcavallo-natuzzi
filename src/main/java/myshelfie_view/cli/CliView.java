package myshelfie_view.cli;

import myshelfie_controller.*;
import myshelfie_model.GameState;
import myshelfie_model.Position;
import myshelfie_network.rmi.RMIClient;
import myshelfie_network.socket.SocketClient;
import myshelfie_view.View;
import myshelfie_view.cli.printers.Printer;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.*;



public class CliView extends View {
    Thread ListenerThread;
    private final Object lockInOut = new Object();
    private boolean gameIsRunning; //the loop
    private boolean chatIsRunning = false; //the moment in which the chat is available
    private boolean isMyTurn = false;

    private final Queue<Messages> messagesQueue = new LinkedList<>();

    private static CliView instance = null;
    private final Scanner scanner;
    private GameState gameState; //is the actual state of the game
    private final PrintStream out;

    private final InputStream in;


    private CliView() {
        out = System.out;
        in = System.in;
        this.scanner = new Scanner(in);
        this.gameIsRunning = true;
    }


    private void commandListenerSync()  {
        while (gameIsRunning) {

            String input = null;
            try{
                Thread.sleep(30);
            }catch (Exception e){
                //do nothing
            }

            synchronized (lockInOut){

                try {
                    while(isMyTurn) {
                        try {lockInOut.wait();}
                        catch (Exception e){
                            //do nothing
                        }
                    }
                    long startTime = System.currentTimeMillis();
                    out.flush();
                    while (in.available() == 0 && (System.currentTimeMillis() - startTime) < 30) {
                        if(in.available() > 0) break;
                    }

                    if (in.available() > 0) {
                        input = scanner.nextLine();
                    }

                }catch (IOException e) {
                    if (Settings.DEBUG) System.err.println("CliView ERROR - IO exception occurred");
                }finally {
                    if (input != null) {
                        if (!commandAvailable(input)) {
                            System.out.println("Command not available");
                        }
                    }
                }
                lockInOut.notifyAll();
            }
        }
    }


    public void start() {
        Printer.getInstance().Logo(); //print MyShelfie
        showCommandsAvailable();

        askConnection();

        try {
            askHostname();
        } catch (Exception e) {
            if (Settings.DEBUG) System.err.println("CliView ERROR - Exception while asking hostname");
        }
        askLogin();
    }

    @Override
    public void gameStarted() {
        System.out.println("Game started!");
    }

    @Override
    public void updateQueuePosition(int newPosition) {
        if (newPosition != 0) {
            System.out.println("Waiting in the queue: " + newPosition + " players are in front of you");
            return;
        }

        askNumberOfPlayers();
    }

    @Override
    public void gameCreateFailure(String reason) {

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

        showCommandsAvailable();
        if (gameState.getPlayerTurn().equals(Settings.getInstance().getUsername())) {
            isMyTurn = true;
        }
        Printer.getInstance().DisplayAllSetup(this.gameState);

        ListenerThread = new Thread(this::commandListenerSync);
        //ListenerThread.setPriority(2);//could set in update dispatcher a 5 priority
        ListenerThread.start(); //start the listener that takes the lock
    }

    @Override
    public void chatIn(String sender, String message, boolean isPublic) {
        if(Settings.DEBUG) {
            System.out.println(sender + " " + message + " " + isPublic);
        }

        messagesQueue.add(new Messages(message, sender, isPublic, new Date(), false));

        if (messagesQueue.size() > 15) {
            messagesQueue.poll();
        }
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
        synchronized (lockInOut) {
            out.println("It's your turn!");
            askTiles();
        }
    }

    @Override
    public void takeFailed(String reason) {
        out.println("TakeTiles failed: " + reason);
        if (reason.equals("The game is Paused due to disconnection of another player")){
            synchronized (lockInOut) {
                isMyTurn = false;
                lockInOut.notifyAll();
            }
        }
        else yourTurn();
    }

    @Override
    public void turnOf(String playerTurn) {
        if(Settings.DEBUG) System.out.println("DEBUG CliView -> isMyTurn is:" + isMyTurn);
        out.println("It's " + playerTurn + "'s turn!");
    }

    @Override
    public void takeTiles(List<Position> tiles, int column) {
        out.println("Sending your move to the server...");
        try {
            EventDispatcher.getInstance().takeTiles(tiles, column);
        } catch (Exception e) {
            if (Settings.DEBUG) System.err.println("CliView ERROR - Failed to take the tiles");
        }
    }

    @Override
    public void takeSuccess(){
        synchronized (lockInOut) {
            out.println("Tiles taken successfully!");
            isMyTurn = false;
            lockInOut.notifyAll();
        }
    }

    @Override
    public void displayNewSetup(GameState gameState){
        this.gameState = gameState;

        if (gameState.getPlayerTurn().equals(Settings.getInstance().getUsername())) {isMyTurn = true;}
        Printer.getInstance().DisplayAllSetup(this.gameState);

        if (Settings.DEBUG)System.out.println(messagesQueue.size() + " messages arrived");


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
        if (isMyTurn) {
            if(Settings.DEBUG) System.out.println("DEBUG CliView -> isMyTurn still true should be false");
            isMyTurn = false;
        }
    }

    @Override
    public void closeCliView() {
        gameIsRunning = false;
    }

    //******************************************************************************************************************
    //**************************************************PRIVATE METHODS*************************************************

    /***
     * This method is used to as the type of connection
     */
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

    /***
     * This method is used to ask the username
     */
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
                RMIClient.getInstance().connect(input);
            } catch (RemoteException | NotBoundException e) {
                if (Settings.DEBUG) System.err.println("CliView ERROR - Could not connect to the server");
                System.out.println("Couldn't connect to the server. Try again ...");
            }
        } else if (Settings.getInstance().getConnectionType() == ConnectionType.SOCKET) {
            try {
                SocketClient.getInstance().start(input, 19736);
            } catch (IOException e) {
                if (Settings.DEBUG) System.err.println("CliView ERROR - Could not connect to the server");
                System.out.println("Couldn't connect to the server. Try again ...");
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
        String input;
        //choose nickname
        while(gameIsRunning ) {
            out.println("Digit your nickname:");
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

        EventDispatcher.getInstance().connect();
    }

    /***
     * This method is used to ask the number of players that will play the game
     */
    private void askNumberOfPlayers() {
        int numberOfPlayers = -1;

        // choose number of players
        while(gameIsRunning) {
            out.println("Digit the number of players [2/3/4]");
            String input = readSafe();

            if (input.startsWith("/") && commandAvailable(input) ){
                out.println("Back to the game...");
            } else if (!input.matches("[a-zA-Z0-9 ]+")) {
                out.println("input not valid: only letters and numbers are allowed");
            } else if (!input.equals("2") && !input.equals("3") && !input.equals("4")) {
                out.println("input not valid");
            } else {
                numberOfPlayers = Integer.parseInt(input);
                break;
            }
        }

        EventDispatcher.getInstance().createGame(numberOfPlayers);
    }

    private boolean notAvailableUsername(String input) {
        return input.equals("") || input.trim().equals(" ") || input.trim().equalsIgnoreCase("ALL") || input.startsWith("/");
    }

    /***
     * This method is used to verify and send a player's move during the game
     */
    private void askTiles(){

        String input;
        List<Position> modelPositions = new ArrayList<>();
        int columnChosen;
        //choose positions
        while(gameIsRunning ) {
            out.println("Choose tiles positions (1 to 3) from the board: [ex: A1 B2 C3, ex: A1 B2, ex: A1]");
            input = readSafe();
            String[] positions = input.trim().toUpperCase().split("\\s+"); //split the input in an array of strings without spaces ex:"    a3 A4 a6    " -> ["A3", "A4", "A6"]
            if (input.startsWith("/") && commandAvailableDuringTiles(input) ) {
                out.println("Back to the game...");
            } else if ( !input.matches("[a-zA-Z0-9 ]+")) {
                out.println("input not valid: only letters and numbers are allowed");
            }
            else if(input.length() > 12){
                out.println("Too long input for a move");
            }
            else if (checkCoordinates(positions,gameState.getPlayers().size())) {
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

            if (input.startsWith("/") && commandAvailableDuringTiles(input) ) {
                out.println("command not valid");
            } else if ( !input.matches("[a-zA-Z0-9 ]+" )) {
                out.println("input not valid: only letters and numbers are allowed");
            } else if (!input.equals("1") && !input.equals("2") && !input.equals("3") && !input.equals("4") && !input.equals("5") ) {
                    out.println("input not valid");
            } else {
                columnChosen = Integer.parseInt(input);
                //Send the move to the server
                takeTiles(modelPositions, columnChosen);
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
            if (command.equals("/showcommon1")){
                showCommonGoal(1);
                return true;
            }else if (command.equals("/showcommon2")) {
                showCommonGoal(2);
                return true;
            }else if (command.equals("/help")) {
                help();
                return true;
            }else if (command.equals("/exit")) {
                exit();
                return true;
                //if command starts with /chat, it will send the rest of the command to the other players as a message
            }else if (command.startsWith("/chat")) {
                chatOut(command);
                return true;
            }else if (command.equals("/showchat")) {
                showChat();
                return true;
            }else {
                return false;
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
     * Check if the command is available, if it is, it will execute it
     * If the commando corresponds to exit, it will set the gameIsRunning flag to false
     *
     * @param command the command to check
     * @return true if the command is available, false otherwise
     */
    private boolean commandAvailableDuringTiles(String command){
        if(chatIsRunning){
            if (command.equals("/showcommon1")){
                showCommonGoal(1);
                return true;
            }else if (command.equals("/showcommon2")) {
                showCommonGoal(2);
                return true;
            }else if (command.equals("/help")) {
                help();
                return true;
                //if command starts with /chat, it will send the rest of the command to the other players as a message
            }else if (command.startsWith("/chat")) {
                chatOut(command);
                return true;
            }else if (command.equals("/showchat")) {
                showChat();
                return true;
            }else {
                return false;
            }
        }
        else{
            switch (command) {
                case "/help" -> {
                    help();
                    return true;
                }
                default -> {
                    return false;
                }
            }
        }
    }

    private void showCommandsAvailable(){
        if(chatIsRunning){
            out.println("Commands available: "); // /showcommon1, /showcommon2, /help, /chat, /exit
            out.println("/showcommon1: show the I common goal");
            out.println("/showcommon2: show the II common goal");
            out.println("/help: show the commands available");
            out.println("/chat to message: send a message to the other players (for public messages,instead of the nickname digit: all)");
            out.println("/showchat: show the chat");
            out.println("/exit: exit from the game");
        }
        else{
            out.println("Commands available: "); // /help, /exit
            out.println("/help: show the commands available");
            out.println("/exit: exit from the game");
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


    private void showCommonGoal(int number){
        switch (number){
            case 1 -> Printer.getInstance().DisplayCommonGoal(gameState.getCommonGoals()[0]);
            case 2 -> Printer.getInstance().DisplayCommonGoal(gameState.getCommonGoals()[1]);
        }
    }

    /***
     * Ask the user to digit the receiver and the message, then it will send the message to the server
     */
    private void chatOut(String command){

        String receiver;
        String message;
        String[] splitCommand = command.split(" ", 3);

        if (splitCommand.length == 1) {
            out.println("You must specify a receiver and a message");
            return;
        }
        if (splitCommand.length == 2) {
            out.println("You must specify a message");
            return;
        }

        receiver = splitCommand[1];
        message = splitCommand[2];

        if (receiver.equals("")) {
            out.println("You must specify a receiver");
            return;
        }
        //if the message is empty or contains just spaces, it will not send it
        if (message.equals("") || message.matches(" *")) {
            out.println("You must specify a message");
            return;
        }

        if (receiver.length() > 20) {out.println("Nickname too long, max 20 characters");
            return;
        }
        if (message.length() > 60) {out.println("Message too long, max 60 characters");
            return;
        }


        if (Settings.DEBUG) System.out.println("CliView-> chatOut: Sending message to " + receiver + ": " + message);
        if(!receiver.equals(Settings.getInstance().getUsername())){
            messagesQueue.add(new Messages(message,receiver, receiver.equals("all"), new Date() ,true));
        }
        EventDispatcher.getInstance().chat(receiver.equals("all") ? null : receiver, message);

    }
    private void help(){
        out.println("Available commands:");
        out.println("/help: show the list of available commands");
        if (chatIsRunning) {
            out.println("/chat <to> <message>: send a message to other players");
            out.println("/showchat: show the chat");
            out.println("/showcommon1: show the I common goal");
            out.println("/showcommon2: show the II common goal");
        }
        out.println("/exit: exit the game");
    }
    private void exit(){
        out.println("Bye Bye");
        gameIsRunning = false;
        //EventDispatcher.getInstance().playerDisconnect();
        EventDispatcher.getInstance().stopPinging();
        System.exit(0);
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
            put("G3",3);
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
                return true;
            }
            if (possibleCoordinate.containsKey(coordinate)) {
                //check if the input is a valid coordinate
                switch (numPlayers) {
                    case 2 -> {
                        if (possibleCoordinate.get(coordinate) <= 2) {
                            return false;
                        } else {
                            out.println("Wrong input: " + coordinate + " is not a valid coordinate...");
                            return true;
                        }
                    }
                    case 3 -> {
                        if (possibleCoordinate.get(coordinate) <= 3) {
                            return false;
                        } else {
                            out.println("Wrong input: " + coordinate + " is not a valid coordinate...");
                            return true;
                        }
                    }
                    case 4 -> {
                        if (possibleCoordinate.get(coordinate) <= 4) {
                            return false;
                        } else {
                            out.println("Wrong input: " + coordinate + " is not a valid coordinate...");
                            return true;
                        }
                    }
                    default -> {
                        out.println("Wrong input: " + numPlayers + " is not a valid number of players...");
                        return false;
                    }
                }
            } else {
                out.println("Wrong input: " + coordinate + " is not a valid coordinate...");
                return false;
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

        // Map for the row COORDINATES of the board in the view
            //into the COORDINATES of the board in the model
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
           put('9', 8);
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

    public void showChat(){
        if (messagesQueue.isEmpty()){
            out.println("Yet no messages in the chat");
        }else{
            Queue<Messages> messagesQueueCopy = new LinkedList<>(messagesQueue);
            for (int i = 0; i < messagesQueue.size(); i++) {
                out.println(Objects.requireNonNull(messagesQueueCopy.poll()).toShow());
            }
        }
    }

    @Override
    public boolean getIsMyTurn() {
        return isMyTurn;
    }
}