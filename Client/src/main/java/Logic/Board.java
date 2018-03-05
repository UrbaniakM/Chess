package Logic;

import java.util.concurrent.Semaphore;

public class Board {
    private Player.Color[][] state;
    private Player.Color winner = Player.Color.EMPTY;
    private int numberOfEmptyField;

    public Board(){
        state = new Player.Color[3][3];
        for(int x = 0; x < 3; x++){
            for(int y = 0; y < 3; y++){
                state[x][y] = Player.Color.EMPTY;
            }
        }
        numberOfEmptyField = 9;
    }

    public synchronized Player.Color getWinner(){
        return winner;
    }

    public void clearBoard(){
        for(int x = 0; x < 3; x++){
            for(int y = 0; y < 3; y++){
                state[x][y] = Player.Color.EMPTY;
            }
        }

        winner = Player.Color.EMPTY;
    }

    public Player.Color[][] getState() {
        return state;
    }

    public Player.Color getState(int x, int y) {
        return state[x][y];
    }

    public boolean isDraw() {
        return numberOfEmptyField == 0 && getWinner() == Player.Color.EMPTY;
    }

    private synchronized void setState(int x, int y, Player.Color color) {
        state[x][y] = color;
        numberOfEmptyField--;
        for (int iter = 0; iter < 3; iter++) {
            if (state[iter][0] == state[iter][1] && state[iter][1] == state[iter][2] && state[iter][2] != Player.Color.EMPTY) {
                winner = color;
            }
            if (state[0][iter] == state[1][iter] && state[1][iter] == state[2][iter] && state[2][iter] != Player.Color.EMPTY) {
                winner = color;
            }
        }
        if (state[0][0] == state[1][1] && state[1][1] == state[2][2] && state[2][2] != Player.Color.EMPTY) {
            winner = color;
        }
        if (state[2][0] == state[1][1] && state[1][1] == state[0][2] && state[0][2] != Player.Color.EMPTY) {
            winner = color;
        }
    }

    public synchronized void doMove(Move move){
        int x = move.getX();
        int y = move.getY();
        if(getState(x,y) == Player.Color.EMPTY){
            setState(x,y,move.getColor());
        } else { // TODO: invalid move - SEND INFO TO SERVER OR SOMETHING IN 'PLAYER' class

        }
    }
}
