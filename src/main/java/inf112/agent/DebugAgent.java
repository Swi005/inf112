package inf112.agent;

import com.badlogic.gdx.math.Vector2;
import inf112.cards.ICard;

import java.util.List;

//TODO: this
public class DebugAgent implements IAgent
{

    @Override
    public List<ICard> getChosenCards(List<ICard> availableCards, int available) {
        return null;
    }

    @Override
    public Vector2 getSpawnPosition(List<Vector2> availablePositions) {
        return null;
    }

    @Override
    public AgentType getAgentType() {
        return null;
    }

    @Override
    public void Update() {

    }
}
