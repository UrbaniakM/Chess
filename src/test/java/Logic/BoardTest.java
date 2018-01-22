package Logic;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BoardTest {
    Board board;

    @Before
    public void setUp(){
        board = new Board();
    }

    @Test
    public void creatingBoardTest(){
        for(int x = 0; x < 3; x++){
            for(int y = 0; y < 3; y++){
                Assert.assertEquals(Player.Color.EMPTY, board.getState(x,y));
            }
        }
    }

    @Test
    public void doMoveTest(){
        Move move1 = new Move(0,0, Player.Color.O);
        Move move2 = new Move(2,2, Player.Color.X);
        Move move3 = new Move(2,2, Player.Color.O);

        board.doMove(move1);
        Assert.assertEquals(Player.Color.O, board.getState(0,0));
        board.doMove(move2);
        Assert.assertEquals(Player.Color.X, board.getState(2,2));
        board.doMove(move3);
        Assert.assertEquals(Player.Color.X, board.getState(2,2));
    }
}
