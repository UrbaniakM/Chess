/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game_engines;

import boards.ChessBoard;
import figures.ChessPiece;
import static figures.ChessPiece.PAWN;
import figures.Figure;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author mateusz
 */
public class Chess extends Application{
    protected ChessBoard chessBoard = new ChessBoard();
    private final int TILE_SIZE = 100;
    private final int SYMBOL_SIZE = 50;
    
    private Group tilesGroup = new Group();
    private Group piecesGroup = new Group();
    
    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(createBoard());
        primaryStage.setTitle("ChessBoard - Mateusz Urbaniak");
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    private Parent createBoard(){
        Pane root = new Pane();
        root.setPrefSize(chessBoard.getWidth() * TILE_SIZE, chessBoard.getHeight()*TILE_SIZE);
        root.getChildren().addAll(tilesGroup,piecesGroup);
        for(int row = 0; row < chessBoard.getHeight(); row++) {
            for(int col = 0; col < chessBoard.getWidth(); col++) {
                Tile tile = new Tile((row+col)%2 == 0,col,row);
                
                tilesGroup.getChildren().add(tile);
                
                if(chessBoard.getFigure(col,row) != null){
                    Piece piece = makePiece(chessBoard.getFigure(col,row),col,row);
                    piecesGroup.getChildren().add(piece);
                }
            }
        }
        
        return root;
    }
    
    private int calculatePosition(double pixel){
        return (int)(pixel + TILE_SIZE / 2) / TILE_SIZE;
    }
    
    private boolean tryMove(Piece piece, int newX, int newY){
        if(piece.getPieceType() != PAWN) {
            if(piece.getFigure().isMoveAllowed(newX, newY)){
                piece.getFigure().setPosition(newX,newY);
                chessBoard.setFigure(piece.getFigure(),newX,newY);
            }
        }
        else {
            return false;
        }
        return false;
    }
    
    public Piece makePiece(Figure fig, int y, int x){
            Piece piece = new Piece(fig,y,x);
            
            piece.setOnMouseReleased(e -> {
                int newX = calculatePosition(piece.getLayoutX());
                int newY = calculatePosition(piece.getLayoutY());
                tryMove(piece, newX, newY); // if wrong move then player has another try - check if tryMove == false
            });
            
            return piece;
        }
    
    private class Piece extends StackPane{
        private ChessPiece pieceType;
        private int x,y;
        private Color pieceColor;
        private Color symbolColor;
        private Figure figure;
        
        public void movePiece(int newX, int newY){
            this.x = newX;
            this.y = newY;
            figure.setPosition(newX, newY);
        }
        
        public Figure getFigure(){
            return this.figure;
        }
        
        public ChessPiece getPieceType(){
            return this.pieceType;
        }
        
        public Piece(Figure fig, int y, int x){
            this.x = x;
            this.y = y;
            relocate(x * TILE_SIZE, y * TILE_SIZE);
            figure = fig;
            pieceColor = figure.getColour() == "white" ? Color.WHITE : Color.BLACK;
            symbolColor = figure.getColour() == "white" ? Color.BLACK : Color.WHITE;
            pieceType = figure.getPieceType();
            Ellipse pieceBackground = new Ellipse(TILE_SIZE * 0.3125,TILE_SIZE * 0.3125);
            pieceBackground.setFill(pieceColor);
            pieceBackground.setStroke(Color.BLACK);
            pieceBackground.setStrokeWidth(TILE_SIZE * 0.03);
            pieceBackground.setTranslateX((TILE_SIZE - TILE_SIZE * 0.3125 * 2) / 2); // center the piece
            pieceBackground.setTranslateY((TILE_SIZE - TILE_SIZE * 0.3125 * 2) / 2); // center the piece
            
            Text pieceSymbol = new Text(figure.getFigureSymbol());
            pieceSymbol.setFont(new Font(SYMBOL_SIZE));
            pieceSymbol.setFill(symbolColor);
            pieceSymbol.setTranslateX(TILE_SIZE / 5.5); // center the piece's symbol
            pieceSymbol.setTranslateY(TILE_SIZE / 5.5); // center the piece's symbol

            getChildren().addAll(pieceBackground, pieceSymbol);  
        }
    }
    
    private class Tile extends Rectangle{
        
        public Tile(boolean light, int y, int x){
            setWidth(TILE_SIZE);
            setHeight(TILE_SIZE);
            relocate(x * TILE_SIZE, y * TILE_SIZE);
            setFill(light ? Color.valueOf("FFE5CC") : Color.BROWN);
        }
    }
}
