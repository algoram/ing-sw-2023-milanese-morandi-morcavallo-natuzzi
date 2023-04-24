import myshelfie_controller.ConnectionType;
import myshelfie_controller.EventDispatcher;
import myshelfie_controller.UpdateHandler;
import myshelfie_network.rmi.RMIClient;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {

    public static void main(String[] args) {
        try {
            RMIClient.getInstance().connect("localhost");
        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }

        EventDispatcher.getInstance().setPlayerCredentials("gamename", "player");
        EventDispatcher.getInstance().setConnectionType(ConnectionType.RMI);

        EventDispatcher.getInstance().chat(null, "Hello World!");
        EventDispatcher.getInstance().chat("player2", "Hello Player 2!");
    }

}
