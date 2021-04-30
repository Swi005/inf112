package agent;

import com.badlogic.gdx.math.Vector2;
import cards.ICard;
import gameBoard.Robot;

import java.util.List;

public interface IAgent
{
    /**
     * Asks the agent to select cards for bot
     * @param availableCards - available cards to chose from
     * @param available - number of cards that can be chosen
     * @return - the cards chosen by the agent
     */
    List<ICard> getChosenCards(List<ICard> availableCards, int available);

    /**
     * What type of agent it is
     * @return
     */
    AgentType getAgentType();

    /**
     * Sends board updates to the agent, i.e robot position
     */
    void Update(List<Robot> bots);

}
