import java.io.IOException;
import java.io.OutputStream;
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
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                mutex.acquireUninterruptibly();
                int i = 0;
                byte[] exitCommand = new byte[3];
                exitCommand[0] = Client.EXIT;
                try {
                    while (i < clients.size()) {
                        OutputStream out = clients.get(i).getSocket().getOutputStream();
                        out.write(exitCommand);
                        clients.get(i).close();
                        i++;
                    }
                }  catch (Throwable e){
                    // donothing
                }
                serverSocket = null;
                mutex.release();
            }
        });

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
        mutex.acquireUninterruptibly();
        clients.add(client);
        mutex.release();
    }

    public static void removeClient(Client client){
        mutex.acquireUninterruptibly();
        clients.remove(client);
        mutex.release();
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
                System.out.println("Interrupted thread");
            } finally {
                mutex.release();
            }
        }
    }

    private class HandleNewClients implements Runnable {
        private Socket clientSocket = null;

        public void run(){
            System.out.println("Start handling new connections...");
            while(serverSocket != null){
                try{
                    clientSocket = serverSocket.accept();
                    clientSocket.setReceiveBufferSize(3);
                    clientSocket.setSendBufferSize(3);
                    mutex.acquireUninterruptibly();
                    System.out.println("Connection established, client IP: " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
                    Client client = new Client(clientSocket);
                    client.start();
                    clients.add(client);
                } catch(IOException ex){
                    ex.printStackTrace();
                    System.out.println("Connection Error");
                } finally {
                    mutex.release();
                }
            }
        }
    }
}
