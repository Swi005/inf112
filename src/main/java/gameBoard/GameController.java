package gameBoard;

import agent.IAgent;
import cards.Again;
import cards.ICard;
import cards.Move;
import cards.Turn;
import com.badlogic.gdx.math.Vector2;
import misc.Utils;

import java.util.*;

public class GameController {

    private final int MAXACTORS = 4;
    private final int HANDSIZEMAX = 10;

    private GameBoard gameBoard;

    private int botIDGen = 0;
    private HashMap<IAgent, Actor> actorAgentRelation = new HashMap<>();

    public GameController(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    /**
     * Get available cards
     * @param agent
     * @return
     */
    public HashSet<ICard> getCards(IAgent agent)
    {
        return actorAgentRelation.get(agent).getAvailableCards();
    }

    /**
     * Get the register size
     * @param agent
     * @return
     */
    public int getRegisterSize(IAgent agent)
    {
        return actorAgentRelation.get(agent).getRegisterSize();
    }

    /**
     * Program the registers
     * @param agent
     */
    public void programRegister(IAgent agent, List<ICard> register)
    {
        actorAgentRelation.get(agent).clearRegisters();
        for(ICard c:register)
        {
            actorAgentRelation.get(agent).addCardToRegister(c);
        }
        //Draw as many cards as you can
        for (int i = 0; i < gameBoard.getBot(actorAgentRelation.get(agent).getId()).getHp()-1; i++)
        {
            if(actorAgentRelation.get(agent).getAvailableCards().size() < HANDSIZEMAX)
                actorAgentRelation.get(agent).addCard(getCard());
            else
                break;
        }
    }

    /**
     * Does a turn
     * @param agent - The agent doing the turn
     * @return all the robots
     */
    public List<Robot> executeTurn(IAgent agent)
    {
        movePhase(agent);
        activateBoardElements();
        flagAndRepair();
        gameBoard.checkOutOfBounds();

        List<Robot> bots = new ArrayList<>();
        for (Actor a: actorAgentRelation.values()) {
            bots.add(gameBoard.getBot(a.getId()));
        }
        return bots;
    }
    private void movePhase(IAgent agent)
    {
        Actor a = actorAgentRelation.get(agent);
        for (int i = 0; i < a.getRegisterSize(); i++) {
            if(a.getBotRegister()[i] != null)
            {
                doInstruction(a,i);
            }
        }
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
                gameBoard.getBot(a.getId()).setRobotPos(pos);
        }
        if(c instanceof Turn)
        {
            gameBoard.getBot(a.getId()).setFacing(gameBoard.getBot(a.getId()).getFacing().turn(((Turn) c).turn));
        }
    }
    private void activateBoardElements()
    {
        gameBoard.updateBoardElements();
    }
    private void flagAndRepair()
    {
        gameBoard.updateBotStatus();
        for (Actor a: actorAgentRelation.values())
        {
            a.updateRegisterSize(gameBoard.getBot(a.getId()).getHp());
        }
    }

    /**
     * Add an actor to the game
     */
    public boolean addActor(IAgent agent)
    {
        if(actorAgentRelation.size() < MAXACTORS)
        {
            actorAgentRelation.put(agent, new Actor(botIDGen));
            gameBoard.addBot(botIDGen);//Add bot to game with same id as actor
            botIDGen++;
            for (int i = 0; i < HANDSIZEMAX; i++) {
                actorAgentRelation.get(agent).addCard(getCard());
            }
            return true;
        }
        return false;
    }

    /**
     * Generate 100 random cards and add them to the card deck
     */
    private ICard getCard()
    {
        Random rnd = new Random();
        return Utils.getCard(rnd.nextInt(8));
    }
    public List<Robot> getRobots()
    {
        List<Robot> retList = new ArrayList<>();
        for (Actor a: actorAgentRelation.values()) {
            retList.add(gameBoard.getBot(a.getId()));
        }
        return retList;
    }
    public boolean checkWin()
    {
        return gameBoard.checkWin();
    }
}
