package tiles;

public interface ITile {

    /**
     * Returns the priority of the inf112.tile
     * higher value means that this inf112.tile will overlap other with lower priority
     * used for generating gameboard from gameMaps
     *
     * @return - priority
     */
    int priority();

}