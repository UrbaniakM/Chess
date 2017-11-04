/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package figures;

import static figures.ChessPiece.QUEEN;

/**
 *
 * @author mateusz
 */
public class Queen extends Figure {
    
    public Queen(String colour){
        super("queen", colour);
        pieceType = QUEEN;
        figureSymbol = "\u2655";
    }
    
    public Queen(String colour, int col, int row){
        super("queen", colour, col, row);
        pieceType = QUEEN;
        figureSymbol = "\u2655";
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