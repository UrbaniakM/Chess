/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package figures;

import static figures.ChessPiece.ROOK;

/**
 *
 * @author mateusz
 */
public class Rook extends Figure {
    
    public Rook(String colour){
        super("rook", colour);
        pieceType = ROOK;
        figureSymbol = "\u2656";
    }
    
    public Rook(String colour, int x, int y){
        super("rook", colour,x,y);
        pieceType = ROOK;
        figureSymbol = "\u2656";
    }
    
    @Override
    public boolean isMoveAllowed(int newX, int newY){
        return true;
    } 
}
