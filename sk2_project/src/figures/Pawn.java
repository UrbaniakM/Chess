/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package figures;

/**
 *
 * @author mateusz
 */
public class Pawn extends Figure {

    public Pawn(String colour){
        super("pawn",colour);
    }
    
    @Override
    public boolean isMoveAllowed(int newX, int newY){
        return true;
    } 
    
    public boolean isBeatingAllowed(int newX, int newY){
        return true;
    }
}
