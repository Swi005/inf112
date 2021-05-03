package cards;

public class Move
{
    public int moveLen;
    public Move(int move)
    {
        this.moveLen = move;
    }

    @Override
    public String toString() {
        return "Move: " + moveLen;
    }
}
