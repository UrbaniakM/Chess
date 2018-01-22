package Logic;

import org.junit.Assert;
import org.junit.Test;

public class MoveTest {

    @Test(expected = IllegalArgumentException.class)
    public void creatingMoveInvalidArgumentsTest(){
        Move moveYOver = new Move(0,3, Player.Color.X);
        Move moveYUnder = new Move(0,-1, Player.Color.X);
        Move moveXOver = new Move(3,0, Player.Color.X);
        Move moveXUnder = new Move(-1,0, Player.Color.X);
        Move moveEmptyColor = new Move(0,0, Player.Color.EMPTY);
    }

    @Test
    public void creatingMoveTest(){
        Move move = new Move(1,2, Player.Color.X);
        Assert.assertEquals(1,move.getX());
        Assert.assertEquals(2,move.getY());
        Assert.assertEquals(Player.Color.X,move.getColor());
    }
}
