package inf112.misc;

import com.badlogic.gdx.math.Vector2;
import inf112.gameBoard.Game;

import java.util.ArrayList;


public class Utils
{
    //TODO: Test findPath
    /**
     * finds a path in a straight line until its told to stop or encounters an obstacle
     *
     * @param visited - previously visited tiles
     * @param n - the number of steps it should search, if n is -1 it will run until it encounters an obstacle
     * @param direction - Which direction should it search
     * @param game - the game
     * @param currPos - the start position
     * @return - returns a list of all coordinates it could go to.
     */
    public static ArrayList<Vector2>  findPath(ArrayList<Vector2> visited, int n, Facing direction, Game game, Vector2 currPos)
    {
        //add current pos
        visited.add(currPos);
        if(n != -1)
           n--;
        //Check if its possible to go to the next tile and that n isn't zero
        if(game.canGo(currPos, currPos.add(direction)) && n != 0)
        {
            return findPath(visited, n, direction, game, currPos.add(direction));
        }
        return visited;
    }
}