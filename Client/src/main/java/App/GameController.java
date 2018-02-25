package App;

import GUI.BoardFX;
import GUI.ExceptionAlert;
import GUI.MainApp;
import GUI.SignFX;
import Logic.Board;
import Logic.Move;
import Logic.Player;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.beans.value.WritableBooleanValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sun.applet.Main;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.Semaphore;

public class GameController {
    private Socket server = null;
    private InputStream in = null;
    private OutputStream out = null;

    private static final byte MOVE = 0;
    private static final byte DECLARE_WINNING = 1;
    private static final byte NEW_GAME = 2;
    private static final byte REMATCH = 3;
    private static final byte EXIT = 4;
    private static final byte NEW_OPPONENT = 5;
    private static final byte WAIT = 6;

    private Board gameBoard;
    private Player clientPlayer;

    private BoardFX boardFX;

    public Socket getOut(){
        return server;
    }

    public static BooleanProperty isPlayerTurn = new BooleanPropertyBase() {
        @Override
        public Object getBean() {
            return null;
        }

        @Override
        public String getName() {
            return null;
        }
    };

    public OutputStream getOutputStream(){
        return out;
    }

    public static Thread threadRecive = null;
    public static Thread threadSend = null;


    private class ThreadRecive extends Thread {
        @Override
        public void run() {
            super.run();
            while (true) {
                try {
                    byte[] command = new byte[3];
                    int i = 0;
                    while(!isInterrupted() && i < 3){
                        if(in.available() > 0) {
                            command[i] = (byte) in.read();
                            i++;
                        }
                    }
                    if(isInterrupted()){
                        break;
                    }
                    if (command[0] == MOVE) {
                        int x = command[1];
                        int y = command[2];
                        final Player.Color opponent = clientPlayer.getOpponent();
                        Move move = new Move(x, y, opponent);
                        gameBoard.doMove(move);
                        boardFX.setFill(x, y, opponent);
                        isPlayerTurn.setValue(true);
                    } else if (command[0] == DECLARE_WINNING) {
                        int x = command[1];
                        int y = command[2];
                        final Player.Color opponent = clientPlayer.getOpponent();
                        Move move = new Move(x, y, opponent);
                        gameBoard.doMove(move);
                        boardFX.setFill(x, y, opponent);
                        isPlayerTurn.setValue(true);
                        Platform.runLater( () -> {
                            new ExceptionAlert("You lost", "blablabla").showAndWait(); // TODO dla testu, usun to
                            boardFX.setDisable(true);
                        });
                        break;
                    } else if(command[0] == EXIT) {
                        if(command[1] == EXIT) { // means exit from another client
                            // TODO
                        } else { // means exit from server
                            Platform.runLater( () -> {
                                new ExceptionAlert("Server disconnected", "Playing is no longer possible. Try again later.").showAndWait();
                                System.exit(100);
                            });
                        }
                    } else {
                        throw new IllegalArgumentException();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class ThreadSend extends Thread{
        Board board;

        public ThreadSend(Board board){
            super();
            this.board = board;
        }

        @Override
        public void run(){
                super.run();
                Player.Color[][] states = new Player.Color[3][3];
                for(int x = 0; x < 3; x++){
                    for(int y = 0; y < 3; y++){
                        states[x][y] = board.getState(x,y);
                    }
                }
                while (true) {
                    Player.Color[][] newStates = gameBoard.getState();
                    for (int x = 0; x < 3; x++) {
                        for (int y = 0; y < 3; y++) {
                            if (!states[x][y].equals(newStates[x][y]) && newStates[x][y].equals(clientPlayer.getColor())) {
                                byte[] command = new byte[3];
                                if(gameBoard.getWinner() == Player.Color.EMPTY){
                                    command[0] = MOVE;
                                } else{
                                    command[0] = DECLARE_WINNING;
                                }
                                command[1] = (byte) x;
                                command[2] = (byte) y;
                                try {
                                    out.write(command);
                                    states[x][y] = gameBoard.getState(x, y);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    if(isInterrupted()){
                        try {
                            byte[] command = new byte[3];
                            command[0] = EXIT;
                            command[1] = EXIT;
                            out.write(command);
                            close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
        }
    }

    public void startNewGame(){
        try {
            byte[] command = new byte[3];
            command[0] = NEW_GAME;
            out.write(command);
            for (int i = 0; i < 3; i++) {
                command[i] = (byte) in.read();
            }
            if(command[0] == NEW_GAME) {
                gameBoard = new Board();
                clientPlayer = (command[1] == 0) ? new Player(Player.Color.O, gameBoard) : new Player(Player.Color.X, gameBoard);
                boardFX = new BoardFX(clientPlayer);
                MainApp.primaryStage.setScene(new Scene(boardFX, 460, 460));
                MainApp.primaryStage.sizeToScene();
                MainApp.primaryStage.show();
                isPlayerTurn.setValue(command[1] == 0); // najpierw kolko, potem krzyzyk
                threadRecive = new ThreadRecive();
                threadRecive.start();
                threadSend = new ThreadSend(gameBoard);
                threadSend.start();
            } else {
                // TODO: else means EXIT from the server before game started
                new ExceptionAlert("Server disconnected", "Playing is no longer possible. Try again later.").showAndWait();
                System.exit(100);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close(){
        try{
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GameController(){
        try{
            server = new Socket("192.168.1.105",3000);
            server.setReceiveBufferSize(3);
            server.setSendBufferSize(3);
            in = server.getInputStream();
            out = server.getOutputStream();
        } catch (IOException e) {
            new ExceptionAlert("Server disconnected", "Playing isn't possible right now. Try again later.").showAndWait();
            System.exit(100);
        }
    }
}
