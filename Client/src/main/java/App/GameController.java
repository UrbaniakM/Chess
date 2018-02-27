package App;

import GUI.*;
import Logic.Board;
import Logic.Move;
import Logic.Player;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.beans.value.WritableBooleanValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import sun.applet.Main;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.Semaphore;

public class GameController extends Application {
    private static Socket server = null;
    private static InputStream in = null;
    private static OutputStream out = null;

    private static final byte MOVE = 0;
    private static final byte DECLARE_WINNING = 1;
    public static final byte NEW_GAME = 2;
    public static final byte REMATCH = 3;
    public static final byte EXIT = 4;

    private static Board gameBoard;
    private static Player clientPlayer;

    private static BoardFX boardFX;

    public static Stage primaryStage;
    public static Alert endDialogAlert = null;

    public static void main(String[] args) {
        launch(args);
    }

    private void closeHook(){
        if(GameController.threadRecive != null){
            GameController.threadRecive.interrupt();
        }
        if(GameController.threadSend != null){
            GameController.threadSend.interrupt();
        } else {
            OutputStream out =  getOutputStream();
            byte[] exitCommand = new byte[3];
            exitCommand[0] = 4;
            exitCommand[1] = 4;
            try {
                out.write(exitCommand);
                out.close();
            } catch(Throwable t){
                //do nothing
            }
        }
    }

    @Override
    public void stop(){
        closeHook();
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setScene(new Scene(new SearchGameButton()));
        this.primaryStage.setResizable(false);
        this.primaryStage.sizeToScene();
        this.primaryStage.show();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                closeHook();
            }
        });
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

    public static OutputStream getOutputStream(){
        return out;
    }

    public static Thread threadRecive = null;
    public static Thread threadSend = null;


    private static class ThreadRecive extends Thread {
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
                    if(command[0] == NEW_GAME) {
                        Platform.runLater(() -> {
                            gameBoard.clearBoard();
                            clientPlayer = (command[1] == 0) ? new Player(Player.Color.O, gameBoard) : new Player(Player.Color.X, gameBoard);
                            boardFX = new BoardFX(clientPlayer);
                            boardFX.setDisable(false);
                            primaryStage.setScene(new Scene(boardFX, 460, 460));
                            primaryStage.sizeToScene();
                            primaryStage.show();
                            isPlayerTurn.setValue(command[1] == 0); // najpierw kolko, potem krzyzyk
                        });
                    }  else if (command[0] == MOVE) {
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
                        isPlayerTurn.setValue(false);
                        Platform.runLater( () -> {
                            new EndGameDialog("lost");
                            boardFX.setDisable(true);
                        });
                    } else if(command[0] == EXIT) {
                        if(command[1] == EXIT) { // means exit from another client
                            Platform.runLater( () -> {
                                new EndGameDialog("exit");
                                boardFX.setDisable(true);
                            });
                        } else { // means exit from server
                            Platform.runLater( () -> {
                                new ExceptionAlert("Server disconnected", "Playing is no longer possible. Try again later.").showAndWait();
                                System.exit(100);
                            });
                        }
                    } else if(command[0] == REMATCH) {
                        //donothing
                    } else {
                        throw new IllegalArgumentException();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class ThreadSend extends Thread{
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
                    Player.Color[][] newStates = board.getState();
                    for (int x = 0; x < 3; x++) {
                        for (int y = 0; y < 3; y++) {
                            if (!states[x][y].equals(newStates[x][y]) && newStates[x][y].equals(clientPlayer.getColor())) {
                                byte[] command = new byte[3];
                                if(board.getWinner() == Player.Color.EMPTY){
                                    command[0] = MOVE;
                                } else{
                                    command[0] = DECLARE_WINNING;
                                }
                                command[1] = (byte) x;
                                command[2] = (byte) y;
                                try {
                                    out.write(command);
                                    states[x][y] = board.getState(x, y);
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

    public static void sendCommand(byte typeOfCommand){
        try {
            byte[] command = new byte[3];
            command[0] = typeOfCommand;
            command[1] = EXIT;
            out.write(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void close(){
        try{
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GameController(){
        try{
            //server = new Socket("192.168.1.105",3000);
            server = new Socket("0.0.0.0",3000);
            server.setReceiveBufferSize(3);
            server.setSendBufferSize(3);
            in = server.getInputStream();
            out = server.getOutputStream();
            gameBoard = new Board();
            threadRecive = new ThreadRecive();
            threadRecive.start();
            threadSend = new ThreadSend(gameBoard);
            threadSend.start();
        } catch (IOException e) {
            new ExceptionAlert("Server disconnected", "Playing isn't possible right now. Try again later.").showAndWait();
            System.exit(100);
        }
    }
}
