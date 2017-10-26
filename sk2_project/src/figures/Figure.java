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
    public String figureName;
    protected Boolean isBeaten = false;
    protected String figureColour;
    protected int xPosition, yPosition;
    protected char symbol;// TEST
    
    public Figure(String name, String colour){
        this.figureColour = colour;
        this.figureName = name;
    }
    
    public void beatFigure(){
        isBeaten = true;
    }
    
    public void setPosition(int x, int y){
        xPosition = x;
        yPosition = y;
    }
    
    abstract public boolean isMoveAllowed();
}
