package inf112.tiles;

import inf112.misc.Facing;

public class Hole implements ITile
{
    public Hole()
    {

    }
    @Override
    public int priority() {
        return 10;
    }
}
