package misc;

import inf112.misc.Facing;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

}