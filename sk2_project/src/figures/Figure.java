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
    protected String figureName;
    protected Boolean isBeaten = false;
    protected String figureColour;
    protected int xPosition, yPosition;
    public char symbol;// TEST
    
    public Figure(String name, String colour){
        this.figureColour = colour;
        this.figureName = name;
        if(name == "pawn"){
            this.symbol = 'p';
        }
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
