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
public class Knight extends Figure {
    
    public Knight(String colour){
        super("knight", colour);
    }
    
    @Override
    public boolean isMoveAllowed(){
        return true;
    } 
}
