package tiles;

import misc.Facing;

public class Laser extends Wall{

    private Facing f;
    private int damage;

    public Laser(Facing f, int damage, Facing[] walls)
    {
        super(walls);
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
