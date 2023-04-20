package myshelfie_controller.network.rmi;

import myshelfie_controller.EventHandler;
import myshelfie_controller.event.Event;

public class RMINetworkInterfaceImpl implements RMINetworkInterface {

    private final EventHandler eventHandler;

    public RMINetworkInterfaceImpl(EventHandler handler) {
        eventHandler = handler;
    }

    @Override
    public void dispatchEvent(Event event) {
        System.out.println("Event added to the queue");
        eventHandler.addToEventQueue(event);
    }

    @Override
    public void setClient(RMIClient client) {

    }
}
