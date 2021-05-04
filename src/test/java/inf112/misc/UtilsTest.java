package inf112.misc;

import com.badlogic.gdx.math.Vector2;
import gameBoard.GameBoard;
import junit.framework.TestCase;
import misc.BoardParser;
import misc.Facing;
import misc.Utils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class UtilsTest extends TestCase {

    @Test
    void isAdjacent() {
        Vector2 u1 = new Vector2(0, 0);
        Vector2 v1 = new Vector2(1, 0);
        assertEquals(Utils.isAdjacent(u1, v1), true);
        Vector2 u2 = new Vector2(0, 0);
        Vector2 v2 = new Vector2(0, 1);
        assertEquals(Utils.isAdjacent(u2, v2), true);
        Vector2 u3 = new Vector2(0, 0);
        Vector2 v3 = new Vector2(1, 1);
        assertEquals(Utils.isAdjacent(u3, v3), true);
        Vector2 u4 = new Vector2(0, 0);
        Vector2 v4 = new Vector2(2, 0);
        assertEquals(Utils.isAdjacent(u4, v4), false);
        Vector2 u5 = new Vector2(0, 0);
        Vector2 v5 = new Vector2(1, 2);
        assertEquals(Utils.isAdjacent(u5, v5), false);
    }

    private GameBoard gameBoardGen() {
        return new GameBoard(BoardParser.parseBoard("assets/tileMap.tmx"));
    }

    @Test
    void findPathTestWhenInfinite() {
        Utils.findPath(new ArrayList<Vector2>(), -1, new Facing("West"), gameBoardGen(), new Vector2(4, 3));
    }
}