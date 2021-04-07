package inf112.tiles;

import com.badlogic.gdx.math.Vector2;
import inf112.misc.Facing;

public interface ITile
{

    /**
     * Returns the priority of the inf112.tile
     * higher value means that this inf112.tile will overlap other with lower priority
     * used for generating gameboard from gameMaps
     * @return - priority
     */
    int priority();

}