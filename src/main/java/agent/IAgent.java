package agent;

import com.badlogic.gdx.math.Vector2;
import cards.ICard;
import gameBoard.Robot;

import java.util.List;

public interface IAgent
{

    /**
     * What type of agent it is
     * @return
     */
    AgentType getAgentType();

}
