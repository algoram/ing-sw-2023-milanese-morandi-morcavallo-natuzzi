import myshelfie_controller.EventHandler;
import myshelfie_controller.network.rmi.RMIServer;

public class Server {

    public static void main(String[] args) {
        EventHandler eventHandler = new EventHandler();

        RMIServer rmiServer = new RMIServer(eventHandler);
    }

}
