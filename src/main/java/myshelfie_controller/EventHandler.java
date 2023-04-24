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

    private static EventHandler instance = null;

    private final Queue<Event> eventQueue;
    private boolean threadRun;

    private EventHandler() {
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

    public static EventHandler getInstance() {
        if (instance == null) {
            instance = new EventHandler();
        }

        return instance;
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
                UpdateDispatcher.getInstance().dispatchResponse(new MessageSendSuccess(player, game));

                String message = ((MessageSend) event).getMessage();
                String to = ((MessageSend) event).getRecipient();

                if (to != null) {
                    //if to is not null send the message to the recipient
                    UpdateDispatcher.getInstance().dispatchResponse(new MessageSendResponse(player, game, message, player, false));
                } else {
                    String[] players = GameManager.getInstance().getPlayers(game);
                    for (String p : players) {
                        if (!p.equals(player)) {
                            UpdateDispatcher.getInstance().dispatchResponse(new MessageSendResponse(player, game, message, player, true));
                        }
                    }
                }
            } else {
                System.out.println("MessageSendFailure");
                UpdateDispatcher.getInstance().dispatchResponse(new MessageSendFailure(player, game, "Message is null"));
            }


        } else if (event instanceof PlayerConnect) {
            System.out.println("PlayerConnect");
            int numberOfPlayers = ((PlayerConnect) event).getNumberOfPlayers();
            if (!GameManager.getInstance().addGame(game, player, numberOfPlayers)) {
                //TODO fix messages to distinguish between different errors
                UpdateDispatcher.getInstance().dispatchResponse(new PlayerConnectFailure(player, game,"Error in connection"));
            } else {
                UpdateDispatcher.getInstance().dispatchResponse(new PlayerConnectSuccess(player, game));
            }



        } else if (event instanceof PlayerDisconnect) {
            System.out.println("PlayerDisconnect");

            GameManager.getInstance().removePlayer(player, game);

            String[] players = GameManager.getInstance().getPlayers(game);
            for (String p : players) {
                UpdateDispatcher.getInstance().dispatchResponse(new PlayerDisconnectSuccess(p, game, player));
            }


        } else if (event instanceof Ping){
            //TODO implement ping
            System.out.println("Ping");



        } else if (event instanceof TakeTiles){
            System.out.println("TakeTiles");

            int column = ((TakeTiles) event).getColumn();
            List<Position> tiles = ((TakeTiles) event).getTiles();

            if (tiles==null || tiles.size()==0){
                UpdateDispatcher.getInstance().dispatchResponse(new TakeTilesFailure(player, game, "No tiles selected"));
            }  else if (column < 0 || column > 4){
                UpdateDispatcher.getInstance().dispatchResponse(new TakeTilesFailure(player, game, "Column out of bounds"));

            } else {

                if (GameManager.getInstance().takeTiles(game, player, column, tiles)) {
                    String[] players = GameManager.getInstance().getPlayers(game);

                    //Notify the success of the take tiles and update the view for all players

                    Board board = GameManager.getInstance().getBoard(game);
                    Bookshelf bookshelf = GameManager.getInstance().getBookshelf(game, player);
                    Token[] commonTokens = GameManager.getInstance().getCommonTokens(game, player);
                    Token playerTokens = GameManager.getInstance().getFinishToken(game, player);
                    int adjacentScore = GameManager.getInstance().getAdjacentScore(game, player);
                    int personalScore = GameManager.getInstance().getPersonalScore(game, player);


                    UpdateDispatcher.getInstance().dispatchResponse(new TakeTilesSuccess(player, game, board, bookshelf, commonTokens, playerTokens, adjacentScore, personalScore));

                    for (String p : players) {
                        if (!p.equals(player)) {
                            updateDispatcher.dispatchResponse(new TakeTilesUpdate(p, game, board, bookshelf, commonTokens, playerTokens, adjacentScore, player));
                        }
                    }

                } else {
                    UpdateDispatcher.getInstance().dispatchResponse(new TakeTilesFailure(player, game, "Error in taking tiles"));
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
