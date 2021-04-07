package inf112.gameBoard;


import inf112.cards.ICard;

import java.util.HashSet;

/**
 * This class represents a player or ai in the inf112.game.
 */
public class Actor
{
    private final int id;
    private HashSet<ICard> availableCards = new HashSet<>();

    private int registerSize = 5;
    private ICard[] botRegister = new ICard[registerSize];

    private int numRespawns; // number of respawns allowed

    private boolean shutDown;
    public Actor(int id)
    {
        this.id = id;
    }

    /**
     * Adds a card to this actors available cards
     * @param card
     */
    public void addCard(ICard card)
    {
        availableCards.add(card);
    }

    /**
     * Adds a card to the register
     * @param card a card
     * @return
     */
    public boolean addCardToRegister(ICard card)
    {
        if(availableCards.contains(card))
        {
            //For loop too check that the card is added as early as possible in the register
            for (int i = 0; i < registerSize; i++)
            {
                if(botRegister[i] == null)
                {
                    botRegister[i] = card;
                    availableCards.remove(card);//remove the card that is to be added
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Lock registers on damage
     * @param botHp
     */
    public void updateRegisterSize(int botHp)
    {
        if(botHp<6)
            registerSize = botHp-1;
        else
            registerSize = 5;
    }

    /**
     * Clears the register of cards
     */
    public void clearRegisters()
    {
        for (int i = 0; i <registerSize; i++) {
            botRegister[i] = null;
        }
    }

    //Getters/Setters
    public int getRegisterSize() {
        return registerSize;
    }

    public ICard[] getBotRegister() {
        return botRegister;
    }
    public void shutDown()
    {
        shutDown = true;
    }
    public boolean isShutDown()
    {
        return shutDown;
    }
    public void powerUp()
    {
        shutDown = false;
    }

    public int getId()
    {
        return id;
    }

    public HashSet<ICard> getAvailableCards()
    {
        return availableCards;
    }
}
