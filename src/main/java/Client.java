import myshelfie_controller.ConnectionType;
import myshelfie_controller.EventDispatcher;
import myshelfie_controller.UpdateHandler;
import myshelfie_network.rmi.RMIClient;
import myshelfie_network.socket.SocketClient;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {

    public static void main(String[] args) {
        try {
            RMIClient.getInstance().connect("localhost");
        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }

        try {
            SocketClient.getInstance().start("localhost", 19736);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        EventDispatcher.getInstance().setPlayerCredentials("player");
        EventDispatcher.getInstance().setConnectionType(ConnectionType.SOCKET);

        EventDispatcher.getInstance().connect(4);

        EventDispatcher.getInstance().chat(null, "Hello World!");
        EventDispatcher.getInstance().chat("player2", "Hello Player 2!");
    }

}
