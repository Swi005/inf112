package gameBoard;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;
import misc.Facing;
import tiles.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 */
public class GameBoard
{
    private ITile[][] board;
    private List<Robot> bots = new ArrayList<>();
    private HashMap<Integer,Vector2> flagPos = new HashMap<>();
    private List<Vector2> lasers = new ArrayList<>(10);
    private HashMap<Integer,Vector2> spawnpoints = new HashMap<Integer,Vector2>();

    public GameBoard(ITile[][] board)
    {
        this.board = board;

        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                if(board[x][y] instanceof Flag)
                    flagPos.put(((Flag)board[x][y]).getFlagId(),new Vector2(x,y));
                else if(board[x][y] instanceof Laser)
                    lasers.add(new Vector2(x,y));
                else if(board[x][y] instanceof Spawnpoint)
                    spawnpoints.put(((Spawnpoint) board[x][y]).ID, new Vector2(x,y));
            }
        }
    }
    /**
     * Enumerates over all tiles on the board and updates the tiles
     */
    public void updateBoardElements()
    {
        //TODO: Implement this
        //I.e check robots positions and if they are on something. if they are on something do the appropriate thing.
        Queue<Robot> robotQueue = new Queue<>();
        for (Robot bot: bots)
                robotQueue.addLast(bot);

        while (!robotQueue.isEmpty())
        {
            Robot bot = robotQueue.removeFirst();
            Vector2 botPos = bot.getRobotPos();

            if(board[(int) botPos.x][(int)botPos.y] instanceof Wall)
                continue;
            if(board[(int) botPos.x][(int)botPos.y] instanceof Floor)
                continue;
            if(board[(int) botPos.x][(int)botPos.y] instanceof Hole)
                bots.get(bots.indexOf(bot)).setHp(0);//TODO: Where does 0hp checks and bot respawn happen?
            if(board[(int) botPos.x][(int)botPos.y] instanceof Flag)
                continue;
            if(board[(int) botPos.x][(int)botPos.y] instanceof Repair)
                continue;
            if(board[(int) botPos.x][(int)botPos.y] instanceof Pusher)
                if(((Pusher)board[(int) botPos.x][(int)botPos.y]).isExtended) {
                    bot.setRobotPos(botPos.add(((Pusher) board[(int) botPos.x][(int) botPos.y]).getPushDirection()));
                    robotQueue.addLast(bot);
                }

            //TODO: Implement rest of tiles
        }
    }

    //TODO:test this
    public boolean canGo(Vector2 from, Vector2 too)//Make private maybe?
    {
        //Check that there isn't a bot in the 'too' location
        for (Robot b: bots) {
            if(b.getRobotPos() == too)
                return false;
        }

        if(board[(int)too.x][(int)too.y] instanceof Wall || board[(int)too.x][(int)too.y] instanceof Laser)
        {
            if(!Wall.canMoveToFromThis(from,too,(Wall)board[(int)too.x][(int)too.y]))
                return false;
        }
        if(board[(int)from.x][(int)from.y] instanceof Wall || board[(int)from.x][(int)from.y] instanceof Laser)
        {
            return Wall.canMoveToFromThis(from, too, (Wall) board[(int) from.x][(int) from.y]);
        }
        return true;
    }

    /**
     * Moves a bot to an adjacent location.
     * @param bot
     * @param too
     * @return
     */
    public boolean moveBot(Robot bot, Vector2 too)
    {
        if(this.canGo(bot.getRobotPos(),too))
        {
            bot.setRobotPos(too);
            return true;
        }
        return false;
    }
    public Robot addBot(int id)
    {
        Robot b = new Robot(id, 10, 0, spawnpoints.get(id), new Facing("north"));
        bots.add(b);
        return b;
    }

    public Robot getBot(int id)
    {
        for (Robot bot: bots) {
            if(bot.getId() == id)
                return bot;
        }
        return null;
    }

    public void updateBotStatus()
    {
        for (Robot bot: bots) {
            if(board[(int) bot.getRobotPos().x][(int) bot.getRobotPos().y] instanceof Repair)
            {
                bot.setSpawnPoint(bot.getRobotPos());
                bot.repair(1);
            }
            if(bot.getRobotPos() == flagPos.get(bot.getNextFlag()))
            {
                bot.setSpawnPoint(bot.getRobotPos());
                if(flagPos.containsKey(bot.getNextFlag()+1))
                {
                    bot.setNextFlag(bot.getNextFlag()+1);
                }
                else{
                    //TODO: ADD VICTORY STUFF
                }
            }
            if(bot.getHp() == 0){//if it has 0 hp set spawn it at its spawnpoitn
                bot.setRobotPos(bot.getSpawnPoint());
            }
        }
    }
    public HashMap<Integer, Vector2> getSpawnPos()
    {
        return spawnpoints;
    }
}