package App;

import GUI.BoardFX;
import Logic.Board;
import Logic.Move;
import Logic.Player;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class GameController {
    private Socket server = null;
    private InputStream in = null;
    private OutputStream out = null;

    private final byte SEND_MOVE = 0;
    private final byte RECIVE_MOVE = 1;
    private final byte DECLARE_WINNING = 2;
    private final byte CONFIRM_OPPONNENT_AS_WINNER = 3;
    private final byte DECLINE_OPPONNENT_AS_WINNER = 4;
    private final byte SEARCH_FOR_NEW_GAME = 5;
    private final byte START_NEW_GAME = 6;
    private final byte ACCEPT_REMATCH = 7; // TODO
    private final byte DECLINE_REMATCH = 8; // TODO

    private Board gameBoard;
    private Player clientPlayer;

    private BoardFX boardFX;
    private Stage primaryStage;

    public void reciveCommand(){
        byte[] command = new byte[3];
        try{
            in.read(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(command[0] == RECIVE_MOVE){
            reciveMove(command);
        } else if(command[0] == DECLARE_WINNING){
            reciveMove(command);
            //if true then confirmWinner(); else declineWinner()// TODO: check if winner
        } else if (command[0] == START_NEW_GAME){
            startNewGame(command);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void confirmWinner(){
        try{
            out.write(CONFIRM_OPPONNENT_AS_WINNER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void declineWinner(){
        try{
            out.write(DECLINE_OPPONNENT_AS_WINNER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void reciveMove(byte command[]){
        int x = command[1];
        int y = command[2];
        Move move = new Move(x,y,clientPlayer.getOpponent());
        gameBoard.doMove(move);
        boardFX.setFill(x,y,clientPlayer.getOpponent());
    }

    public void sendMove(int x, int y){
        byte[] command = new byte[3];
        command[0] = SEND_MOVE;
        //command[0] = DECLARE_WINNING;// TODO: check if not winner before sending move
        command[1] = (byte)x;
        command[2] = (byte)y;
        try{
            out.write(command);
        } catch (IOException e) {
            e.printStackTrace();
        }

        reciveCommand();
    }

    public void searchForNewGame(Stage primaryStage){
        this.primaryStage = primaryStage;
        try{
            out.write(SEARCH_FOR_NEW_GAME);
        } catch (IOException e) {
            e.printStackTrace();
        }

        reciveCommand();
    }

    private void startNewGame(byte command[]){
        gameBoard = new Board();
        clientPlayer = (command[1] == 0) ? new Player(Player.Color.O,gameBoard) : new Player(Player.Color.X,gameBoard);
        boardFX = new BoardFX(clientPlayer,this);
        primaryStage.setScene(new Scene(boardFX,460,460));
        // TODO: if O then do the first move, else reciveCommand();
    }

    public void acceptRematch(){
        // TODO
    }

    public void declineRematch(){
        // TODO
    }

    public void close(){
        try{
            in.close();
            out.close();
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GameController(){
        try{
            server = new Socket("192.168.1.105",3000);
            in = server.getInputStream();
            out = server.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(100);
        }
    }
}
