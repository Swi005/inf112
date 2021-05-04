package cards;

import misc.Facing;

public class Turn implements ICard {
    public Facing turn;

    public Turn(Facing f) {
        this.turn = f;
    }

    private String convertFromCompassToDir(String dir) {
        switch (dir.toLowerCase()) {
            case "north":
                return "Ahead";
            case "west":
                return "Left";
            case "east":
                return "Right";
            case "south":
                return "Back";
            default:
                throw new IllegalArgumentException("Expected a compass direction, recieved: " + dir);
        }
    }

    @Override
    public String toString() {
        return "Turn: " + convertFromCompassToDir(turn.toString());
    }
}
