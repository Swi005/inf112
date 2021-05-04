package cards;

public class Reverse extends Move
{
    public Reverse(int move) {
        super(move);
    }

    @Override
    public String toString() {
        return "Reverse: " + (Math.abs(super.moveLen));
    }
}
