import javax.sql.rowset.serial.SerialBlob;
import java.io.*;
import java.net.Socket;

public class Client extends Thread{
    private Socket socket;

    static final byte NEW_GAME = 2;
    static final byte EXIT = 4;

    public boolean wantsToPlay = false;

    public Client(Socket socket){
        this.socket = socket;
    }

    public Socket getSocket(){
        return socket;
    }

    public void close(){
        try {
            System.out.println("Connection closed, client IP: " +
                    socket.getInetAddress() + ":" + socket.getPort());
            if(socket != null){
                socket.close();
            }
            Server.removeClient(this);
        } catch (IOException ex){
            System.out.println("Connection Error"); // TODO: disconnect client
        }
    }

    @Override
    public void run() {
        try {
            byte[] command = new byte[3];
            InputStream in = socket.getInputStream();
            in.read(command);
            if (command[0] == NEW_GAME) {
                wantsToPlay = true;
            } else if (command[0] == EXIT) {
                close();
            } else {
                System.out.println("Connection Error"); // TODO: disconnect client
                throw new IllegalArgumentException();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
