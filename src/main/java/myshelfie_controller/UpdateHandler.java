package myshelfie_controller;

import myshelfie_controller.response.*;
import myshelfie_model.GameState;
import myshelfie_network.rmi.RMIClient;
import myshelfie_network.socket.SocketClient;
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

    /***
     * This method is used to handle the response received from the server
     * @param response
     */
    public void handle(Response response) {

        if (response instanceof PlayerConnectSuccess) {
            // start pinging server
            if(Settings.DEBUG)System.out.println("UpdateHandler-> handle(): Starting to ping...");
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

            if (playerTurn != null && !View.getInstance().getIsMyTurn()) playerTurn(playerTurn);
            //todo here an else should notify that the player has to wait for the other player to reconnect

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

            if(Settings.DEBUG)System.out.println("UpdateHandler-> handle(): ConnectUpdate received");

            GameState gameState = ((ConnectUpdate) response).getGameState();
            View.getInstance().initGameState(gameState);
            if (gameState.getPlayerTurn() != null) playerTurn(gameState.getPlayerTurn());

        } else if (response instanceof TakeTilesSuccess) {
            if (Settings.DEBUG) System.out.println("UpdateHandler-> handle(): TakeTilesSuccess received");

            View.getInstance().takeSuccess();
            GameState gameState = ((TakeTilesSuccess) response).getGameState();
            View.getInstance().displayNewSetup(gameState);

            //if there is just one player left the game should stop
            if (gameState.getPlayerTurn() != null) playerTurn(gameState.getPlayerTurn());

        } else if (response instanceof TakeTilesUpdate) {

            GameState gameState = ((TakeTilesUpdate) response).getGameState();
            View.getInstance().displayNewSetup(gameState);
            if (gameState.getPlayerTurn() != null) playerTurn(gameState.getPlayerTurn());

        } else if (response instanceof TakeTilesFailure) {

            String reason = ((TakeTilesFailure) response).getReason();
            View.getInstance().takeFailed(reason);

        } else if (response instanceof GameFinished) {
            String winner = ((GameFinished) response).getWinner();
            EventDispatcher.getInstance().stopPinging();
            View.getInstance().gameFinished(winner);

            if(Settings.getInstance().getConnectionType().equals(ConnectionType.RMI)) {
                try{
                    RMIClient.getInstance().closeConnection();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }else if (Settings.getInstance().getConnectionType().equals(ConnectionType.SOCKET)) {
                try{
                    SocketClient.getInstance().stop();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            System.exit(0);

        } else if (response instanceof GameFinishedForYou){
            View.getInstance().gameFinishedForYou();
        } else if (response instanceof GameCreateSuccess) {
            View.getInstance().gameStarted();
        } else if (response instanceof GameCreateUpdate) {
            View.getInstance().updateQueuePosition(((GameCreateUpdate) response).getQueuePosition());
        } else if (response instanceof GameCreateFailure) {
            View.getInstance().gameCreateFailure(((GameCreateFailure) response).getReason());
        } else {
            if(Settings.DEBUG)System.out.println("UpdateHandler-> handle(): response still not implemented in update handler");
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
