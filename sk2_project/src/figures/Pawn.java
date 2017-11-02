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
    
    @Override
    public boolean isMoveAllowed(int newX, int newY){
        if(newX != xPosition){
            return false;
        }
        else if((yPosition == 6) && (newY == 4)){
            yPosition = newY;
            return true;
        }
        else if((yPosition == 1) && (newY == 3)){
            yPosition = newY;
            return true;
        }
        else if((startingY == 6) && (newY != (yPosition - 1))){
            return false;
        }
        else if((startingY == 1) && (newY != (yPosition + 1))){
            return false;
        }
        yPosition = newY;
        return true;
    } 
    
    public boolean isBeatingAllowed(int newX, int newY){
        if((newX == (xPosition + 1)) || (newX == (xPosition - 1) )){
            if((startingY == 6) && (newY != yPosition - 1)){
                return false;
            }
            if((startingY == 1) && (newY != yPosition + 1)){
                return false;
            }
        }
        else {
            return false;
        }
        xPosition = newX;
        yPosition = newY;
        return true;
    }
}
