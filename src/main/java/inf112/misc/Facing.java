package inf112.misc;

import com.badlogic.gdx.math.Vector2;

/**
 * Custom Vector2 for NW/SE orientation
 */

public class Facing extends Vector2
{
    public Facing(int NS, int EW)
    {
        super(NS,EW);
        //Following code is for checking that its a valid facing
        if(NS == EW)
        {
            throw new IllegalArgumentException("Invalid facing");

        }
        if(NS == 0)
        {
            if(!(EW == 1 || EW == -1))
            {
                throw new IllegalArgumentException("Argument is out of bounds");
            }
        }
        if(EW == 0)
        {
            if(!(NS == 1 || NS == -1))
            {
                throw new IllegalArgumentException("Argument is out of bounds");
            }

        }
        if(EW != 0 && NS != 0)
            throw new IllegalArgumentException("Invalid facing");
    }
    public Facing(String facing)
    {
        super();
        switch (facing.toLowerCase())
        {
            case "north":
                this.x = 1;
                this.y = 0;
                break;
            case "south":
                this.x = -1;
                this.y = 0;
                break;
            case "east":
                this.x = 0;
                this.y = 1;
                break;
            case "west":
                this.x = 0;
                this.y = -1;
                break;
            default:
                throw new IllegalArgumentException("Invalid facing");
        }
    }

    @Override
    public boolean equals(Object obj) {
        return (((Facing)obj).x == this.x && ((Facing)obj).y == this.y);
    }
}

