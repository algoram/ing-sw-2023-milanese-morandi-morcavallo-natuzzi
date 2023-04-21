package myshelfie_controller;

import myshelfie_controller.response.Response;

import java.util.LinkedList;
import java.util.Queue;

public class UpdateHandler {

    private final Queue<Response> responseQueue = new LinkedList<>();
    private boolean threadRun = true;

    public UpdateHandler() {
        new Thread(() -> {
            while (threadRun) {
                Response response;

                synchronized (responseQueue) {
                    response = responseQueue.poll();
                }

                if (response != null) {
                    handle(response);
                }
            }
        }).start();
    }

    public void addToResponseQueue(Response response) {
        synchronized (responseQueue) {
            responseQueue.add(response);
        }
    }

    public void handle(Response response) {
        // TODO: update view from here
    }

    public void closeHandler() {
        threadRun = false;
    }
}
