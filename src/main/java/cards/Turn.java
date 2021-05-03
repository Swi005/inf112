package cards;

import misc.Facing;

public class Turn implements ICard
{
    public Facing turn;
    public Turn(Facing f)
    {
        this.turn = f;
    }
    @Override
    public String toString() {
        return "Turn: " + turn.toString();
    }
}
