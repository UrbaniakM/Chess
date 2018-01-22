package GUI;

import App.GameController;
import Logic.Player;
import javafx.scene.layout.FlowPane;

public class BoardFX extends FlowPane{
    private SignFX state[][];

    public BoardFX(Player player, GameController gameController){
        super(5,5);
        state = new SignFX[3][3];
        for(int x = 0; x < 3; x++){
            for(int y = 0; y < 3; y++){
                state[x][y] = new SignFX(gameController, player, x, y);
                this.getChildren().add(state[x][y]);
            }
        }
    }

    public void setFill(int x, int y, Player.Color color){
        if(color == null){
            throw new IllegalArgumentException();
        }
        state[x][y].setFill(color);
    }
}
