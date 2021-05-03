package cards;

import misc.Facing;

public class Turn
{
    public Facing turn;

    @Override
    public String toString() {
        return "Turn: " + turn.toString();
    }
}
