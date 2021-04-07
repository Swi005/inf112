package inf112.tiles;

import inf112.misc.Facing;

public class Laser implements ITile{

    private Facing f;
    private int damage;

    public Laser(Facing f, int damage)
    {
        this.f = f;
        this.damage = damage;
    }

    @Override
    public int priority() {
        return 0;
    }

    public Facing getFacing()
    {
        return this.f;
    }

    public int getDamage()
    {
        return this.damage;
    }
}
