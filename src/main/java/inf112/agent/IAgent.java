package inf112.agent;

import com.badlogic.gdx.math.Vector2;
import inf112.cards.ICard;

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
     * Querries the agent for a spawn pos for the bot,
     * @param availablePositions - Available positions that the bot can spawn at
     * @return - The chosen position
     */
    Vector2 getSpawnPosition(List<Vector2> availablePositions);

    /**
     * What type of agent it is
     * @return
     */
    AgentType getAgentType();

    /**
     * Sends board updates to the agent, i.e robot position
     */
    void Update();
}
