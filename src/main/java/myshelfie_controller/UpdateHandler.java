package myshelfie_controller;

import myshelfie_controller.response.*;

import java.util.LinkedList;
import java.util.Queue;

public class UpdateHandler {

    private static UpdateHandler instance = null;

    private final Queue<Response> responseQueue = new LinkedList<>();
    private boolean threadRun = true;

    private UpdateHandler() {
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

    public static UpdateHandler getInstance() {
        if (instance == null) {
            instance = new UpdateHandler();
        }

        return instance;
    }

    public void addToResponseQueue(Response response) {
        synchronized (responseQueue) {
            responseQueue.add(response);
        }
    }

    public void handle(Response response) {
        System.out.println("Received response " + response.getClass().getSimpleName());

        // TODO: update view from here
        if (response instanceof PlayerConnectSuccess){
            // start pinging server
            System.out.println("Starting to ping...");
            EventDispatcher.getInstance().startPinging();
        } else if (response instanceof PlayerConnectFailure) {

            
        } else if (response instanceof MessageSendSuccess) {

            
        } else if (response instanceof TakeTilesSuccess) {

            
        } else if (response instanceof TakeTilesUpdate) {

            
        } else if (response instanceof PingAck) {

            
        } else if (response instanceof PlayerDisconnectSuccess) {


        } else if (response instanceof TakeTilesFailure) {

        }
    }

    public void closeHandler() {
        threadRun = false;
    }
}
