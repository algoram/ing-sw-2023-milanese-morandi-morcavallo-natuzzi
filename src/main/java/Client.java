import myshelfie_controller.EventDispatcher;
import myshelfie_controller.UpdateHandler;
import myshelfie_network.rmi.RMIClient;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {

    public static void main(String[] args) {
        UpdateHandler updateHandler = new UpdateHandler();

        RMIClient rmiClient = null;
        try {
            rmiClient = new RMIClient(updateHandler, "localhost");
        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }

        EventDispatcher eventDispatcher = new EventDispatcher(
                rmiClient,
                "gamename",
                "username"
        );

        eventDispatcher.chat(null, "Hello World!");
        eventDispatcher.chat("player2", "Hello Player 2!");
    }

}
