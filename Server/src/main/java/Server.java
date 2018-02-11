import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private ServerSocket server = null;
    private ArrayList<Client> clients = null;
    private Socket clientSocket = null;

    private static final byte SEND_MOVE = 0;
    private static final byte RECIVE_MOVE = 1;
    private static final byte DECLARE_WINNING = 2;
    private static final byte CONFIRM_OPPONNENT_AS_WINNER = 3;
    private static final byte DECLINE_OPPONNENT_AS_WINNER = 4;
    private static final byte SEARCH_FOR_NEW_GAME = 5;
    private static final byte START_NEW_GAME = 6;
    private static final byte ACCEPT_REMATCH = 7;
    private static final byte DECLINE_REMATCH = 8;


    public Server(){
        try{
            server = new ServerSocket(3000);
            clients = new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(100);
        }
    }

    public void run(){
        while(true){
            try{
                clientSocket = server.accept();
                System.out.println("Connection Established, client IP: " + clientSocket.getInetAddress());
                Client client = new Client(clientSocket);
                client.start();

            } catch(IOException ex){
                ex.printStackTrace();
                System.out.println("Connection Error");
            }
        }
    }
}
