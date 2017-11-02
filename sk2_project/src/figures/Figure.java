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
    protected int startingX, startingY;
    protected int numberOfMoves = -1;
    protected ChessPiece pieceType;
    protected String figureSymbol;
    
    public Figure(String name, String colour){
        this.figureColour = colour;
        this.figureName = name;
    }
    
    public Figure(String name, String colour, int x, int y){
        this.figureColour = colour;
        this.figureName = name;
        this.startingX = x;
        this.startingY = y;
        this.numberOfMoves = 0;
    }
    
    public void beatFigure(){
        isBeaten = true;
    }
    
    public void setPosition(int x, int y){
        xPosition = x;
        yPosition = y;
        if(numberOfMoves == -1){
            startingX = x;
            startingY = y;
        }
        numberOfMoves++;
    }
    
    abstract public boolean isMoveAllowed(int newX, int newY);
    
    public String getColour(){
        return figureColour;
    }
    
    public ChessPiece getPieceType(){
        return this.pieceType;
    }
    
    public String getFigureSymbol(){
        return this.figureSymbol;
    }
}
