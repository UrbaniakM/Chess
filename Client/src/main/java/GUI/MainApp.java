package GUI;

import App.GameController;
import Logic.Board;
import Logic.Player;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.OutputStream;

public class MainApp extends Application {
    public static Stage primaryStage;
    public static GameController gameController = new GameController();

    public static void main(String[] args) {
        launch(args);
    }

    private void close(){
        if(GameController.threadRecive != null){
            GameController.threadRecive.interrupt();
        }
        if(GameController.threadSend != null){
            GameController.threadSend.interrupt();
        } else {
            OutputStream out =  gameController.getOutputStream();
            byte[] exitCommand = new byte[3];
            exitCommand[0] = 4;
            exitCommand[1] = 4;
            try {
                out.write(exitCommand);
                out.close();
            } catch(Throwable t){
                //do nothing
            }
        }
    }

    @Override
    public void stop(){
        close();
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setScene(new Scene(new SearchGameButton()));
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                close();
            }
        });
    }
}
