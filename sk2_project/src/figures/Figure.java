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
    protected Boolean isBeaten = false; // raczej bedzie niepotrzebne
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
    
    public Figure(String name, String colour, int col, int row){
        this.figureColour = colour;
        this.figureName = name;
        this.startingX = col;
        this.startingY = row;
        this.xPosition = col;
        this.yPosition = row;
        this.numberOfMoves = 0;
    }
    
    public void beatFigure(){ // raczej bedzie niepotrzebne
        isBeaten = true;
    }
    
    public void setPosition(int col, int row){
        this.xPosition = col;
        this.yPosition = row;
        if(numberOfMoves == -1){
            this.startingX = col;
            this.startingY = row;
        }
        this.numberOfMoves++;
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
    
    public int getX(){
        return this.xPosition;
    }
    
    public int getY(){
        return this.yPosition;
    }
}
