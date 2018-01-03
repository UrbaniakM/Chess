package boards;
import figures.Figure;

public abstract class Board {
    final protected int width, height;
    protected Figure[][] boardMatrix;

    public Board(int width, int height){
        this.width = width;
        this.height = height;
        boardMatrix = new Figure[width][height];
    }

    public void printBoard(){
        for(int row = 0; row < 8; row++){
            for(int col = 0; col < 8; col++){
                if(boardMatrix[col][row] == null){
                    System.out.print("0");
                }
                else{
                    System.out.print("-");
                }
            }
            System.out.print("\n");
        }
    }

    abstract protected void fillBoard();

    public Figure getFigure(int col, int row){
        return boardMatrix[col][row];
    }

    public void setFigure(Figure fig, int x, int y, int oldX, int oldY){
        boardMatrix[x][y] = fig;
        boardMatrix[oldY][oldY] = null;
        fig.setPosition(x,y);
    }

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }
}

