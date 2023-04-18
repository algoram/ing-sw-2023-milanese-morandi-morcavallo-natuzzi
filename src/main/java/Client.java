import myshelfie_controller.ClientController;
import myshelfie_controller.network.rmi.RMIClient;
import myshelfie_controller.network.rmi.RMINetworkInterface;
import myshelfie_controller.network.rmi.RMINetworkInterfaceImpl;
import myshelfie_model.Position;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class Client {

    public static void main(String[] args) {
        ClientController clientController = new ClientController();

        try {
            RMIClient client = new RMIClient(clientController);

            client.connect("localhost");

            client.join("gamename", "username");

            client.chat(null, "Hello World!");

            client.close();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }
    }

}
