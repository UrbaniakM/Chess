package GUI;

import App.GameController;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SearchGameButton extends Button {
    public SearchGameButton(){
        super("Search for new game");
        this.setOnMouseClicked(event -> {
            GameController.sendCommand(GameController.NEW_GAME);
            // TODO: pop alert informing about searching new game
        });
    }
}
