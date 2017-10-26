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
    
    
    public void printBoard(){
        for(int row = 0; row < width; row++){
            for(int col = 0; col < height; col++){
                if(boardMatrix[row][col] == null){
                    System.out.print("0");
                }
                else {
                    System.out.print(boardMatrix[row][col].symbol);
                }
            }
            System.out.println("");
        }
    }
    
}
