import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client extends Thread{
    private Socket socket;
    private InputStream in = null;
    private OutputStream out = null;

    public Client(Socket socket){
        this.socket = socket;
        try{
            in = socket.getInputStream();
            out = socket.getOutputStream();
        } catch (IOException ex){
            ex.printStackTrace();
            System.out.println("Connection Error");
        }

    }

    public void reciveCommand(){
        byte[] command = new byte[3];
        try{
            in.read(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(command[0] == Server.RECIVE_MOVE){
            reciveMove(command);
        } else if(command[0] == Server.DECLARE_WINNING){
            reciveMove(command);
            //if true then confirmWinner(); else declineWinner()// TODO: check if winner
        } else if (command[0] == Server.SEARCH_FOR_NEW_GAME){
            startNewGame(command);
        } else {
            throw new IllegalArgumentException();
        }
    }


    public void start(){
        while(true){
            reciveCommand();
        }
    }
}
