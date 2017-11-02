/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boards;

import figures.Bishop;
import figures.Figure;
import figures.King;
import figures.Knight;
import figures.Pawn;
import figures.Queen;
import figures.Rook;

/**
 *
 * @author mateusz
 */
public class ChessBoard extends Board {
    protected Figure whitePieces[];
    protected Figure blackPieces[];
    
    public ChessBoard(){
        super(8,8);
        /*whitePieces = new Figure[16];
        blackPieces = new Figure[16];*/
        fillBoard();
    }
    
    /*@Override
    protected void fillBoard(){
        for(int row = 0; row < height; row++){
            for(int col = 0; col < width; col++){
                boardMatrix[row][col] = null;
            }
        }
        for(int col = 0; col < width; col++){
            whitePieces[col] = new Pawn("white",6,col);
            blackPieces[col] = new Pawn("black",1,col);
            boardMatrix[1][col] = blackPieces[col];
            boardMatrix[6][col] = whitePieces[col];
        }
        // rooks
        whitePieces[8] = new Rook("white", 7, 0);
        whitePieces[9] = new Rook("white", 7, 7);
        boardMatrix[7][0] = whitePieces[8];
        boardMatrix[7][7] = whitePieces[9];
        blackPieces[8] = new Rook("black",0,0);
        blackPieces[9] = new Rook("black",0,7);
        boardMatrix[0][0] = blackPieces[8];
        boardMatrix[0][7] = blackPieces[9];
        // knights
        whitePieces[10] = new Knight("white", 7,1);
        whitePieces[11] = new Knight("white", 7,6);
        boardMatrix[7][1] = whitePieces[10];
        boardMatrix[7][6] = whitePieces[11];
        blackPieces[10] = new Knight("black", 0,1);
        blackPieces[11] = new Knight("black", 0,6);
        boardMatrix[0][1] = blackPieces[10];
        boardMatrix[0][6] = blackPieces[11];
        // bishops
        whitePieces[12] = new Bishop("white", 7,2);
        whitePieces[13] = new Bishop("white", 7,5);
        boardMatrix[7][2] = whitePieces[12];
        boardMatrix[7][5] = whitePieces[13];
        blackPieces[12] = new Bishop("black", 0,2);
        blackPieces[13] = new Bishop("black", 0,5);
        boardMatrix[0][2] = blackPieces[12];
        boardMatrix[0][5] = blackPieces[13];
        // queens
        whitePieces[14] = new Queen("white", 7,3);
        boardMatrix[7][3] = whitePieces[14];
        blackPieces[14] = new Queen("black", 0,3);
        boardMatrix[0][3] = blackPieces[14];
        // kings
        whitePieces[15] = new King("white", 7,4);
        boardMatrix[7][4] = whitePieces[15];
        blackPieces[15] = new King("black", 0,4);
        boardMatrix[0][4] = blackPieces[15];
    }*/
    @Override
    protected void fillBoard(){
        for(int row = 0; row < height; row++){
            for(int col = 0; col < width; col++){
                boardMatrix[row][col] = null;
            }
        }
        for(int col = 0; col < width; col++){
            boardMatrix[1][col] = new Pawn("black",1,col);
            boardMatrix[6][col] = new Pawn("white",6,col);
        }
        // rooks
        boardMatrix[7][0] = new Rook("white", 7, 0);
        boardMatrix[7][7] = new Rook("white", 7, 7);
        boardMatrix[0][0] = new Rook("black",0,0);
        boardMatrix[0][7] = new Rook("black",0,7);
        // knights
        boardMatrix[7][1] = new Knight("white", 7,1);
        boardMatrix[7][6] = new Knight("white", 7,6);
        boardMatrix[0][1] = new Knight("black", 0,1);
        boardMatrix[0][6] = new Knight("black", 0,6);
        // bishops
        boardMatrix[7][2] = new Bishop("white", 7,2);
        boardMatrix[7][5] = new Bishop("white", 7,5);
        boardMatrix[0][2] = new Bishop("black", 0,2);
        boardMatrix[0][5] = new Bishop("black", 0,5);
        // queens
        boardMatrix[7][3] = new Queen("white", 7,3);
        boardMatrix[0][3] = new Queen("black", 0,3);
        // kings
        boardMatrix[7][4] = new King("white", 7,4);
        boardMatrix[0][4] = new King("black", 0,4);
    }
}
