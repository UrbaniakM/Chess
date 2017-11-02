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
    
    public King(String colour, int x, int y){
        super("king", colour, x, y);
        pieceType = KING;
        figureSymbol = "\u2654";
    }
    
    @Override
    public boolean isMoveAllowed(int newX, int newY){
        return true;
    } 
}