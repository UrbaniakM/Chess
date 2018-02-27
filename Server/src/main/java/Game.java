import com.sun.org.apache.bcel.internal.generic.NEW;
import jdk.internal.util.xml.impl.Input;

import javax.xml.ws.Service;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Game extends Thread{
    private static final byte MOVE = 0;
    private static final byte DECLARE_WINNING = 1;
    private static final byte NEW_GAME = 2;
    private static final byte REMATCH = 3;
    private static final byte EXIT = 4;
    private static final byte NEW_OPPONENT = 5;

    private Client clientFirst;
    private Client clientSecond;


    private InputStream inFirst;
    private OutputStream outFirst;

    private InputStream inSecond;
    private OutputStream outSecond;

    public Game(Client first, Client second){
        try {
            clientFirst = first;
            inFirst = first.getSocket().getInputStream();
            outFirst = first.getSocket().getOutputStream();

            clientSecond = second;
            inSecond = second.getSocket().getInputStream();
            outSecond = second.getSocket().getOutputStream();
        } catch (IOException ex){
            ex.printStackTrace();
            System.out.println("Connection Error"); // TODO: disconnect client
        }
    }

    @Override
    public void run(){
        boolean rematchFirst = false;
        boolean rematchSecond = false;
        boolean sendFirst = false;
        boolean sendSecond = false;
        byte O = 0;
        byte X = 1;

        try {
            byte[] command = new byte[3];
            command[0] = NEW_GAME;
            command[1] = O;
            command[2] = 0;
            outFirst.write(command);
            command[1] = X;
            outSecond.write(command);

            while(!isInterrupted()) {
                Move move = null;
                if(inFirst.available()>0){
                    move = recive(inFirst);
                    if(move.wrong){
                        Client client = new Client(clientSecond.getSocket());
                        Server.removeClient(clientSecond);
                        Server.addClient(client);
                        client.start();
                        client = new Client(clientFirst.getSocket());
                        Server.removeClient(clientFirst);
                        Server.addClient(client);
                        client.wantsToPlay = true;
                        client.start();
                        break;
                    }
                    send(move.command, outSecond);
                    if (move.rematch) {
                        sendFirst = true;
                        rematchFirst = true;
                    } else if(move.end){
                        clientFirst.close();
                        Client client = new Client(clientSecond.getSocket());
                        Server.removeClient(clientSecond);
                        Server.addClient(client);
                        client.start();
                        break;
                    }
                }
                if(inSecond.available()>0){
                    move = recive(inSecond);
                    if(move.wrong){
                        Client client = new Client(clientSecond.getSocket());
                        Server.removeClient(clientSecond);
                        Server.addClient(client);
                        client.wantsToPlay = true;
                        client.start();
                        client = new Client(clientFirst.getSocket());
                        Server.removeClient(clientFirst);
                        Server.addClient(client);
                        client.start();
                        break;
                    }
                    send(move.command, outFirst);
                    if(move.rematch) {
                        sendSecond = true;
                        rematchSecond = true;
                        // TODO
                    }else if(move.end){
                        clientSecond.close();
                        Client client = new Client(clientFirst.getSocket());
                        Server.removeClient(clientFirst);
                        Server.addClient(client);
                        client.start();
                        break;
                    }
                }
                if(move != null && move.hasWon) {
                    if (O == 0) {
                        O = 1;
                        X = 0;
                    } else {
                        X = 1;
                        O = 0;
                    }
                }
                if(sendFirst == sendSecond && sendFirst == true){
                    if(rematchFirst == rematchSecond){
                        command[0] = NEW_GAME;
                        command[1] = O;
                        command[2] = 0;
                        outFirst.write(command);
                        command[1] = X;
                        outSecond.write(command);
                        sendFirst = false;
                        sendSecond = false;
                        rematchFirst = false;
                        rematchSecond = false;
                    }
                }
            }
            if(isInterrupted()){
                try {
                    byte[] exitCommand = new byte[3];
                    exitCommand[0] = EXIT;
                    outFirst.write(exitCommand);
                    outSecond.write(exitCommand);
                    Server.removeClient(clientFirst);
                    Server.removeClient(clientSecond);
                } catch (Throwable t){
                    // do nothing
                }
            }
        } catch (IOException ex){
            try {
                byte[] exitCommand = new byte[3];
                exitCommand[0] = EXIT;
                outFirst.write(exitCommand);
                outSecond.write(exitCommand);
                interrupt();
            } catch (Throwable t){
                // do nothing
            }
            //ex.printStackTrace();
            //System.out.println("Connection Error");
        }
    }

    private Move recive(InputStream in){
        try {
            byte[] command = new byte[3];
            for (int i = 0; i < 3; i++) {
                command[i] = (byte) in.read();
            }
            return new Move(command);
        } catch (IOException ex){
            ex.printStackTrace();
            System.out.println("Connection Error"); // TODO: disconnect client
        }
        return null;
    }

    private void send(byte[] command, OutputStream out){
        try {
            out.write(command);
        } catch (IOException ex){
            ex.printStackTrace();
            System.out.println("Connection Error"); // TODO: disconnect client
        }
    }

    private class Move{
        public boolean hasWon;
        public boolean end;
        public boolean rematch;
        public boolean wrong;
        public byte[] command;

        public Move(byte[] command){
            this.command = command;
            hasWon = (command[0] == DECLARE_WINNING);
            end = (command[0] == EXIT);
            rematch = (command[0] == REMATCH);
            wrong = (command[0] == NEW_GAME);
        }
    }
}
