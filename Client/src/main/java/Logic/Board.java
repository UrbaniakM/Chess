package Logic;

public class Board {
    private Player.Color[][] state;

    public Board(){
        state = new Player.Color[3][3];
        for(int x = 0; x < 3; x++){
            for(int y = 0; y < 3; y++){
                state[x][y] = Player.Color.EMPTY;
            }
        }
    }

    public Player.Color getState(int x, int y) {
        return state[x][y];
    }

    private void setState(int x, int y, Player.Color color){
        state[x][y] = color;
    }

    public void doMove(Move move){
        int x = move.getX();
        int y = move.getY();

        if(getState(x,y) == Player.Color.EMPTY){
            setState(x,y,move.getColor());
        } else { // TODO: invalid move - SEND INFO TO SERVER OR SOMETHING IN 'PLAYER' class

        }
    }
}
