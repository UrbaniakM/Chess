package Logic;

import org.junit.Assert;
import org.junit.Test;

public class PlayerTest {

    @Test(expected = IllegalArgumentException.class)
    public void creatingPlayerInvalidArgumentsTest(){
        Player playerColorNULL = new Player(null, new Board());
        Player playerBoardNULL = new Player(Player.Color.X, null);
        Player playerColorEmpty = new Player(Player.Color.EMPTY, new Board());
    }

    @Test
    public void creatingPlayerTest(){
        Player player = new Player(Player.Color.X, new Board());
        Assert.assertEquals(Player.Color.X, player.getColor());
        Assert.assertEquals(Player.Color.O, player.getOpponent());
    }
}
