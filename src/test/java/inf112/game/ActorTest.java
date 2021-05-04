package inf112.game;

import cards.ICard;
import gameBoard.Actor;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class TestCard implements ICard {
    public int id;

    public TestCard(int id) {
        this.id = id;
    }
}

class ActorTest extends TestCase {
    @Test
    void getAvailableCards() {
        Actor a = new Actor(0);
        HashSet<ICard> cards = new HashSet<>();
        TestCard t0 = new TestCard(0);
        a.addCard(t0);
        cards.add(t0);
        TestCard t1 = new TestCard(1);
        a.addCard(t1);
        cards.add(t1);
        TestCard t2 = new TestCard(2);
        a.addCard(t2);
        cards.add(t2);
        TestCard t3 = new TestCard(3);
        a.addCard(t3);
        cards.add(t3);

        assertEquals(cards, a.getAvailableCards());
    }

    @Test
    void addCard() {
        Actor a = new Actor(0);
        a.addCard(new TestCard(0));
        a.addCard(new TestCard(1));
        a.addCard(new TestCard(2));
        a.addCard(new TestCard(3));

        assertEquals(a.getAvailableCards().size(), 4);
    }


    @Test
    void addCardToRegister() {
        Actor a = new Actor(0);
        TestCard t0 = new TestCard(0);
        a.addCard(t0);
        TestCard t1 = new TestCard(1);
        a.addCard(t1);
        TestCard t2 = new TestCard(2);
        a.addCard(t2);
        TestCard t3 = new TestCard(3);
        a.addCard(t3);

        HashSet<ICard> cards = new HashSet<>();
        cards.add(t1);
        cards.add(t2);
        cards.add(t3);

        assertEquals(a.addCardToRegister(t0), true);
        assertEquals(a.getAvailableCards(), cards);
        assertEquals(a.addCardToRegister(new TestCard(4)), false);
    }

    @Test
    void updateRegisterSize() {
        Actor a = new Actor(0);
        a.updateRegisterSize(4);
        assertEquals(a.getRegisterSize(), 3);
    }

    @Test
    void clearRegisters() {
        Actor a = new Actor(0);

        TestCard t0 = new TestCard(0);
        a.addCard(t0);
        TestCard t1 = new TestCard(1);
        a.addCard(t1);
        TestCard t2 = new TestCard(2);
        a.addCard(t2);
        TestCard t3 = new TestCard(3);
        a.addCard(t3);
        TestCard t4 = new TestCard(4);
        a.addCard(t4);

        a.addCardToRegister(t0);
        a.addCardToRegister(t1);
        a.addCardToRegister(t2);
        a.addCardToRegister(t3);
        a.addCardToRegister(t4);

        a.updateRegisterSize(5);
        a.clearRegisters();
        ICard[] cards = new ICard[]{null, null, null, null, t4};


        assertArrayEquals(a.getBotRegister(), cards);
    }


}