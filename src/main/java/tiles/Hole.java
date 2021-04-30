package tiles;

import misc.Facing;

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
