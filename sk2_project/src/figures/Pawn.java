/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package figures;

import static figures.ChessPiece.PAWN;

/**
 *
 * @author mateusz
 */
public class Pawn extends Figure {
    public Pawn(String colour){
        super("pawn",colour);
        pieceType = PAWN;
        figureSymbol = "\u2659";
    }
    
    public Pawn(String colour, int col, int row){
        super("pawn",colour,col,row);
        pieceType = PAWN;
        figureSymbol = "\u2659";
    }
    
    @Override
    public boolean isMoveAllowed(int newCol, int newRow){
        if(newCol < 0 || newCol > 7){
            return false;
        }
        else if(newRow < 0 || newRow > 7){
            return false;
        }
        else if(newCol != xPosition){
            return false;
        }
        else if((yPosition == 6) && (newRow == 4)){
            return true;
        }
        else if((yPosition == 1) && (newRow == 3)){
            return true;
        }
        else if((startingY == 6) && (newRow == (yPosition - 1))){
            return true;
        }
        else if((startingY == 1) && (newRow == (yPosition + 1))){
            return true;
        }
        return false;
    } 
    
    public boolean isBeatingAllowed(int newCol, int newRow){ // BICIE W PRZELOCIE
        if((newCol == (xPosition + 1)) || (newCol == (xPosition - 1) )){
            if((startingY == 6) && (newRow != yPosition - 1)){
                return false;
            }
            if((startingY == 1) && (newRow != yPosition + 1)){
                return false;
            }
        }
        else {
            return false;
        }
        return true;
    }
}
