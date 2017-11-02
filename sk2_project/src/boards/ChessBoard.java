/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boards;

import figures.Figure;
import figures.Pawn;

/**
 *
 * @author mateusz
 */
public class ChessBoard extends Board {
    protected Figure whitePieces[];
    protected Figure blackPieces[];
    
    public ChessBoard(){
        super(8,8);
        whitePieces = new Figure[16];
        blackPieces = new Figure[16];
        fillBoard();
    }
    
    @Override
    protected void fillBoard(){
        for(int row = 0; row < height; row++){
            for(int col = 0; col < width; col++){
                boardMatrix[row][col] = null;
            }
        }
        for(int col = 0; col < width; col++){
            whitePieces[col] = new Pawn("white");
            whitePieces[col].setPosition(1,col);
            blackPieces[col] = new Pawn("black");
            blackPieces[col].setPosition(7,col);
            boardMatrix[1][col] = blackPieces[col];
            boardMatrix[6][col] = whitePieces[col];
        }
    }

}
