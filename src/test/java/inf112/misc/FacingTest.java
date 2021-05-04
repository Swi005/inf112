package inf112.misc;

import junit.framework.TestCase;
import misc.Facing;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class FacingTest extends TestCase {
    @Test
    public void testArgumentsAreEqual() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Facing(0, 0);
        });
    }

    @Test
    public void testArgumentsAreBothNotZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Facing(1, -1);
        });
    }

    @Test
    public void testArgumentOutOfBounds() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Facing(15, 0);
        });

    }

    @Test
    public void testInputAsString() {
        Facing t = new Facing(1, 0);
        Facing f = new Facing("north");

        assertEquals(f, t);
    }

    @Test
    public void testInputAsWonkyString() {
        Facing t = new Facing(1, 0);
        Facing f = new Facing("nOrTh");

        assertEquals(f, t);
    }

    @Test
    public void testTurn() {
        Facing right = new Facing(0, 1);
        Facing left = new Facing(0, -1);
        Facing back = new Facing(-1, 0);


        assertEquals(new Facing("South"), new Facing("east").turn(right));

        assertEquals(new Facing("East"), new Facing("south").turn(left));

        assertEquals(new Facing("north"), new Facing("south").turn(back));
    }
}