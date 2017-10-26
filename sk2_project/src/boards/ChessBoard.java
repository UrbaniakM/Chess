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
    }
    
    @Override
    protected void fillBoard(){
        
        for(int i = 0; i < 8; i++){
            whitePieces[i] = new Pawn("white");
            whitePieces[i].setPosition(1,i);
            blackPieces[i] = new Pawn("black");
            blackPieces[i].setPosition(7,i);
        }
    }

}
