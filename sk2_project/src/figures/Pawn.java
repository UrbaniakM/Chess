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
    
    public Pawn(String colour, int x, int y){
        super("pawn",colour,x,y);
        pieceType = PAWN;
        figureSymbol = "\u2659";
    }
    
    @Override
    public boolean isMoveAllowed(int newX, int newY){
        /*if(newX != xPosition){
            return false;
        }
        else if((yPosition == 6) && (newY == 4)){
            System.out.println("aa");
            return true;
        }
        else if((yPosition == 1) && (newY == 3)){
            System.out.println("bb");
            return true;
        }
        else if((startingY == 6) && (newY == (yPosition - 1))){
            System.out.println("cc");
            return true;
        }
        else if((startingY == 1) && (newY == (yPosition + 1))){
            System.out.println("dd");
            return true;
        }
        System.out.println("j");
        return false;*/
        return true;
    } 
    
    public boolean isBeatingAllowed(int newX, int newY){
        /*if((newX == (xPosition + 1)) || (newX == (xPosition - 1) )){
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
        return true;*/
        return true;
    }
}
