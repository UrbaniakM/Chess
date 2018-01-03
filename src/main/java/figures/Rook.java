package figures;

import static figures.ChessPiece.ROOK;

public class Rook extends Figure {
    
    public Rook(String colour){
        super("rook", colour);
        pieceType = ROOK;
        figureSymbol = "\u2656";
    }
    
    public Rook(String colour, int col, int row){
        super("rook", colour,col,row);
        pieceType = ROOK;
        figureSymbol = "\u2656";
    }
    
    @Override
    public boolean isMoveAllowed(int newCol, int newRow){
        if(newCol < 0 || newCol > 7){
            return false;
        }
        else if(newRow < 0 || newRow > 7){
            return false;
        }
        else if(newCol != xPosition && newRow == yPosition){
            return true;
        }
        else if(newCol == xPosition && newRow != yPosition){
            return true;
        }
        return false;
    } 
}
