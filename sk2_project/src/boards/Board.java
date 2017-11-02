/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boards;

import figures.Figure;

/**
 *
 * @author mateusz
 */
public abstract class Board {
    final protected int width, height;
    protected Figure[][] boardMatrix;
    
    public Board(int width, int height){
        this.width = width;
        this.height = height;
        boardMatrix = new Figure[width][height];
    }
    
    abstract protected void fillBoard();
    
    public Figure getFigure(int x, int y){
        return boardMatrix[x][y];
    }
    
    public void setFigure(Figure fig, int x, int y){
        boardMatrix[x][y] = fig;
    }
    
    public int getWidth(){
        return this.width;
    }
    
    public int getHeight(){
        return this.height;
    }
}
