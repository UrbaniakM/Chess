package Logic;

public class Move {
    Player.Color color;
    private int x;
    private int y;

    public Move(int x, int y, Player.Color color){
        if(x < 0 || x > 2 || y < 0 || y > 2 || color == Player.Color.EMPTY || color == null){
            throw new IllegalArgumentException();
        }
        this.color = color;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Player.Color getColor() {
        return color;
    }
}
