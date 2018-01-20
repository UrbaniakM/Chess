package GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    Scene mainScene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        mainScene = new Scene(new BoardFX(), 450, 450);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }
}
