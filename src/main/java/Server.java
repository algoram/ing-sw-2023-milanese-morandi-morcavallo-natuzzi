import myshelfie_controller.ServerController;
import myshelfie_controller.network.rmi.RMIServer;
import myshelfie_model.Game;

public class Server {

    public static void main(String[] args) {
        // TODO: consider turning this into a singleton
        ServerController serverController = new ServerController();

        RMIServer rmiServer = new RMIServer(serverController);
    }

}
