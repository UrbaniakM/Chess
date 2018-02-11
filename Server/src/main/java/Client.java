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

    public void start(){

    }
}
