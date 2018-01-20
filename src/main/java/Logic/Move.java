package Logic;

public class Move {
    Player.Color color;
    private int x;
    private int y;

    public Move(int x, int y, Player.Color color){
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
