package GUI;


import App.GameController;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class EndGameDialog extends Alert { // TODO
    private ButtonType buttonNewGame = new ButtonType("New game");
    private ButtonType buttonRematch = new ButtonType("Rematch");
    private ButtonType buttonExit = new ButtonType("Exit");

    public EndGameDialog(String type){
        super(AlertType.CONFIRMATION);
        initOwner(GameController.primaryStage);
        setHeaderText("Choose your option");
        setContentText(null);
        getButtonTypes().clear();
        getButtonTypes().add(buttonNewGame);
        getButtonTypes().add(buttonRematch);
        getButtonTypes().add(buttonExit);
        if(type.equals("won")) {
            this.setTitle("You have won with your current opponent");
        } else if(type.equals("lost")){
            this.setTitle("You have lost with your current opponent");
        } else {
            this.setTitle("Your current opponent has disconnected.");
            getDialogPane().lookupButton(buttonRematch).setDisable(true);
        }
        showAndWait();

        ButtonType result = getResult();
        if(result.equals(buttonNewGame)){
            //TODO
        } else if(result.equals(buttonRematch)){
            //TODO
        } else{
            //TODO
        }
    }
}
