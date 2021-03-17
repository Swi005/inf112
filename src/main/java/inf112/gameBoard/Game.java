package inf112.gameBoard;

import com.badlogic.gdx.math.Vector2;
import inf112.tiles.ITile;

public class Game
{
    private ITile[][] board;
    private int height,width;
    //private ArrayList<Robot> bots = new ArrayList<>(); //TODO: Implement Bot class
    public Game(ITile[][] board, int height, int width)
    {
        this.board = board;
        this.width = width;
        this.height = height;
    }

    /**
     * Enumerates over all tiles on the board and updates the tiles
     */
    private void updateTilesOnBoard()
    {
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                //TODO: Switch-case here for each type of tile.
            }
        }
    }

    //TODO: Implement and test this
    public boolean canGo(Vector2 from, Vector2 too)
    {
        //TODO:check if bot in 'too'
        //TODO:Check if wall/other blocking object
        return true;
    }
}
