package myshelfie_view.cli;

import myshelfie_controller.ConnectionType;
import myshelfie_controller.EventDispatcher;
import myshelfie_view.InputReader;
import myshelfie_view.View;

import java.io.PrintStream;
import java.util.concurrent.ExecutionException;

public class CliView extends View {
    private InputReader inputReader;
    private final PrintStream out;
    public CliView(EventDispatcher dispatcher) {
        super(dispatcher);
        inputReader = new InputReader();
        out = System.out;
    }

    //******************************************************************************************************************
    //*********************************************** PUBLIC METHODS **************************************************
    public void init() throws ExecutionException, InterruptedException {
        out.println("Welcome to MyShelfie!");
        out.print(  "  __       ____      __     _____ __    __ ______ __       ______ _____ ______    \n" +
                    " |  \     /  \ \    / /    / ____|  |  |  |  ____|  |     |  ____|_   _|  ____|   \n" +
                    " |    \  /    \ \_ / /    | (___ |  |__|  | |__  |  |     | |__    | | | |__      \n" +
                    " |  |  \/     |\ \/ /      \___ \|   __   |  __| |  |     |  __|   | | |  __|     \n" +
                    " |  | |  | |  | |  |       ____) |  |  |  | |____|  |_____| |     _| |_| |____    \n" +
                    " |__| |__| |__| |__|      |_____/|__|  |__|______|________|_|    |_____|______|   \n" +
                    "                                                                                  \n"
        );
        out.println("Digit '/start' to start the game.");
        out.println("Digit '/help' to see the list of available commands.");
        out.println("Digit '/exit' to exit the game.");

        inputReader = new InputReader();
        String input = null;

        while(true) {
            input = readCommand();
            if (input.startsWith("/") && commandAvailable(input)) {
                //TODO: implementare EXIT mode
            }
            else if (input.equals("/start") ){
                start();
            }
            else{
                out.println("input not valid");
            }
        }
    }


    public void connectionSuccessfull() {
        out.println("Connection Successful");
    }

    public void connectionFailed() {
        out.println("Connection Failed");
        //TODO exit
    }

    public void chatIn(String sender, String message) {
        out.println(sender + ": " + message);
    }


    //******************************************************************************************************************
    //**************************************************PRIVATE METHODS*************************************************
    private void start()  {
        inputReader = new InputReader();
        String input = null;

        //choose connection type
        while(true) {
            out.println("Digit 's' to socket 'r' to rmi");
            input = readCommand();

            if (input.equals("s")) {
                EventDispatcher.getInstance().setConnectionType(ConnectionType.SOCKET);
                break;
            }
            else if (input.equals("r")) {
                EventDispatcher.getInstance().setConnectionType(ConnectionType.RMI);
                break;
            }
            else if (input.startsWith("/") && commandAvailable(input) ){
                //TODO: implementare EXIT mode
            }
            else {
                out.println("input not valid");
                continue;
            }
        }

        //choose nickname
        while(true) {
            out.println("digit your nicknmame:");
            input = readCommand();

            if (input.startsWith("/") && commandAvailable(input) ){
                //TODO: implementare EXIT mode
            }
            //TODO come viene salvata una stringa fatta di soli spazi???
            else if ( input.equals("all") || input.equals("") || input == null || input.startsWith("/") || checkAvailableNickname(input)) {
                out.println("input not valid");
            }
            else {
                EventDispatcher.getInstance().setPlayerCredentials(input);
                break;
            }
        }

        //choose number of players
        while(true) {
            out.println("digit the number of players");
            input = readCommand();

            if (input.startsWith("/") && commandAvailable(input)) {
                //TODO: implementare EXIT mode
            }
            //TODO come viene salvata una stringa fatta di soli spazi???
            else if (!input.equals("2") && !input.equals("3") && !input.equals("4")) {
                out.println("input not valid");
            }
            else {
                EventDispatcher.getInstance().connect(Integer.parseInt(input));
                break;
            }
        }
    }

    private boolean commandAvailable(String command) {
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
            case default:
                return false;
        }
    }

    //TODO: gestire errori di lettura
    private String readCommand() {
        String input = null;
        try {
            input = inputReader.readLine();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        return input;
    }
    private boolean checkAvailableNickname(String nickname) {
        //TODO write method in EventDispatcher
        return false;
    }
    private void chatOut(){
        out.println("digit the receiver, digit 'all' to send to all");
        String receiver = readCommand();

        out.println("digit the message");
        String message = readCommand();
        EventDispatcher.getInstance().chat(receiver.equals("all") ? null : receiver, message);
    }
    private void help(){
        out.println("exit: exit the game");
        out.println("chat: send a message");
    }

    private void exit(){
        out.println("Bye Bye");
       //TODO exit event
    }

}

