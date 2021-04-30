package inf112.tile;

import com.badlogic.gdx.math.Vector2;
import misc.Facing;
import tiles.Wall;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

class WallTest extends TestCase
{
    @Test
    public void BlockedByWallTest()
    {
        Vector2 orgn = new Vector2(0,0);
        Vector2 dest = new Vector2(1,0);//move 1 north
        Wall w = new Wall(new Facing[]{new Facing(1,0), new Facing(0,1)}); //Walls to the east and north

        assertFalse(w.canMoveToFromThis(dest, orgn,w));
    }
    @Test
    public void NotBlockedByWallTest()
    {
        Vector2 orgn = new Vector2(0,0);
        Vector2 dest = new Vector2(-1,0);//move 1 north
        Wall w = new Wall(new Facing[]{new Facing(1,0), new Facing(0,1)}); //Walls to the east and north

        assertTrue(w.canMoveToFromThis(dest, orgn,w));
    }
}