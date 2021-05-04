package tiles;

public class Flag implements ITile {

    private final int flagID;

    public Flag(int id) {
        this.flagID = id;
    }

    @Override
    public int priority() {
        return 9999999;
    }

    public int getFlagId() {
        return flagID;
    }

}
