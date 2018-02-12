import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class Server {
    private ServerSocket serverSocket = null;
    private ArrayList<Client> clients = null;
    private Socket clientSocket = null;
    private static final Queue<Client> newGameQueue = new ArrayBlockingQueue<>(10);

    static final byte SEND_MOVE = 0;
    static final byte RECIVE_MOVE = 1;
    static final byte DECLARE_WINNING = 2;
    static final byte CONFIRM_OPPONNENT_AS_WINNER = 3;
    static final byte DECLINE_OPPONNENT_AS_WINNER = 4;
    static final byte SEARCH_FOR_NEW_GAME = 5;
    static final byte START_NEW_GAME = 6;
    static final byte ACCEPT_REMATCH = 7;
    static final byte DECLINE_REMATCH = 8;


    public Server(){
        try{
            serverSocket = new ServerSocket(3000);
            clients = new ArrayList<>();
            //TODO: przy wychodzeniu clienta z servera, usuniecie tych czekajacych na nowa gre z listy
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(100);
        }
    }

    public void run(){
        while(true){
            try{
                clientSocket = serverSocket.accept();
                System.out.println("Connection Established, client IP: " + clientSocket.getInetAddress());
                Client client = new Client(clientSocket);
                client.start();

            } catch(IOException ex){
                ex.printStackTrace();
                System.out.println("Connection Error");
            }
        }
    }

    public static final void addToQueue(Client client){
        newGameQueue.add(client);
    }

    public static final void removeFromQueue(Client client){
        newGameQueue.remove(client);
    }

    public static final Client retriveFromQueue(){
        return newGameQueue.poll();
    }
}
