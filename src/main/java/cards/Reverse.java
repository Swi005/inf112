package cards;

public class Reverse extends Move
{
    public Reverse(int move) {
        super(move);
        this.moveLen = -1;
    }

    @Override
    public String toString() {
        return "Reverse: " + (Math.abs(moveLen));
    }
}
