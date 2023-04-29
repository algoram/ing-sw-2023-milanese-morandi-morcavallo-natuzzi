package myshelfie_controller;

import myshelfie_controller.response.*;
import myshelfie_model.GameState;
import myshelfie_model.GameUpdate;
import myshelfie_view.View;

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
        if (response instanceof PlayerConnectSuccess) {
            // start pinging server
            System.out.println("Starting to ping...");
            EventDispatcher.getInstance().startPinging();
        } else if (response instanceof PlayerConnectFailure) {
            String message = ((PlayerConnectFailure) response).getMessage();
            View.getInstance().showMessage(message);
            EventDispatcher.getInstance().setPlayerCredentials(null);

        } else if (response instanceof PlayerDisconnectSuccess) {

            String playerout = ((PlayerDisconnectSuccess) response).getDisconnectedPlayer();
            String playerTurn = ((PlayerDisconnectSuccess) response).getPlayerTurn();

            View.getInstance().showMessage("Player"+ playerout + "disconnected");

            if (playerTurn.equals(EventDispatcher.getInstance().getPlayerCredentials().getUsername())){
                View.getInstance().showMessage("it's your turn");
            }else{
                View.getInstance().showMessage("it's + " + playerTurn + "'s turn");
            }

        } else if (response instanceof MessageSendSuccess) {
            View.getInstance().showMessage("Message sent successfully!");

        } else if (response instanceof MessageSendFailure) {
            View.getInstance().showMessage("Message send Failure");

        } else if (response instanceof MessageSendResponse) {
            String message = ((MessageSendResponse) response).getMessage();
            String sender = ((MessageSendResponse) response).getFrom();
            Boolean AllPlayers = ((MessageSendResponse) response).getAllPlayers();

            if (AllPlayers){
                View.getInstance().showMessage("Message from " + sender + "to everyone:");
            }else{
                View.getInstance().showMessage("Message from " + sender + "to you:");
            }
            View.getInstance().showMessage(message);

        } else if (response instanceof PingAck) {
            // do nothing

        } else if (response instanceof ConnectUpdate) {
            GameState gameState = ((ConnectUpdate) response).getGameState();
            View.getInstance().showMessage("");
            updateView(gameState);


        } else if (response instanceof TakeTilesSuccess) {
            View.getInstance().showMessage("Tiles taken successfully!");
            GameUpdate gameUpdate = ((TakeTilesSuccess) response).getGameUpdate();


        } else if (response instanceof TakeTilesUpdate) {


        } else if (response instanceof TakeTilesFailure) {

        }
    }

    private void updateView(GameState gameState){

    }

    private void updateView(GameUpdate gameUpdate){

    }

    public void closeHandler() {
        threadRun = false;
    }
}
