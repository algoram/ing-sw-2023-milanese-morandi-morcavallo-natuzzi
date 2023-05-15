import myshelfie_controller.EventHandler;
import myshelfie_controller.GameManager;
import myshelfie_controller.UpdateDispatcher;
import myshelfie_network.rmi.RMIServer;
import myshelfie_network.socket.SocketServer;

import java.io.IOException;
//todo implementare la gestione del setting partita disabilitato per i giocatori che entrano dopo il primo giocatore (che ha inserito numplayer etc)
public class Server {

    public static void main(String[] args) {
        RMIServer.getInstance().start(1099);
        try {
            SocketServer.getInstance().start(19736);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
