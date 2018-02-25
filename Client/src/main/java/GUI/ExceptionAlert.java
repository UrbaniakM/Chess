package GUI;

import javafx.scene.control.Alert;
import javafx.scene.layout.Region;
import sun.applet.Main;

public class ExceptionAlert extends Alert {

    public ExceptionAlert(String title, String text){
        super(AlertType.ERROR, text);
        initOwner(MainApp.primaryStage);
        this.setTitle(title);
        this.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
    }
}

