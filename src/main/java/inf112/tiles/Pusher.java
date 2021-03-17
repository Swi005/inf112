package inf112.tiles;

import inf112.misc.Facing;

public class Pusher implements ITile
{
    Facing dir;
    public boolean isExtended = false;
    public Pusher(Facing dir)
    {
        this.dir = dir;

    }
    public Pusher(Facing dir, boolean isExtended)
    {
        this.dir = dir;
        this.isExtended = isExtended;
    }

    /**
     * Gets the direction the pusher pushes robots
     *
     * @return - direction
     */
    public Facing getPushDirection()
    {
        return this.dir;
    }
    @Override
    public int priority() {
        return 0;
    }
}
