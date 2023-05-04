import myshelfie_controller.*;
import myshelfie_network.rmi.RMIClient;
import myshelfie_network.socket.SocketClient;
import myshelfie_view.View;

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
        //init()

        Settings.getInstance().setViewType(ViewType.CLI);
        View.getInstance();


        EventDispatcher.getInstance();

    }

}
