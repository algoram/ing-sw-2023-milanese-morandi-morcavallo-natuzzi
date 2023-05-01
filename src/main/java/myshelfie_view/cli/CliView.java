package myshelfie_view.cli;

import myshelfie_controller.ConnectionType;
import myshelfie_controller.EventDispatcher;
import myshelfie_controller.event.Event;
import myshelfie_model.GameState;
import myshelfie_model.GameUpdate;
import myshelfie_model.board.Board;
import myshelfie_model.player.Player;
import myshelfie_view.View;
import myshelfie_view.cli.printers.BasicPrint;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

public class CliView extends View {

    private boolean gameIsRunning;
    private boolean chatIsRunning = false;
    private static CliView instance = null;
    private Scanner scanner;
    private final PrintStream out;
    private CliView() {
        out = System.out;
        this.scanner = new Scanner(System.in);
        this.gameIsRunning = true;
        new Thread(() -> {
            while (gameIsRunning) {
                init();
                //todo: check if correct
            }
        }).start();
    }

    //******************************************************************************************************************
    //*********************************************** PUBLIC METHODS **************************************************

    public static CliView getInstanceCli() {
        if (instance == null) {
            instance = new CliView();
        }
        return instance;
    }
    public void init() {
        BasicPrint basicPrint = new BasicPrint(out);
        basicPrint.Logo();
        basicPrint.Commands();
        String input = readSafe();

        boolean startIsRunning = true;
        //choose connection type
        while(startIsRunning) {
            out.println("Digit 's' to socket 'r' to rmi");
            input = readSafe();

            if (input.startsWith("/") && commandAvailable(input,startIsRunning) ){
                out.println("command not valid");
            }
            else if (input.equals("s")) {
                EventDispatcher.getInstance().setConnectionType(ConnectionType.SOCKET);
                break;
            }
            else if (input.equals("r")) {
                EventDispatcher.getInstance().setConnectionType(ConnectionType.RMI);
                break;
            }
            else {
                out.println("input not valid");
            }
        }
        askLogin(startIsRunning);
    }


    public void showLogMessage(String message){
        if(message != null){
            out.println(message);
        }
    };

    public void connectionSuccessful() {
        //todo move chatIsRunning = true;
        out.println("Waiting for other players to enter!");
    }

    public void connectionFailed(String reason) {
        out.println(reason);
        out.println("Try again!");
        boolean startIsRunning = true;
        askLogin(startIsRunning);
    }

    //todo:#1
    public void initGameState(GameState gameState){
        BasicPrint basicPrint = new BasicPrint(out);
        out.println("Game is starting!");

        basicPrint.DisplayAll(gameState);
    }


    public void chatIn(String sender, String message, boolean isPublic) {
        if(isPublic)
            out.println(sender + ": " + message);
        else
            out.println("(" + sender+ " )" + " to you: " + message);
    }

    //todo:#2
    public void messageSentSuccessfully() {

    };

    //todo:#2
    public void messageSentFailure(String errorMessage) {

    }

    //todo:#2
    public void playerDisconnected(String playerOut) {

    };

    //todo:#1
    public void yourTurn() {

    };

    //todo:#1
    public void takeFailed(String reason) {

    };

    //todo:#1
    public void turnOf(String playerTurn) {

    };

    //todo:#1
    public void showGameUpdate(GameUpdate gameUpdate) {

    };

    public void closeCliView() {
        gameIsRunning = false;
    }

    //******************************************************************************************************************
    //**************************************************PRIVATE METHODS*************************************************

    /***
     * restart the init method from the user setup
     *
     *param startIsRunning flag to decide if the start phase is running or not
     * */
    private void askLogin(boolean startIsRunning){
        String input = null;
        //choose nickname
        while(startIsRunning) {
            out.println("digit your nicknmame:");
            input = readSafe();

            if (input.startsWith("/") && commandAvailable(input,startIsRunning) ){
                out.println("command not valid");
            }
            else if (input.equals("all") || input.equals("") || input.startsWith("/") || input.contains(" ")) {
                out.println("input not valid");
            } else if (input.length()>17) {
                //17 is the max length of a nickname for the right print on CLI view
                out.println("nickname too long");
            } else {
                EventDispatcher.getInstance().setPlayerCredentials(input);
                break;
            }
        }
        //choose number of players
        while(startIsRunning) {
            out.println("digit the number of players");
            input = readSafe();

            if (input.startsWith("/") && commandAvailable(input, startIsRunning) ){
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
     * @param stateIsRunning flag to decide if the game is running or not
     * @return true if the command is available, false otherwise
     */
    private boolean commandAvailable(String command, boolean stateIsRunning){
        if(chatIsRunning){
            switch (command) {
                case "/help":
                    help();
                    return true;
                case "/chat":
                    chatOut();
                    return true;
                case "/exit":
                    stateIsRunning = false;
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
                    stateIsRunning = false;
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

    private Player getThisPlayer(List<Player> players){
        for(Player player : players){
            if(player.getUsername().equals(EventDispatcher.getInstance().getUsername())){
                return player;
            }
        }
        return null;
    }

}