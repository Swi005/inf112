package inf112.gameBoard;

import com.badlogic.gdx.math.Vector2;
import inf112.GUI.GameGUI;
import inf112.agent.IAgent;
import inf112.cards.*;
import inf112.misc.Utils;

import java.util.*;

//Model
public class GameController
{
    private int maxActors = 4;
    private GameBoard gameBoard;
    private GameGUI gui;//View
    private int botIDGen = 0;
    private HashMap<Actor, IAgent> actorAgentRelation = new HashMap<>();
    private HashSet<ICard> cards = new HashSet<>();


    public GameController(GameBoard game, int maxActors)
    {
        //TODO:Finish Constructor
        this.gameBoard = game;
        if(maxActors >= this.maxActors)
            this.maxActors = maxActors;

    }

    /**
     * Add an actor to the inf112.game
     */
    public boolean addActor(IAgent agent)
    {
        if(actorAgentRelation.size() < maxActors)
        {
            actorAgentRelation.put(new Actor(botIDGen),agent);
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
        programRegisters();
    }

    private void programRegisters()
    {
        List<Thread> threads= new LinkedList<>();
        for (final Actor a: actorAgentRelation.keySet())//TODO: THE use of final here may cause trouble, in case of bugs check this
        {
            threads.add(new Thread(new Runnable() {//EXPERIMENTAL AS FUCK, DELETE ASYNC CODE IF IFFY!!!!!!!!!!!!!!!
                public void run() {
                    a.clearRegisters();
                    for (ICard c: actorAgentRelation.get(a).getChosenCards((List<ICard>) a.getAvailableCards(),a.getRegisterSize()))
                    {
                        a.addCardToRegister(c);
                    }
                }
            }));
            threads.get(threads.size()-1).start();
        }
        while(!Utils.threadsHaveFinished(threads))
        {
            //TODO: Send msg to IAgents?
            //Pros: Gives waiting msg,
            //Negs: Spams agents can cause timeouts etc.
        }

        movePhase();
    }

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
        gameBoard.updateBoardElems();
    }
    private void flagAndRepair()
    {
        gameBoard.updateBotStatus();

        for (Actor a: actorAgentRelation.keySet())
        {
            a.updateRegisterSize(gameBoard.getBot(a.getId()).getHp());
        }
    }
}
