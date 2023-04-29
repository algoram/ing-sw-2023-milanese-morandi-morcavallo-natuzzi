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
            View.getInstance().connectionSuccessful();

        } else if (response instanceof PlayerConnectFailure) {
            String message = ((PlayerConnectFailure) response).getMessage();
            EventDispatcher.getInstance().setPlayerCredentials(null);
            View.getInstance().connectionFailed(message);


        } else if (response instanceof PlayerDisconnectSuccess) {

            String playerout = ((PlayerDisconnectSuccess) response).getDisconnectedPlayer();
            String playerTurn = ((PlayerDisconnectSuccess) response).getPlayerTurn();

            //todo the following showMessage could become a function to better regulate disconnections
            View.getInstance().playerDisconnected(playerout);

            playerTurn(playerTurn);

        } else if (response instanceof MessageSendSuccess) {
            View.getInstance().messageSentSuccessfully();

        } else if (response instanceof MessageSendFailure) {
            String errorMessage = ((MessageSendFailure) response).getMessage();
            View.getInstance().messageSentFailure(errorMessage);

        } else if (response instanceof MessageSendResponse) {
            String message = ((MessageSendResponse) response).getMessage();
            String sender = ((MessageSendResponse) response).getFrom();
            Boolean allPlayers = ((MessageSendResponse) response).getAllPlayers();

            View.getInstance().chatIn(sender, message, allPlayers);

        } else if (response instanceof PingAck) {
            // do nothing

        } else if (response instanceof ConnectUpdate) {
            GameState gameState = ((ConnectUpdate) response).getGameState();
            View.getInstance().initGameState(gameState);

        } else if (response instanceof TakeTilesSuccess) {

            GameUpdate gameUpdate = ((TakeTilesSuccess) response).getGameUpdate();
            View.getInstance().showGameUpdate(gameUpdate);

        } else if (response instanceof TakeTilesUpdate) {

            GameUpdate gameUpdate = ((TakeTilesSuccess) response).getGameUpdate();
            View.getInstance().showGameUpdate(gameUpdate);
            playerTurn(gameUpdate.getPlayerTurn());

        } else if (response instanceof TakeTilesFailure) {

            String reason = ((TakeTilesFailure) response).getReason();
            View.getInstance().takeFailed(reason);
            View.getInstance().yourTurn();

        }
    }

    private void playerTurn(String playerTurn){
        if (playerTurn.equals(EventDispatcher.getInstance().getUsername())){
            View.getInstance().yourTurn();
        }else{
            View.getInstance().turnOf(playerTurn);
        }
    }

    public void closeHandler() {
        threadRun = false;
    }
}
