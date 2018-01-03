package figures;

import static figures.ChessPiece.BISHOP;

public class Bishop extends Figure {
    
    public Bishop(String colour){
        super("bishop", colour);
        pieceType = BISHOP;
        figureSymbol = "\u2657";
    }
    
    public Bishop(String colour, int col, int row){
        super("bishop", colour, col,row);
        pieceType = BISHOP;
        figureSymbol = "\u2657";
    }
    
    @Override
    public boolean isMoveAllowed(int newCol, int newRow){
        if(newCol < 0 || newCol > 7){
            return false;
        }
        else if(newRow < 0 || newRow > 7){
            return false;
        }
        return true;
    } 
}