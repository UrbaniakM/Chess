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
        figureSymbol = "\u2658 ";
    }
    
    @Override
    public boolean isMoveAllowed(int newX, int newY){
        return true;
    } 
}
