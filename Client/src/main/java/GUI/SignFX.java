package GUI;


import App.GameController;
import Logic.Player;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class SignFX extends StackPane{
    private Rectangle tile;
    private Color rectangleFill;
    private Text text;

    private BooleanProperty isEmpty = new BooleanPropertyBase() {
        @Override
        public Object getBean() {
            return null;
        }

        @Override
        public String getName() {
            return null;
        }
    };

    private Player player;
    private int x;
    private int y;

    public SignFX(Player player, int x, int y) {
        this.x = x;
        this.y = y;
        this.player = player;
        this.isEmpty.setValue(true);
        tile = new Rectangle(150,150);
        rectangleFill = Color.rgb(224,224,224);
        tile.setFill(rectangleFill);

        this.getChildren().add(tile);

        text = new Text("");
        text.setFont(Font.font("Verdana", 90));
        this.getChildren().add(text);

        this.disableProperty().bind(GameController.isPlayerTurn.not()
            .or(isEmpty.not())
        );

        this.setOnMouseClicked(event -> {
                this.player.doMove(this.x, this.y);
                this.setFill(this.player.getColor());
                this.isEmpty.setValue(false);
                GameController.isPlayerTurn.setValue(false);
                if(player.isWinner()){
                        new EndGameDialog("won");
                        this.getParent().setDisable(true);
                }
        });
    }

    public void setFill(Player.Color color){
        rectangleFill = (color == Player.Color.O) ? Color.rgb(255,255,102) : Color.rgb(153,255,255);
        String signText = (color == Player.Color.O) ? "O" : "X";
        text.setText(signText);
        tile.setFill(rectangleFill);
        this.isEmpty.set(false);
    }
}
