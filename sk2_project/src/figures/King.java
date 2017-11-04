/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package figures;

import static figures.ChessPiece.KING;

/**
 *
 * @author mateusz
 */
public class King extends Figure {
    
    public King(String colour){
        super("king", colour);
        pieceType = KING;
        figureSymbol = "\u2654";
    }
    
    public King(String colour, int col, int row){
        super("king", colour, col, row);
        pieceType = KING;
        figureSymbol = "\u2654";
    }
    
    private int abs(int value){
        if(value < 0){
            return -value;
        }
        return value;
    }
    
    @Override
    public boolean isMoveAllowed(int newCol, int newRow){
        if(newCol < 0 || newCol > 7){
            return false;
        }
        else if(newRow < 0 || newRow > 7){
            return false;
        }
        else if(abs(xPosition - newCol) > 1){
            return false;
        }
        else if(abs(yPosition - newRow) > 1){
            return false;
        }
        return true;
    } 
}