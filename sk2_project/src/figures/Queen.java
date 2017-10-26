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
public class Queen extends Figure {
    
    public Queen(String colour){
        super("queen", colour);
    }
    
    @Override
    public boolean isMoveAllowed(){
        return true;
    } 
}