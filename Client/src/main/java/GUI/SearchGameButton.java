package GUI;

import App.GameController;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SearchGameButton extends Button {
    public SearchGameButton(GameController gameController, Stage primaryStage){
        super("Search for new game");
        this.setOnMouseClicked(event -> {
            gameController.searchForNewGame(primaryStage);
            // TODO: pop alert informing about searching new game
        });
    }
}
