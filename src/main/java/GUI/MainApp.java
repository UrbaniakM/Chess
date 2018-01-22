package GUI;

import App.GameController;
import Logic.Board;
import Logic.Player;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    Scene mainScene;
    private Board gameBoard;
    private Player player;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GameController gameController = new GameController();

        //mainScene = new Scene(new BoardFX(player), 460, 460);
        mainScene = new Scene(new SearchGameButton(gameController,primaryStage));
        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();
    }


}
