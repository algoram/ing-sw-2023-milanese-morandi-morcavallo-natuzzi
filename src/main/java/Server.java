import myshelfie_controller.EventHandler;
import myshelfie_controller.GameManager;
import myshelfie_controller.UpdateDispatcher;
import myshelfie_network.rmi.RMIServer;

public class Server {

    public static void main(String[] args) {
        RMIServer.getInstance().start(1099);
    }

}
