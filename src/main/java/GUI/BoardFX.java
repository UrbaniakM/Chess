package GUI;

import Logic.Board;
import javafx.scene.layout.FlowPane;

public class BoardFX extends FlowPane{
    private SignFX state[][];

    public BoardFX(Board board){
        super(5,5);
        state = new SignFX[3][3];
        for(int x = 0; x < 3; x++){
            for(int y = 0; y < 3; y++){
                state[x][y] = new SignFX(board);
                this.getChildren().add(state[x][y]);
            }
        }
    }
}
