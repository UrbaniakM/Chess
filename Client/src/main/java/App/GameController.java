package App;

import GUI.BoardFX;
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

    private Thread threadRecive;
    private Thread threadSend;


    private final Task taskRecive = new Task<Void>() {
        @Override
        protected Void call() {
            while (true) {
                try {
                    byte[] command = new byte[3];
                    for (int i = 0; i < 3; i++) {
                        command[i] = (byte) in.read();
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
                        break;
                        //TODO: rematch / end / new game
                    } else {
                        throw new IllegalArgumentException();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    };

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
                                //if(!hasWon){
                                command[0] = MOVE;
                                //} else{
                                //command[0] = DECLARE_WINNING;// TODO: check if not winner before sending move
                                //}
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
                threadRecive = new Thread(taskRecive);
                threadRecive.setDaemon(true);
                threadRecive.start();
                threadSend = new ThreadSend(gameBoard);
                threadSend.start();
            } else {
                // TODO: else means EXIT from the other player before game started
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
            e.printStackTrace();
            System.exit(100);
        }
    }
}
