package boards;

import figures.Bishop;
import figures.Figure;
import figures.King;
import figures.Knight;
import figures.Pawn;
import figures.Queen;
import figures.Rook;


public class ChessBoard extends Board {
    
    public ChessBoard(){
        super(8,8);
        fillBoard();
    }
    
    
    public void checkBoard(){
        for(int row = 0; row < height; row++){
            for(int col = 0; col < width; col++){
                if(boardMatrix[col][row] == null){
                    continue;
                }
                if(boardMatrix[col][row].getX() != col)
                    System.out.print("Wrong col assign");
                if(boardMatrix[col][row].getY() != row)
                    System.out.print("Wrong row assign");
            }
        }
    }
    
    @Override
    final protected void fillBoard(){
        for(int row = 0; row < height; row++){
            for(int col = 0; col < width; col++){
                boardMatrix[col][row] = null;
            }
        }
        for(int col = 0; col < width; col++){
            boardMatrix[col][1] = new Pawn("black",col,1);
            boardMatrix[col][6] = new Pawn("white",col,6);
        }
        // rooks
        boardMatrix[0][7] = new Rook("white", 0, 7);
        boardMatrix[7][7] = new Rook("white", 7, 7);
        boardMatrix[0][0] = new Rook("black",0,0);
        boardMatrix[7][0] = new Rook("black",7,0);
        // knights
        boardMatrix[1][7] = new Knight("white", 1,7);
        boardMatrix[6][7] = new Knight("white", 6,7);
        boardMatrix[1][0] = new Knight("black", 1,0);
        boardMatrix[6][0] = new Knight("black", 6,0);
        // bishops
        boardMatrix[2][7] = new Bishop("white", 2,7);
        boardMatrix[5][7] = new Bishop("white", 5,7);
        boardMatrix[2][0] = new Bishop("black", 2,0);
        boardMatrix[5][0] = new Bishop("black", 5,0);
        // queens
        boardMatrix[3][7] = new Queen("white", 3,7);
        boardMatrix[3][0] = new Queen("black", 3,0);
        // kings
        boardMatrix[4][7] = new King("white", 4,7);
        boardMatrix[4][0] = new King("black", 4,0);
        checkBoard();
    }
}
