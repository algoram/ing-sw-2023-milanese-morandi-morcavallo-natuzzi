package myshelfie_controller;

import myshelfie_controller.event.Event;
import myshelfie_controller.event.MessageSent;

import java.util.LinkedList;
import java.util.Queue;

public class EventHandler {

    private final Queue<Event> eventQueue;
    private boolean threadRun;

    public EventHandler() {
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

        if (event instanceof MessageSent) {
            System.out.println("MessageSent");
            if (((MessageSent) event).getRecipient() != null) {
                System.out.printf("\tto: %s\n", ((MessageSent) event).getRecipient());
            }
            System.out.printf("\tmessage: %s\n", ((MessageSent) event).getMessage());
        } else {
            throw new RuntimeException("Event not implemented");
        }
    }

    public void closeHandler() {
        threadRun = false;
    }

}
