package inf112.tiles;

import com.badlogic.gdx.math.Vector2;
import inf112.misc.Facing;

public class Wall implements ITile
{
    Facing[] walls;
    public Wall(Facing[] walls)
    {
        this.walls = walls;
    }

    @Override
    public int priority() {
        return 1;
    }

    /**
     * Method for checking if a position is blocked by a wall
     *
     * @param pos - the position that you want to check if is blocked
     * @param wallPos - Position of the wall tile
     * @return - if its possible to move to/ from pos
     */
    public boolean canMoveToFromThis(Vector2 pos, Vector2 wallPos)
    {
        for (Facing f: walls)
        {
            if(wallPos.add(f).equals(pos))
                return false;
        }
        return true;
    }
}
