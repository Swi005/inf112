package inf112.tiles;

import com.badlogic.gdx.math.Vector2;
import inf112.misc.Facing;

public class Floor implements ITile
{
    public Floor()
    {}

    @Override
    public int priority() {
        return 0;
    }

}
