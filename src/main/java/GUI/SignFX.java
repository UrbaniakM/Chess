package GUI;


import Logic.Player;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class SignFX extends StackPane{
    private Rectangle tile;
    private Color rectangleFill;
    private Text text;

    private Player player;
    private int x;
    private int y;

    public SignFX(Player player, int x, int y) {
        this.x = x;
        this.y = y;
        this.player = player;
        tile = new Rectangle(150,150);
        rectangleFill = Color.rgb(224,224,224);
        tile.setFill(rectangleFill);

        this.getChildren().add(tile);

        text = new Text("");
        text.setFont(Font.font("Verdana", 90));
        this.getChildren().add(text);

        this.setOnMouseClicked(event -> {
            this.player.doMove(this.x,this.y);
            this.setFill(this.player.getColor());
        });
    }

    public void setFill(Player.Color color){
        rectangleFill = (color == Player.Color.O) ? Color.rgb(255,255,102) : Color.rgb(153,255,255);
        String signText = (color == Player.Color.O) ? "O" : "X";
        text.setText(signText);
        tile.setFill(rectangleFill);
    }
}
