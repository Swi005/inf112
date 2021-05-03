package gameBoard;

import com.badlogic.gdx.math.Vector2;
import agent.IAgent;
import cards.*;
import misc.Utils;

import java.util.*;

//Model
public class GameControllerOld
{
    private int maxActors = 4;
    private GameBoard gameBoard;
    private int botIDGen = 0;
    private HashMap<Actor, IAgent> actorAgentRelation = new HashMap<>();
    private HashSet<ICard> cards = new HashSet<>();


    public GameControllerOld(GameBoard game, int maxActors)
    {
        this.gameBoard = game;

        if(maxActors >= this.maxActors)
            this.maxActors = maxActors;

        generateStartingDeck();
    }

    /**
     * Add an actor to the inf112.game
     */
    public boolean addActor(IAgent agent)
    {
        if(actorAgentRelation.size() < maxActors)
        {
            actorAgentRelation.put(new Actor(botIDGen),agent);
            gameBoard.addBot(botIDGen);//Add bot to game with same id as actor
            botIDGen++;
            return true;
        }
        return false;
    }
    /**
     * Starts the game
     */
    public void startGame()
    {
        drawCards();
    }

    private void doInstruction(Actor a, int instructionIndex)
    {

        ICard c = a.getBotRegister()[instructionIndex];

        //Do previous instruction
        if(c instanceof Again && instructionIndex > 0)
            c = a.getBotRegister()[instructionIndex-1];
        else if(c instanceof Again && instructionIndex == 0) //If its the first instruction do nothing this turn
            return;
        if(c instanceof Move)
        {
            for (Vector2 pos: Utils.findPath(new ArrayList<Vector2>(), ((Move) c).moveLen, gameBoard.getBot(a.getId()).getFacing(),gameBoard, gameBoard.getBot(a.getId()).getRobotPos()))
            {
                if(pos != null)
                {
                    gameBoard.getBot(a.getId()).setRobotPos(pos);
                }
            }
        }
        if(c instanceof Turn)
        {
            gameBoard.getBot(a.getId()).setFacing(gameBoard.getBot(a.getId()).getFacing().turn(((Turn) c).turn));
        }
    }

    /**
     * Execute instructions for bots that have been shutdown
     */
    private void doShutDownInstructions()
    {
        for (Actor a : actorAgentRelation.keySet())
        {
            if(a.isShutDown())
            {
                //If the bot was shut down do all the actions now
                for (int i = 0; i < a.getBotRegister().length; i++)
                {
                    doInstruction(a,i);
                }
                a.powerUp();
                gameBoard.getBot(a.getId()).setHp(gameBoard.getBot(a.getId()).getMaxHp());
            }
        }
        drawCards();
    }

    /**
     * Assign cards to bot
     */
    private void drawCards()
    {
        for (Actor a: actorAgentRelation.keySet())
        {
            for (int i = 0; i < gameBoard.getBot(a.getId()).getHp()-1; i++)
            {
                ICard c = cards.iterator().next();
                cards.remove(c);
                a.addCard(c);
            }
        }
        //programRegisters();
    }
/*
    private void programRegisters()
    {
        List<Thread> threads= new LinkedList<>();
        for (final Actor a: actorAgentRelation.keySet())
        {
            a.clearRegisters();
            List<ICard> chosen = new ArrayList<>();
            chosen.addAll(a.getAvailableCards());
            for (ICard c : actorAgentRelation.get(a).getChosenCards(chosen, a.getRegisterSize())) {
                a.addCardToRegister(c);
            }
        }
        movePhase();
    }
*/
    private void movePhase()
    {
        for (int i = 0; i < 5; i++)
        {
            for (Actor a: actorAgentRelation.keySet())
            {
                if(a.isShutDown())
                    continue;
                if(a.getBotRegister()[i] != null)
                {
                    doInstruction(a,i);
                }
            }
            activateBoardElements();
            flagAndRepair();
        }
    }
    private void activateBoardElements()
    {
        gameBoard.updateBoardElements();
    }
    private void flagAndRepair()
    {
        gameBoard.updateBotStatus();

        for (Actor a: actorAgentRelation.keySet())
        {
            a.updateRegisterSize(gameBoard.getBot(a.getId()).getHp());
        }
    }

    /**
     * Generate 100 random cards and add them to the card deck
     */
    public void generateStartingDeck()
    {
        Random rnd = new Random();
        for (int i = 0; i < 100; i++)
        {
            cards.add(Utils.getCard(rnd.nextInt(8)));
        }
    }
}
