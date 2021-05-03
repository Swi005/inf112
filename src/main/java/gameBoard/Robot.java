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


    public Robot(int id, int hp, int nextFlagID, Vector2 currPos, Facing facing)
    {
        this.id = id;
        this.hp = hp;
        this.nextFlagID = nextFlagID;
        this.currPos = currPos;
        this.facing = facing;
    }

    /**
     * Setter for the bots position
     * @param newPos
     */
    public void setRobotPos(Vector2 newPos)
    {
        this.currPos = newPos;
    }

    /**
     * Returns the robots current position
     * @return
     */
    public Vector2 getRobotPos()
    {
        return this.currPos;
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
        if(this.hp >= dmg)
            this.hp -=dmg;
        else
            this.hp = 0;
    }
    public void repair(int points)
    {
        if(hp+points < maxHp)
            hp = maxHp;
        else
            hp += points;
    }
    public void setFacing(Facing facing) {
        this.facing = facing;
    }
    public Facing getFacing()
    {
        return this.facing;
    }
    public int getId() {
        return id;
    }

    public Vector2 getSpawnPoint() {
        return spawnPoint;
    }

    public void setSpawnPoint(Vector2 spawnPoint) {
        this.spawnPoint = spawnPoint;
    }

    public void setHp(int hp) {
        if(hp >= maxHp)
            this.hp = maxHp;
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
