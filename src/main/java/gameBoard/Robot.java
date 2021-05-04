package gameBoard;

import com.badlogic.gdx.math.Vector2;
import misc.Facing;

public class Robot
{
    private int id;
    private int hp;//Current hp of bot
    private int maxHp = 10;
    private int nextFlagID; //next flag it needs to visit
    private Vector2 currPos;
    private Facing facing;
    private Vector2 spawnPoint;


    public Robot(int id, int hp, int nextFlagID, Vector2 spawnPoint, Facing facing)
    {
        this.id = id;
        this.hp = hp;
        this.nextFlagID = nextFlagID;
        this.spawnPoint = spawnPoint.cpy();
        this.currPos = spawnPoint.cpy();
        this.facing = facing;
    }

    /**
     * Setter for the bots position
     * @param newPos
     */
    public void setRobotPos(Vector2 newPos)
    {
        this.currPos = newPos.cpy();
    }

    /**
     * Returns the robots current position
     * @return
     */
    public Vector2 getRobotPos()
    {
        return this.currPos.cpy();
    }

    /**
     * Sets the new next flag
     * @param id
     */
    public void setNextFlag(int id)
    {
        this.nextFlagID = id;
    }

    public int getNextFlag()
    {
        return this.nextFlagID;
    }
    public void doDamage(int dmg)
    {
        this.setHp(this.getHp()-dmg);
    }
    public void repair(int points)
    {
        this.setHp(this.getHp()+points);
    }
    public void setFacing(Facing facing) {
        this.facing = facing.copy();
    }
    public Facing getFacing()
    {
        return this.facing.copy();
    }
    public int getId() {
        return id;
    }

    public Vector2 getSpawnPoint() {
        return spawnPoint.cpy();
    }

    public void setSpawnPoint(Vector2 spawnPoint) {
        this.spawnPoint = spawnPoint.cpy();
    }

    public void setHp(int hp)
    {
        if(hp >= maxHp)
            this.hp = maxHp;
        if(hp<=0)
            this.hp = 0;
        else
            this.hp = hp;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }
}
