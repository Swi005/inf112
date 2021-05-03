package misc;

import cards.*;
import com.badlogic.gdx.math.Vector2;
import gameBoard.GameBoard;
import tiles.*;

import java.util.ArrayList;
import java.util.List;


public class Utils
{
    //TODO: Test findPath
    /**
     * finds a path in a straight line until its told to stop or encounters an obstacle
     *
     * @param visited - previously visited tiles
     * @param n - the number of steps it should search, if n is -1 it will run until it encounters an obstacle
     * @param direction - Which direction should it search
     * @param game - the inf112.game
     * @param currPos - the start position
     * @return - returns a list of all coordinates it could go to.
     */
    public static ArrayList<Vector2>  findPath(ArrayList<Vector2> visited, int n, Facing direction, GameBoard game, Vector2 currPos)
    {
        //add current pos
        visited.add(currPos);
        if(n != -1)
           n--;
        //Check if its possible to go to the next inf112.tile and that n isn't zero
        if(game.canGo(currPos, currPos.add(direction)) && n != 0)
        {
            return findPath(visited, n, direction, game, currPos.add(direction));
        }
        return visited;
    }

    /**
     * Checks if two positions are adjacent
     * @param u firstPos
     * @param v secondPos
     * @return - Boolean
     */
    public static boolean isAdjacent(Vector2 u, Vector2 v)
    {
        return (Math.abs(u.x-v.x) <2 && Math.abs(u.y - v.y) <2);
    }

    /**
     * Check if all threads in the list have exited
     * @param threads - Threads to check
     * @return - Boolean
     */
    public static boolean threadsHaveFinished(List<Thread> threads)
    {
        for (Thread t: threads) {
            if(t.isAlive())
                return false;
        }
        return true;
    }

    /**
     * Converts a tile ID to a tile
     * Used for reading a .tmx file
     * @param id -tileID
     * @return
     */
    public static ITile toTile(int id)
    {
        switch (id-1) {
            case 0:
                return new Pusher(new Facing("north"));
            case 1:
                return new Pusher(new Facing("east"));
            case 2:
                return new Pusher(new Facing("south"));
            case 3:
                return new Pusher(new Facing("west"));
            case 4:
                return new Floor();
            case 5:
                return new Hole();
            case 6:
                return new Repair();
            case 7:
                return new Wall(new Facing[]{new Facing("south"), new Facing("east")});
            case 8:
                return new Pusher(new Facing("north"));
            case 9:
                return new Pusher(new Facing("east"));
            case 10:
                return new Pusher(new Facing("south"));
            case 11:
                return new Pusher(new Facing("west"));
            case 14:
                return new Repair();
            case 15:
                return new Wall(new Facing[]{new Facing("north"), new Facing("east")});
            case 22:
                return new Wall(new Facing[]{new Facing("east")});
            case 23:
                return new Wall(new Facing[]{new Facing("north"), new Facing("west")});
            case 28:
                return new Wall(new Facing[]{new Facing("south")});
            case 29:
                return new Wall(new Facing[]{new Facing("west")});
            case 30:
                return new Wall(new Facing[]{new Facing("north")});
            case 31:
                return new Wall(new Facing[]{new Facing("south"), new Facing("west")});
            case 36:
                return new Laser(new Facing("north"), 1, new Facing[]{new Facing("south")});
            case 37:
                return new Laser(new Facing("east"), 1, new Facing[]{new Facing("west")});
            case 44:
                return new Laser(new Facing("south"), 1, new Facing[]{new Facing("north")});
            case 45:
                return new Laser(new Facing("west"), 1, new Facing[]{new Facing("east")});
            case 52://TODO: GEAR LOGIC
                return new Gear(); //LEFT
            case 53:
                return new Gear(); //RIGHT
            case 54:
                return new Flag(1);
            case 62:
                return new Flag(2);
            case 70:
                return new Flag(3);
            case 78:
                return new Flag(4);
            case 90:
                return new Hole();
            case 91:
                return new Hole();
            case 86:
                return new Laser(new Facing("north"), 2, new Facing[]{new Facing("south")});
            case 92:
                return new Laser(new Facing("east"), 1, new Facing[]{new Facing("west")});
            case 93:
                return new Laser(new Facing("south"), 1, new Facing[]{new Facing("north")});
            case 94:
                return new Laser(new Facing("west"), 1, new Facing[]{new Facing("east")});
            case 105:
                return new Hole();
            case 106:
                return new Hole();
            case 107:
                return new Hole();
            case 108:
                return new Hole();
            case 109:
                return new Hole();
            case 112:
                return new Hole();
            case 113:
                return new Hole();
            case 114:
                return new Hole();
            case 115:
                return new Hole();
            case 116:
                return new Hole();
            case 117:
                return new Hole();
            case 120:
                return new Spawnpoint(1-1);
            case 121:
                return new Spawnpoint(2-1);
            case 123:
                return new Spawnpoint(3-1);
            case 124:
                return new Spawnpoint(4-1);
            case 128:
                return new Spawnpoint(5-1);
            case 129:
                return new Spawnpoint(6-1);
            case 130:
                return new Spawnpoint(7-1);
            case 131:
                return new Spawnpoint(8-1);
            default:
                return new Floor();
        }
    }

    /**
     * Gets card based upon an int
     * Used for generating a card deck
     * @param i
     * @return
     */
    public static ICard getCard(int i)
    {
        switch (i) {
            case 0:
                return new Again();
            case 1:
                return new Move(1);
            case 2:
                return new Move(2);
            case 3:
                return new Move(3);
            case 4:
                return new Reverse(1);
            case 5:
                return new Reverse(2);
            case 6:
                return new Turn(new Facing("West"));
            case 7:
                return new Turn(new Facing("East"));
            default:
                return new Turn(new Facing("South"));
        }
    }
}
