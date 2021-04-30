package inf112.misc;

import tiles.ITile;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardParserTest extends TestCase {

    @Test
    void parseBoardTest()
    {
        ITile[][] tst = misc.BoardParser.parseBoard("assets/tileMap.tmx");
        assertNotEquals(null,tst);
    }
}