package myshelfie_controller;

import myshelfie_controller.event.*;
import myshelfie_controller.response.*;
import myshelfie_model.Position;
import myshelfie_model.board.Board;
import myshelfie_model.goal.Token;
import myshelfie_model.player.Bookshelf;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class EventHandler {

    private final Queue<Event> eventQueue;
    private final UpdateDispatcher updateDispatcher;
    private final GameManager gameManager;
    private boolean threadRun;

    public EventHandler(UpdateDispatcher dispatcher,GameManager gameManager) {
        updateDispatcher = dispatcher;
        this.gameManager = gameManager;

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
        String game = event.getSource()[1];
        String player = event.getSource()[0];

        if (event instanceof MessageSend) {
            if (((MessageSend) event).getMessage() != null) {
                System.out.println("MessageSend");

                //Notify the success of the message send
                updateDispatcher.dispatchResponse(new MessageSendSuccess(player, game));

                String message = ((MessageSend) event).getMessage();
                String to = ((MessageSend) event).getRecipient();

                if (to != null) {
                    //if to is not null send the message to the recipient
                    updateDispatcher.dispatchResponse(new MessageSendResponse(player, game, message, player, false));
                } else {
                    String[] players = gameManager.getPlayers(game);
                    for (String p : players) {
                        if (!p.equals(player)) {
                            updateDispatcher.dispatchResponse(new MessageSendResponse(player, game, message, player, true));
                        }
                    }
                }
            } else {
                System.out.println("MessageSendFailure");
                updateDispatcher.dispatchResponse(new MessageSendFailure(player, game, "Message is null"));
            }


        } else if (event instanceof PlayerConnect) {
            System.out.println("PlayerConnect");
            int numberOfPlayers = ((PlayerConnect) event).getNumberOfPlayers();
            if (!gameManager.addGame(game, player, numberOfPlayers)) {
                //TODO fix messages to distinguish between different errors
                updateDispatcher.dispatchResponse(new PlayerConnectFailure(player, game,"Error in connection"));
            } else {
                updateDispatcher.dispatchResponse(new PlayerConnectSuccess(player, game));
                //if all connected send GameState
            }



        } else if (event instanceof PlayerDisconnect) {
            System.out.println("PlayerDisconnect");

            gameManager.removePlayer(player, game);

            String[] players = gameManager.getPlayers(game);
            for (String p : players) {
                updateDispatcher.dispatchResponse(new PlayerDisconnectSuccess(p, game, player));
            }


        } else if (event instanceof Ping){
            //TODO implement ping
            System.out.println("Ping");



        } else if (event instanceof TakeTiles){
            System.out.println("TakeTiles");

            int column = ((TakeTiles) event).getColumn();
            List<Position> tiles = ((TakeTiles) event).getTiles();

            if (tiles==null || tiles.size()==0){
                updateDispatcher.dispatchResponse(new TakeTilesFailure(player, game, "No tiles selected"));
            }  else if (column < 0 || column > 4){
                updateDispatcher.dispatchResponse(new TakeTilesFailure(player, game, "Column out of bounds"));

            } else {

                if (gameManager.takeTiles(game, player, column, tiles)) {
                    String[] players = gameManager.getPlayers(game);

                    //Notify the success of the take tiles and update the view for all players

                    Board board = gameManager.getBoard(game);
                    Bookshelf bookshelf = gameManager.getBookshelf(game, player);
                    Token[] commonTokens = gameManager.getCommonTokens(game, player);
                    int FinishPoint = gameManager.getFinishToken(game, player);
                    int adjacentScore = gameManager.getAdjacentScore(game, player);
                    int personalScore = gameManager.getPersonalScore(game, player);


                    updateDispatcher.dispatchResponse(new TakeTilesSuccess(player, game, board, bookshelf, commonTokens, FinishPoint, adjacentScore, personalScore));

                    for (String p : players) {
                        if (!p.equals(player)) {
                            updateDispatcher.dispatchResponse(new TakeTilesUpdate(p, game, board, bookshelf, commonTokens, FinishPoint, adjacentScore, player));
                        }
                    }

                } else {
                    updateDispatcher.dispatchResponse(new TakeTilesFailure(player, game, "Error in taking tiles"));
                }
            }

        } else {
            throw new RuntimeException("Event not implemented");
        }
    }

    public void closeHandler() {
        threadRun = false;
    }

}
