/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boards;

/**
 *
 * @author mateusz
 */
public abstract class Board {
    protected int width, height;
    protected Byte[][] boardMatrix;
    
    Board(int width, int height){
        this.width = width;
        this.height = height;
        boardMatrix = new Byte[width][height];
        fillBoard();
    }
    
    abstract protected void fillBoard();
    
    
}
