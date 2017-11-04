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
import figures.Pawn;

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
    
    private Piece piecesBoard[][] = new Piece[chessBoard.getWidth()][chessBoard.getHeight()];
    
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
        chessBoard.checkBoard();
        root.setPrefSize(chessBoard.getWidth() * TILE_SIZE, chessBoard.getHeight()*TILE_SIZE);
        root.getChildren().addAll(tilesGroup,piecesGroup);
        for(int row = 0; row < chessBoard.getHeight(); row++) {
            for(int col = 0; col < chessBoard.getWidth(); col++) {
                Tile tile = new Tile((row+col)%2 == 0,col,row);
                
                tilesGroup.getChildren().add(tile);
                
                if(chessBoard.getFigure(col,row) != null){
                    Piece piece = makePiece(chessBoard.getFigure(col,row),col,row);
                    piecesBoard[col][row] = piece;
                    piecesGroup.getChildren().add(piece);
                }
                else {
                    piecesBoard[col][row] = null;
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
                return true;
            }
        }
        else {
            Pawn pomFig = (Pawn)piece.getFigure();
            if(chessBoard.getFigure(newX,newY) == null){
                if(pomFig.isMoveAllowed(newX, newY)){
                    return true;
                }
            }
            else {
                if(pomFig.isBeatingAllowed(newX, newY)){
                    return true;
                }
            }
        }
        return false;
    }
    
    public void printPieces(){
        for(int row = 0; row < 8; row++){
            for(int col = 0; col < 8; col++){
                if(piecesBoard[col][row] == null){
                    System.out.print("0");
                }
                else{
                    System.out.print("-");
                }
            }
            System.out.print("\n");
        }
    }
    
    public Piece makePiece(Figure fig, int x, int y){
            Piece piece = new Piece(fig,x,y);
            
            piece.setOnMouseReleased(e -> {
                int newX = calculatePosition(piece.getLayoutX());
                int newY = calculatePosition(piece.getLayoutY());
                if(tryMove(piece, newX, newY)){ // if wrong move then player has another try - check if tryMove == false
                    piece.movePiece(newX,newY);
                }
                else {
                    piece.abortMove();
                }
            });
            
            return piece;
        }

    private void setPiece(int newX, int newY, int prevX, int prevY){
        piecesBoard[newX][newY] = piecesBoard[prevX][prevY];
        piecesBoard[prevX][prevY] = null;
    }
    
    private class Piece extends StackPane{
        private ChessPiece pieceType;
        private Color pieceColor;
        private Color symbolColor;
        private Figure figure;
        private double mouseX, mouseY;
        private double oldX, oldY;
        
        
        public void movePiece(int newX, int newY){
            oldX = newX * TILE_SIZE;
            oldY = newY * TILE_SIZE;
            int prevX = figure.getX(), prevY = figure.getY();
            if(piecesBoard[newX][newY] != null){
                if((prevX != newX) || (prevY != newY)){
                    piecesGroup.getChildren().remove(piecesBoard[newX][newY]);
                }
            }
            chessBoard.setFigure(figure,newX,newY, prevX, prevY);
            piecesBoard[newX][newY] = piecesBoard[prevX][prevY];
            piecesBoard[prevX][prevY] = null;
            //setPiece(newX,newY,prevX,prevY); - setPiece niepotrzebne raczej
            relocate(oldX, oldY);
            //printPieces();
            //System.out.println("");
            //chessBoard.printBoard(); // tutaj <<<---- cos nie tak z czyszczeniem, ALE DZIALA
        }
        
        public void abortMove(){
            relocate(oldX,oldY);
        }
        
        public Figure getFigure(){
            return this.figure;
        }
        
        public ChessPiece getPieceType(){
            return this.pieceType;
        }
        
        public Piece(Figure fig, int x, int y){
            relocate(x * TILE_SIZE, y * TILE_SIZE);
            oldX = x * TILE_SIZE;
            oldY = y * TILE_SIZE;
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
            
            setOnMousePressed(e -> {
                mouseX = e.getSceneX();
                mouseY = e.getSceneY();
            });
            
            setOnMouseDragged(e -> {
                relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);
            });
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
