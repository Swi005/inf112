package tiles;

public class Spawnpoint implements ITile{
    public int ID;
    public Spawnpoint (int id)
    {this.ID = id;}

    @Override
    public int priority() {
        return 0;
    }
}
