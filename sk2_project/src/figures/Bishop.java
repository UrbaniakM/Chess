/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package figures;

import static figures.ChessPiece.BISHOP;

/**
 *
 * @author mateusz
 */
public class Bishop extends Figure {
    
    public Bishop(String colour){
        super("bishop", colour);
        pieceType = BISHOP;
        figureSymbol = "\u2657";
    }
    
    @Override
    public boolean isMoveAllowed(int newX, int newY){
        return true;
    } 
}