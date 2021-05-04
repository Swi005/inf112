package gameBoard;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;
import misc.Facing;
import misc.Utils;
import tiles.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 */
public class GameBoard {
    private final HashMap<Integer, Vector2> flagPos = new HashMap<>();
    private final HashMap<Laser, Vector2> lasers = new HashMap<>();
    private final HashMap<Integer, Vector2> spawnpoints = new HashMap<>();
    private final ITile[][] board;
    private final List<Robot> bots = new ArrayList<>();

    public GameBoard(ITile[][] board) {
        this.board = board;

        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                if (board[x][y] instanceof Flag)
                    flagPos.put(((Flag) board[x][y]).getFlagId(), new Vector2(x, y));
                else if (board[x][y] instanceof Laser)
                    lasers.put((Laser) board[x][y], new Vector2(x, y));
                else if (board[x][y] instanceof Spawnpoint)
                    spawnpoints.put(((Spawnpoint) board[x][y]).ID, new Vector2(x, y));
            }
        }
    }

    /**
     * Enumerates over all tiles on the board and updates the tiles
     */
    public void updateBoardElements() {
        //I.e check robots positions and if they are on something. if they are on something do the appropriate thing.
        Queue<Robot> robotQueue = new Queue<>();
        for (Robot bot : bots)
            robotQueue.addLast(bot);

        while (!robotQueue.isEmpty()) {
            Robot bot = robotQueue.removeFirst();
            Vector2 botPos = bot.getRobotPos();

            if (!Utils.isWithinBounds(bot.getRobotPos(), board.length, board[0].length)) {
                bot.setRobotPos(bot.getSpawnPoint());
                continue;
            }

            if (board[(int) botPos.x][(int) botPos.y] instanceof Hole)
                bots.get(bots.indexOf(bot)).setRobotPos(bots.get(bots.indexOf(bot)).getSpawnPoint());
            if (board[(int) botPos.x][(int) botPos.y] instanceof Pusher)
                if (((Pusher) board[(int) botPos.x][(int) botPos.y]).isExtended) {
                    bot.setRobotPos(botPos.add(((Pusher) board[(int) botPos.x][(int) botPos.y]).getPushDirection()));
                    robotQueue.addLast(bot);
                }
        }
        for (Laser l : lasers.keySet()) {
            for (Robot b : bots) {
                if (Utils.findPath(new ArrayList<Vector2>(), -1, l.getFacing(), this, lasers.get(l)).contains(b.getRobotPos()))
                    b.doDamage(l.getDamage());
            }
        }
    }

    //TODO:test this
    public boolean canGo(Vector2 from, Vector2 too)//Make private maybe?
    {
        if (!Utils.isWithinBounds(too, this.getHeight(), this.getWidth()))
            return false;

        if (board[(int) too.x][(int) too.y].equals(null))
            return false;
        if (board[(int) too.x][(int) too.y] instanceof Wall || board[(int) too.x][(int) too.y] instanceof Laser) {
            if (!Wall.canMoveToFromThis(from, too, (Wall) board[(int) too.x][(int) too.y]))
                return false;
        }
        if (board[(int) from.x][(int) from.y] instanceof Wall || board[(int) from.x][(int) from.y] instanceof Laser) {
            return Wall.canMoveToFromThis(too, from, (Wall) board[(int) from.x][(int) from.y]);
        }
        return true;
    }

    /**
     * Func for quickly checking if any of the bots are out of bounds,
     * temporary fix since i couldnt find the source of the oob error.
     *
     * @return
     */
    public void checkOutOfBounds() {
        for (Robot bot : bots) {
            if (!Utils.isWithinBounds(bot.getRobotPos(), this.getHeight(), this.getWidth())) {
                System.out.println("Robot is out of bounds at x: " + bot.getRobotPos().x + " y: " + bot.getRobotPos().y);
                bot.setFacing(new Facing("North"));
                bot.setRobotPos(bot.getSpawnPoint());
            }
        }
    }

    public Robot addBot(int id) {
        Robot b = new Robot(id, 10, 1, spawnpoints.get(id).cpy(), new Facing("north"));
        bots.add(b);
        return b;
    }

    public Robot getBot(int id) {
        for (Robot bot : bots) {
            if (bot.getId() == id)
                return bot;
        }
        return null;
    }

    public void updateBotStatus() {
        for (Robot bot : bots) {
            if (!Utils.isWithinBounds(bot.getRobotPos(), board.length, board[0].length)) {
                bot.setRobotPos(bot.getSpawnPoint());
                continue;
            }
            if (board[(int) bot.getRobotPos().x][(int) bot.getRobotPos().y] instanceof Repair) {
                bot.setSpawnPoint(bot.getRobotPos());
                bot.repair(1);
            }
            if (bot.getRobotPos().equals(flagPos.get(bot.getNextFlag()))) {
                System.out.println("Bot visited flag: " + bot.getNextFlag());
                System.out.println("Next flag is : " + (bot.getNextFlag() + 1));
                bot.setSpawnPoint(bot.getRobotPos());
                if (flagPos.containsKey(bot.getNextFlag() + 1))
                    bot.setNextFlag(bot.getNextFlag() + 1);
                else
                    bot.setNextFlag(-1);
            }
            if (bot.getHp() == 0) {//if it has 0 hp set spawn it at its spawnpoint
                bot.setRobotPos(bot.getSpawnPoint());
                bot.setFacing(new Facing("North"));
            }
        }
    }

    public HashMap<Integer, Vector2> getSpawnPos() {
        return spawnpoints;
    }

    public boolean checkWin() {

        for (Robot b : bots) {
            if (b.getNextFlag() == -1) {
                System.out.println("Won");
                return true;
            }
        }
        return false;
    }

    public int getWidth() {
        return board[0].length;
    }

    public int getHeight() {
        return board.length;
    }
}
