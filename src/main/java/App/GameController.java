package App;

import GUI.BoardFX;
import Logic.Board;
import Logic.Move;
import Logic.Player;

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

    private Board gameBoard;
    private Player clientPlayer;

    private BoardFX boardFX;


    public void reciveCommand(){
        byte command[] = new byte[3];
        try{
            in.read(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(command[0] == RECIVE_MOVE){
            reciveMove(command);
        } else if(command[0] == 2){
            reciveMove(command);
            // TODO: check if winner
        } else if (command[0] == 6){
            startNewGame(command);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void declareWinner(){

    }

    public void confirmWinner(){

    }

    private void reciveMove(byte command[]){
        int x = command[1];
        int y = command[2];
        Move move = new Move(x,y,clientPlayer.getOpponent());
        gameBoard.doMove(move);
        boardFX.setFill(x,y,clientPlayer.getOpponent());
    }

    public void sendMove(){

    }

    public void searchForNewGame(){

    }

    private void startNewGame(byte command[]){
        gameBoard = new Board();
        clientPlayer = (command[1] == 0) ? new Player(Player.Color.O,gameBoard) : new Player(Player.Color.X,gameBoard);
        boardFX = new BoardFX(clientPlayer);
    }

    public void acceptRematch(){

    }

    public void declineRematch(){

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
        while(server == null) {
            try{
                server = new Socket("127.0.0.1",3000);
                in = server.getInputStream();
                out = server.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }
}
