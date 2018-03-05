package GUI;


import App.GameController;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;

public class EndGameDialog extends Alert { // TODO
    private ButtonType buttonNewGame = new ButtonType("New game");
    private ButtonType buttonRematch = new ButtonType("Rematch");
    private ButtonType buttonExit = new ButtonType("Exit");

    public EndGameDialog(String type){
        super(AlertType.CONFIRMATION);
        initOwner(GameController.primaryStage);
        initStyle(StageStyle.UNDECORATED);
        setTitle("Choose your option");
        setContentText(null);
        getButtonTypes().clear();
        getButtonTypes().add(buttonNewGame);
        getButtonTypes().add(buttonRematch);
        getButtonTypes().add(buttonExit);
        if(type.equals("won")) {  // TODO: + DRAW
            setHeaderText("You have won with your current opponent");
        } else if(type.equals("lost")){
            this.setHeaderText("You have lost with your current opponent");
        } else {
            this.setHeaderText("Your current opponent has disconnected.");
            getDialogPane().lookupButton(buttonRematch).setDisable(true);
        }
        showAndWait();

        ButtonType result = getResult();
        if(result.equals(buttonNewGame)){
            GameController.sendCommand(GameController.NEW_GAME);
        } else if(result.equals(buttonRematch)){
            GameController.sendCommand(GameController.REMATCH);
        } else{
            GameController.sendCommand(GameController.EXIT);
            System.exit(0);
        }
    }
}
