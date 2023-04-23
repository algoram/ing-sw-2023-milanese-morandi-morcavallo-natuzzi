import myshelfie_controller.EventHandler;
import myshelfie_controller.GameManager;
import myshelfie_controller.UpdateDispatcher;
import myshelfie_network.rmi.RMIServer;

public class Server {

    public static void main(String[] args) {
        UpdateDispatcher updateDispatcher = new UpdateDispatcher();
        EventHandler eventHandler = new EventHandler(updateDispatcher);
        GameManager gameManager = new GameManager();
        RMIServer rmiServer = new RMIServer(eventHandler, gameManager);
    }

}
