package GUI;

import App.GameController;
import javafx.scene.control.Alert;
import javafx.scene.layout.Region;
import sun.applet.Main;

public class ExceptionAlert extends Alert {

    public ExceptionAlert(String title, String text){
        super(AlertType.ERROR, text);
        initOwner(GameController.primaryStage);
        this.setTitle(title);
        this.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
    }
}

