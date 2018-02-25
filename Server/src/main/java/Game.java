import jdk.internal.util.xml.impl.Input;

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
        byte[] command = new byte[3];
        command[0] = NEW_GAME;
        command[1] = 0;
        command[2] = 0;
        try {
            outFirst.write(command);
            command[1] = 1;
            outSecond.write(command);

            while(true) {
                Move move;
                if(inFirst.available()>0){
                    move = recive(inFirst); // TODO: if not null
                    send(move.command, outSecond);
                    if (move.hasWon) {
                        // TODO
                    } else if(move.end){
                        // TODO
                    }
                }
                if(inSecond.available()>0){
                    move = recive(inSecond);  // TODO: if not null
                    send(move.command, outFirst);
                    if (move.hasWon) {
                        // TODO
                    } else if(move.end){
                        // TODO
                    }
                }
            }
        } catch (IOException ex){
            ex.printStackTrace();
            System.out.println("Connection Error"); // TODO: disconnect client
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
        public byte[] command;

        public Move(byte[] command){
            this.command = command;
            hasWon = (command[0] == DECLARE_WINNING);
            end = (command[0] == EXIT);
        }
    }
}
