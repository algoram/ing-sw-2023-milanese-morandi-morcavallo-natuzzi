package myshelfie_controller;

import myshelfie_controller.event.Event;
import myshelfie_controller.event.MessageSend;
import myshelfie_controller.event.PlayerConnect;
import myshelfie_controller.response.PlayerConnectFailure;

import java.util.LinkedList;
import java.util.Queue;

public class EventHandler {

    private final Queue<Event> eventQueue;
    private final UpdateDispatcher updateDispatcher;
    private boolean threadRun;

    public EventHandler(UpdateDispatcher dispatcher) {
        updateDispatcher = dispatcher;

        eventQueue = new LinkedList<>();
        threadRun = true;

        new Thread(() -> {
            while (threadRun) {
                Event event;

                synchronized (eventQueue) {
                    event = eventQueue.poll();
                }

                if (event != null) {
                    handle(event);
                }
            }
        }).start();
    }

    public void addToEventQueue(Event event) {
        synchronized (eventQueue) {
            eventQueue.add(event);
        }
    }

    public void handle(Event event) {
        System.out.printf("Event from %s/%s: ", event.getSource()[0], event.getSource()[1]);

        if (event instanceof MessageSend) {
            System.out.println("MessageSent");
            if (((MessageSend) event).getRecipient() != null) {
                System.out.printf("\tto: %s\n", ((MessageSend) event).getRecipient());
            }
            System.out.printf("\tmessage: %s\n", ((MessageSend) event).getMessage());
        } else if (event instanceof PlayerConnect) {
            System.out.println("PlayerConnect");
            String player = event.getSource()[0];
            String game = event.getSource()[1];

            // TODO: finish handling connection

        } else {
            throw new RuntimeException("Event not implemented");
        }
    }

    public void closeHandler() {
        threadRun = false;
    }

}
