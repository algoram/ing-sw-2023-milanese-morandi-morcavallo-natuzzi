package myshelfie_view.cli;

import myshelfie_controller.ConnectionType;
import myshelfie_controller.EventDispatcher;
import myshelfie_view.View;
import java.io.PrintStream;
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
        out.println("Welcome to MyShelfie!");
        out.print(  "  __       ____      __     _____ __    __ ______ __       ______ _____ ______    \n" +
                " |  \     /  \ \    / /    / ____|  |  |  |  ____|  |     |  ____|_   _|  ____|   \n" +
                " |    \  /    \ \_ / /    | (___ |  |__|  | |__  |  |     | |__    | | | |__      \n" +
                " |  |  \/     |\ \/ /      \___ \|   __   |  __| |  |     |  __|   | | |  __|     \n" +
                " |  | |  | |  | |  |       ____) |  |  |  | |____|  |_____| |     _| |_| |____    \n" +
                " |__| |__| |__| |__|      |_____/|__|  |__|______|________|_|    |_____|______|   \n" +
                "                                                                                  \n"
        );
        out.println("Digit '/help' to see the list of available commands.");
        out.println("Digit '/exit' to exit the game.");
        new Thread( () ->{
            while (gameIsRunning) {

                String input = null;
                input = readSafe();

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
                        continue;
                    }
                }
                //choose nickname
                while(startIsRunning) {
                    out.println("digit your nicknmame:");
                    input = readSafe();

                    if (input.startsWith("/") && commandAvailable(input,startIsRunning) ){
                        out.println("command not valid");
                    }
                    else if ( input.equals("all") || input.equals("") || input == null || input.startsWith("/") || input.contains(" ") ) {
                        out.println("input not valid");
                    }
                    else {
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
        }).start();

    }

    public void connectionSuccessfull() {
        //todo move  chatIsRunning = true;
        out.println("Waiting for other players to enter!");
    }

    public void connectionFailed(String reason) {
        out.println(reason);
        //todo ask again username try again connect
    }

    public void chatIn(String sender, String message) {
        out.println(sender + ": " + message);
    }


    //******************************************************************************************************************
    //**************************************************PRIVATE METHODS*************************************************

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
        } while (input.equals("") || input == null);

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

    public void showMessage (String message){
        //todo
    }

    public void closeCliView() {
        gameIsRunning = false;
    }
}