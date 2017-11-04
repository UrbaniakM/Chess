/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package figures;

import static figures.ChessPiece.KNIGHT;

/**
 *
 * @author mateusz
 */
public class Knight extends Figure {
    
    public Knight(String colour){
        super("knight", colour);
        pieceType = KNIGHT;
        figureSymbol = "\u2658";
    }
    
    public Knight(String colour, int col, int row){
        super("knight", colour, col, row);
        pieceType = KNIGHT;
        figureSymbol = "\u2658";
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
