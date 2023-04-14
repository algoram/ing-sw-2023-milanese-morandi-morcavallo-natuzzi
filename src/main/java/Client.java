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
        String host = "localhost";

        try {
            Registry registry = LocateRegistry.getRegistry(host);

            RMINetworkInterface server = (RMINetworkInterface) registry.lookup("MyShelfieRMI");

            server.connect("gamename", "username");

            ArrayList<Position> positions = new ArrayList<>();

            positions.add(new Position(1, 3));
            positions.add(new Position(2, 4));

            server.take("gamename", "username", 1, positions);

            server.chat("gamename", "username", null, "Hello World!");

            server.disconnect("gamename", "username");
        } catch (RemoteException e) {
            System.err.println("Couldn't retrieve remote registry.");
        } catch (NotBoundException e) {
            System.err.println("No instance of MyShelfieRMI found.");
        }
    }

}
