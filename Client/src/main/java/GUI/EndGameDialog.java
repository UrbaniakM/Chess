package GUI;


import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class EndGameDialog extends Alert { // TODO
    private ButtonType buttonNewGame = new ButtonType("New game");
    private ButtonType buttonRematch = new ButtonType("Rematch");
    private ButtonType buttonExit = new ButtonType("Exit");

    public EndGameDialog(String type){
        super(AlertType.CONFIRMATION);
        if(type.equals("won")) {
            this.setTitle("You have won with your current opponent");
        } else if(type.equals("lost")){
            this.setTitle("You have lost with your current opponent");
        } else {
            this.setTitle("Your current opponent has disconnected.");
        }
        this.setHeaderText("Choose your option");
        this.setContentText(null);
        this.getButtonTypes().clear();
        this.getButtonTypes().add(buttonNewGame);
        if(type.equals("won") || type.equals("lost")){
            this.getButtonTypes().add(buttonRematch);
        }
        this.getButtonTypes().add(buttonExit);
        this.showAndWait();

        ButtonType result = getResult();
        if(result.equals(buttonNewGame)){

        } else if(result.equals(buttonRematch)){

        } else{

        }
    }
}
