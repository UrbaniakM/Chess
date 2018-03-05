import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Server {
    private static ServerSocket serverSocket = null;
    private static volatile ArrayList<Client> clients = null;
    private static volatile ArrayList<Game> games = null;
    private static Semaphore mutex = new Semaphore(1);
    private static Thread newClientsThread = null;

    public static void main(String[] args){
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                if(newClientsThread != null){
                    newClientsThread.interrupt();
                }
                int i = 0;
                try {
                    while (i < games.size()) {
                        games.get(i).interrupt();
                        //games.get(i).join();
                        i++;
                    }
                } catch (Throwable e){
                    // do nothing
                }
                mutex.acquireUninterruptibly();
                i = 0;
                byte[] exitCommand = new byte[3];
                exitCommand[0] = Client.EXIT;
                try {
                    while (i <= clients.size()) {
                        OutputStream out = clients.get(i).getSocket().getOutputStream();
                        out.write(exitCommand);
                        clients.get(i).close();
                        i++;
                    }
                }  catch (Throwable e){
                    // do nothing
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
            System.out.println("Server on address: " + serverSocket.getInetAddress() + ":3000");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(100);
        }
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
        //mutex.acquireUninterruptibly();
        Game game = new Game(clientFirst, clientSecond);
        clientFirst.wantsToPlay = false;
        clientSecond.wantsToPlay = false;
        games.add(game);
        game.start();
        //mutex.release();
    }

    public void run(){
        System.out.println("Server is running ...");
        newClientsThread = new HandleNewClients(); newClientsThread.start();
        while(true){
            try {
                Client first = null;
                Client second = null;
                int i = 0;
                mutex.acquire();
                while (i < clients.size()) {
                    Client currentClient = clients.get(i);
                    if (currentClient.wantsToPlay){// && currentClient != first) {
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
                    i++;
                }
                if (second != null) {
                    newGame(first, second);
                }
            } catch (InterruptedException ex){
                mutex.release();
                break;
            } finally {
                mutex.release();
            }
        }
    }

    private class HandleNewClients extends Thread {
        private Socket clientSocket = null;

        public void run(){
            System.out.println("Start handling new connections...");
            while(serverSocket != null && !isInterrupted()){
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
                    System.out.println("Connection error, couldn't establish connection");
                } finally {
                    mutex.release();
                }
            }
        }
    }
}
