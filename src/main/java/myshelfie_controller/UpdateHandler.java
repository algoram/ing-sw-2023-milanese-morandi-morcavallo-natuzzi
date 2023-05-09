package myshelfie_controller;

import myshelfie_controller.response.*;
import myshelfie_model.GameState;
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
                Thread needsToWait;

                synchronized (responseQueue) {
                    response = responseQueue.poll();
                }

                if (response != null) {
                    new Thread(() -> handle(response)).start();
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
        // TODO: verificare disconnessioni
        // System.out.println("Received response " + response.getClass().getSimpleName());

        if (response instanceof PlayerConnectSuccess) {
            // start pinging server
            System.out.println("UpdateHandler-> handle(): Starting to ping...");
            EventDispatcher.getInstance().startPinging();
            View.getInstance().connectionSuccessful();

        } else if (response instanceof PlayerConnectFailure) {
            String message = ((PlayerConnectFailure) response).getMessage();
            Settings.getInstance().setUsername(null);
            View.getInstance().connectionFailed(message);

        } else if (response instanceof PlayerDisconnectSuccess) {

            String playerout = ((PlayerDisconnectSuccess) response).getDisconnectedPlayer();
            String playerTurn = ((PlayerDisconnectSuccess) response).getPlayerTurn();

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

            System.out.println("UpdateHandler-> handle(): ConnectUpdate received");

            GameState gameState = ((ConnectUpdate) response).getGameState();
            View.getInstance().initGameState(gameState);
            playerTurn(gameState.getPlayerTurn());

        } else if (response instanceof TakeTilesSuccess) {
            if (Settings.DEBUG) System.out.println("UpdateHandler-> handle(): TakeTilesSuccess received");

            //todo this function is created to regulate the listener thread but could carry gamestate
            View.getInstance().takeSuccess();
            GameState gameState = ((TakeTilesSuccess) response).getGameState();
            View.getInstance().displayNewSetup(gameState);
            playerTurn(gameState.getPlayerTurn());

        } else if (response instanceof TakeTilesUpdate) {

            GameState gameState = ((TakeTilesUpdate) response).getGameState();
            View.getInstance().displayNewSetup(gameState);
            playerTurn(gameState.getPlayerTurn());

        } else if (response instanceof TakeTilesFailure) {

            String reason = ((TakeTilesFailure) response).getReason();
            View.getInstance().takeFailed(reason);
            //thi cli already runs the take turn again

        } else if (response instanceof GameFinished) {
            String winner = ((GameFinished) response).getWinner();
            //todo
            View.getInstance().gameFinished(winner);

        } else if (response instanceof GameFinishedForYou){
            //todo
            View.getInstance().gameFinishedForYou();
        } else {
            System.out.println("response still not implemented in update handler");
        }
    }

    private void playerTurn(String playerTurn){
        if (playerTurn.equals(Settings.getInstance().getUsername())){
            View.getInstance().yourTurn();
        }else{
            View.getInstance().turnOf(playerTurn);
        }
    }

    public void closeHandler() {
        threadRun = false;
    }
}
