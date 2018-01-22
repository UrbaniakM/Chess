package Logic;

public class Player {
    public enum Color{
        X,
        O,
        EMPTY;
    }
    private Color color;
    private Board board;

    public Player(Color color, Board board){
        if(color == Color.EMPTY || board == null || color == null){
            throw new IllegalArgumentException();
        }
        this.color = color;
        this.board = board;
    }

    public Color getColor() { return color; }

    public Color getOpponent(){
        switch (color) {
            case X:
                return Color.O;
            case O:
                return Color.X;
            case EMPTY:
            default:
                throw new IllegalArgumentException("Color must be well defined");
        }
    }

    public void doMove(int x, int y){
        Move move = new Move(x,y,getColor());
        board.doMove(move);
    }
}
