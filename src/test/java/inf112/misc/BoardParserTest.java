package inf112.misc;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import tiles.ITile;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class BoardParserTest extends TestCase {

    @Test
    void parseBoardTest() {
        ITile[][] tst = misc.BoardParser.parseBoard("assets/tileMap.tmx");
        assertNotEquals(null, tst);
    }
}