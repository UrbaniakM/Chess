package GUI;


import Logic.Player;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class SignFX extends StackPane{
    private Rectangle tile;
    private Color rectangleFill;
    private Text text;

    public SignFX() {
        tile = new Rectangle(150,150);
        rectangleFill = Color.WHITE;
        tile.setFill(rectangleFill);

        this.getChildren().add(tile);

        text = new Text("");
        this.getChildren().add(text);
    }

    public void setFill(Player.Color color){
        rectangleFill = (color == Player.Color.O) ? Color.rgb(255,255,102) : Color.rgb(153,255,255);
        String signText = (color == Player.Color.O) ? "O" : "X";
        text.setText(signText);
        tile.setFill(rectangleFill);
    }
}
