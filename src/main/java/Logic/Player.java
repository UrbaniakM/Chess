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

    public void doMove(Move move){
        board.doMove(move);
    }
}
