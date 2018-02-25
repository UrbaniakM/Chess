import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;

public class Server {
    private static ServerSocket serverSocket = null;
    private static volatile ArrayList<Client> clients = null;
    private static volatile ArrayList<Game> games = null;
    private static Semaphore mutex = new Semaphore(1);

    public static void main(String[] args){
        Server server = new Server();
        server.run();
    }

    public Server(){
        try{
            serverSocket = new ServerSocket(3000);
            clients = new ArrayList<>();
            games = new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(100);
        }
    }

    public static List<Client> getClients(){
        return clients;
    }

    public static void addClient(Client client){
        clients.add(client);
    }

    public static void removeClient(Client client){
        clients.remove(client);
    }

    public static void newGame(Client clientFirst, Client clientSecond){
        Game game = new Game(clientFirst, clientSecond);
        clientFirst.wantsToPlay = false;
        clientSecond.wantsToPlay = false;
        games.add(game);
        game.start();
    }

    public void run(){
        System.out.println("Server is running...");
        new Thread(new HandleNewClients()).start();
        while(true){
            try {
                Client first = null;
                Client second = null;
                int i = 0;
                mutex.acquire();
                while (i < clients.size()) {
                    if (clients.get(i).wantsToPlay) {
                        first = clients.get(i);
                        i++;
                        break;
                    }
                    i++;
                }
                while (i < clients.size()) {
                    if (clients.get(i).wantsToPlay) {
                        second = clients.get(i);
                        break;
                    }
                }
                if (second != null) {
                    newGame(first, second);
                }
            } catch (InterruptedException ex){
                ex.printStackTrace();
                System.out.println("Mutex error");
            } finally {
                mutex.release();
            }
        }
    }

    private class HandleNewClients implements Runnable {
        private Socket clientSocket = null;

        public void run(){
            System.out.println("Start handling new connections...");
            while(true){
                try{
                    clientSocket = serverSocket.accept();
                    clientSocket.setReceiveBufferSize(3);
                    clientSocket.setSendBufferSize(3);
                    mutex.acquire();
                    System.out.println("Connection Established, client IP: " + clientSocket.getInetAddress());
                    Client client = new Client(clientSocket);
                    client.start();
                    clients.add(client);
                } catch(IOException ex){
                    ex.printStackTrace();
                    System.out.println("Connection Error");
                } catch (InterruptedException ex){
                    ex.printStackTrace();
                    System.out.println("Mutex error");
                } finally {
                    mutex.release();
                }
            }
        }
    }
}
