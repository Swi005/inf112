package inf112.misc;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

class FacingTest extends TestCase
{
    @Test
    public void TestArgumentsAreEqual()
    {
        IllegalArgumentException ex = new IllegalArgumentException();
        try
        {
            Facing f = new Facing(0,0);
        }
        catch(IllegalArgumentException e)
        {
            ex = e;
        }
        assertEquals(ex.getMessage(), "Invalid facing");
    }
    @Test
    public void TestArgumentsAreBothNotZero()
    {
        IllegalArgumentException ex = new IllegalArgumentException();
        try
        {
            Facing f = new Facing(1,-1);
        }
        catch(IllegalArgumentException e)
        {
            ex = e;
        }
        assertEquals(ex.getMessage(), "Invalid facing");
    }
    @Test
    public void TestArgumentOutOfBounds()
    {
        IllegalArgumentException ex = new IllegalArgumentException();
        try
        {
            Facing f = new Facing(15,0);
        }
        catch(IllegalArgumentException e)
        {
            ex = e;
        }
        assertEquals(ex.getMessage(), "Argument is out of bounds");
    }
    @Test
    public void testInputAsString()
    {
        Facing t = new Facing(1,0);
        Facing f = new Facing("north");

        assertEquals(f,t);
    }
    @Test
    public void testInputAsWonkyString()
    {
        Facing t = new Facing(1,0);
        Facing f = new Facing("nOrTh");

        assertEquals(f,t);
    }
    @Test
    public void testTurn()
    {
        Facing right = new Facing(0,1);
        Facing left = new Facing(0,-1);
        Facing back = new Facing(-1,0);


        assertEquals(new Facing("South"), new Facing("east").turn(right));

        assertEquals(new Facing("East"), new Facing("south").turn(left));

        assertEquals(new Facing("north"), new Facing("south").turn(back));
    }
}