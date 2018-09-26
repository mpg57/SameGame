
import org.junit.*;
import static org.junit.Assert.*;


/**
 * SameGameTest.java - a class for testing SameGame
 * @author  Mia Gorczyca
 * @version 1.0
 */


public class SameGameTest extends TestCase {
    @Test
    public void testCheckNeighbors() {
        SameGame sameGame = new SameGame() {
            sameGame.checkNeighbors();
        }
    }

    @Test
    public void testShiftDown() {
        SameGame sameGame = new SameGame() {
            sameGame.shiftDown();
        }
    }

    @Test
    public void testShiftLeft() {
        SameGame sameGame = new SameGame() {
            sameGame.shiftLeft();
        }
    }

    @Test
    public void testColumnEmpty() {
        SameGame sameGame = new SameGame() {
            sameGame.columnEmpty();
        }
    }
}
