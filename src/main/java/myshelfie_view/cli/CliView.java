package myshelfie_view.cli;

import myshelfie_controller.ConnectionType;
import myshelfie_controller.EventDispatcher;
import myshelfie_controller.UpdateHandler;
import myshelfie_view.InputReader;
import myshelfie_view.View;

import java.io.PrintStream;
import java.util.concurrent.ExecutionException;

public class CliView extends View {
    private static CliView instance = null;
    private static InputReader inputReader;
    private final PrintStream out;
    private CliView() {
        inputReader = new InputReader();
        out = System.out;
    }

    public static CliView getInstanceCli() {
        if (instance == null) {
            instance = new CliView();
        }
        return instance;
    }

    public void init() throws ExecutionException, InterruptedException {
        out.println("Welcome to MyShelfie!");
        out.print(  "  __       ____       __     _____ _    _   ______ __       ______ _____ ______ \n" +
                    " |  \     /  \ \     / /    / ____|  |  |  |  ____|  |     |  ____|_   _|  ____|\n" +
                    " |    \  /    \ \_  / /    | (___ |  |__|  | |__  |  |     | |__    | | | |__   \n" +
                    " |  |  \/     |\ \ / /      \___ \|   __   |  __| |  |     |  __|   | | |  __|  \n" +
                    " |  | |  | |  | |  |        ____) |  |  |  | |____|  |_____| |     _| |_| |____  \n" +
                    " |__| |__| |__| |__|       |_____/|__|  |__|______|________|_|    |_____|______|  \n" +
                    "                                                                        \n"
        );
        out.println("Digit 'start' to start the game.");
        out.println("Digit 'help' to see the list of available commands.");
        out.println("Digit 'exit' to exit the game.");

        inputReader = new InputReader();
        String input = readCommand();


        if( input.equals("start") ){
            start();
        }

    }

    private void start()  {
        inputReader = new InputReader();
        String input = null;

        out.println("Digit 's' to socket 'r' to rmi");
        input = readCommand();
        while (!input.equals("s") && !input.equals("r")) {
            out.println("input not valid");
            out.println("Digit 's' to socket 'r' to rmi");
            input = readCommand();
        }
        if( input.equals("s") ){
            EventDispatcher.getInstance().setConnectionType(ConnectionType.SOCKET);
        }else if( input.equals("r") ){
            EventDispatcher.getInstance().setConnectionType(ConnectionType.RMI);
        }
        out.println("digit your nicknmame");
        input = readCommand();

        //TODO check if nickname is valid not 'all' not empty and not already taken
        EventDispatcher.getInstance().setPlayerCredentials(input);
        out.println("digit the number of players");
        input = readCommand();
        while (!input.equals("2") && !input.equals("3") && !input.equals("4")) {
            out.println("input not valid");
            out.println("digit the number of players");
            input = readCommand();
        }
        EventDispatcher.getInstance().connect(Integer.parseInt(input));
    }

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

    private void chatOut(){
        out.println("digit the receiver, digit 'all' to send to all");
        String receiver = readCommand();

        out.println("digit the message");
        String message = readCommand();
        EventDispatcher.getInstance().chat(receiver.equals("all") ? null : receiver, message);
    }

}

