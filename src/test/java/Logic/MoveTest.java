package Logic;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MoveTest {

    @Test(expected = IllegalArgumentException.class)
    public void creatingMoveInvalidArgumentsTest(){
        Move moveYOver = new Move(0,3, Player.Color.X);
        Move moveYUnder = new Move(0,-1, Player.Color.X);
        Move moveXOver = new Move(3,0, Player.Color.X);
        Move moveXUnder = new Move(-1,0, Player.Color.X);
        Move moveEmptyColor = new Move(0,0, Player.Color.EMPTY);
    }

}
