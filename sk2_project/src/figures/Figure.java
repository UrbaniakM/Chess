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
public abstract class Figure {
    String figureName;
    Boolean isBeaten = false;
    String figureColour;
    
    Figure(String colour, String name){
        this.figureColour = colour;
        this.figureName = name;
    }
    
    abstract public void beatFigure();
    abstract public boolean moveFigure();
}
