package myshelfie_view.cli;

import jdk.jshell.spi.ExecutionControl;
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
import java.util.List;
import java.util.Scanner;

public class CliView extends View {
    private boolean gameIsRunning; //the loop
    private boolean chatIsRunning = false; //the moment in which the chat is available
    private static CliView instance = null;
    private Scanner scanner;
    private GameState gameState; //is the actual state of the game
    private final PrintStream out;

    private Double refreshRate = 100.0; //refresh rate of the game

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

        new Thread(() -> {
            while (gameIsRunning) {
                synchronized (gameState){
                    //todo create a class display that memorize a gameState and the last messages
                    Printer.getInstance().DisplayAllSetup(this.gameState);
                }

                try{
                    Thread.sleep(this.refreshRate.longValue());
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                }catch (Exception e){
                    e.printStackTrace();
                    System.out.println("Error in the refresh rate");
                }
            }
        }).start();
    }

    @Override
    public void chatIn(String sender, String message, boolean isPublic) {
        if(isPublic)
            out.println(sender + ": " + message);
        else
            out.println("(" + sender+ " )" + " to you: " + message);
    }

    @Override
    public void chatOut(String to, String message) {

    }

    @Override
    public void messageSentSuccessfully() {
        System.out.println("Message sent successfully");
    }

    @Override
    public void messageSentFailure(String errorMessage) {
        System.out.println("Message sent failure: " + errorMessage);
    }

    @Override
    public void playerDisconnected(String playerOut) {
        System.out.println(playerOut + " disconnected");
    }

    @Override
    public void yourTurn() {
        System.out.println("It's your turn!");
    }

    @Override
    public void takeFailed(String reason) {
        System.out.println("Take failed: " + reason);
    }

    @Override
    public void turnOf(String playerTurn) {
        System.out.println("It's " + playerTurn + "'s turn!");
    }

    @Override
    public void takeTile(List<Position> tiles, int column) {

    }

    @Override
    public void displayNewSetup(GameState gameState){
        synchronized (gameState) {
            this.gameState = gameState;
        }
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
    }

    /***
     * restart the init method from the user setup
     *param startIsRunning flag to decide if the start phase is running or not
     * */
    private void askLogin(){
        String input = null;
        //choose nickname
        while(gameIsRunning ) {
            out.println("digit your nicknmame:");
            input = readSafe();

            if (input.startsWith("/") && commandAvailable(input) ){
                out.println("command not valid");
            } else if (input.length()>17) {
                //17 is the max length of a nickname for the right print on CLI view
                out.println("nickname too long");
            } else {
                Settings.getInstance().setUsername(input);
                break;
            }
        }
        //choose number of players
        while(gameIsRunning) {
            out.println("digit the number of players");
            input = readSafe();

            if (input.startsWith("/") && commandAvailable(input) ){
                out.println("command not valid");
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
                case "/help":
                    help();
                    return true;
                case "/chat":
                    chatOut();
                    return true;
                case "/exit":
                    exit();
                    return true;
                default:
                    return false;
            }}
        else{
            switch (command) {
                case "/help":
                    help();
                    return true;
                case "/exit":
                    exit();
                    return true;
                default:
                    return false;
            }
        }
    }
    /***
     * Read a string from the console, if the string is empty or null, it will ask again
    */
    private String readSafe() {
        String input = null;
        do {
            input = scanner.nextLine();
        } while (input.equals(""));

        return input;
    }
    /***
     * Ask the user to digit the receiver and the message, then it will send the message to the server
     */
    private void chatOut(){
        out.println("digit the receiver, digit 'all' to send to all");
        String receiver = readSafe();

        out.println("digit the message");
        String message = readSafe();
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


}