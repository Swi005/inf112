package inf112.GUI;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import inf112.agent.AgentType;
import inf112.agent.IAgent;
import inf112.cards.ICard;
import jdk.internal.agent.Agent;

import java.util.List;

public class DebugGUI implements Screen, IAgent
{
    @Override
    public void show() {

    }

    @Override
    public void render(float v) {

    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public List<ICard> getChosenCards(List<ICard> availableCards, int available)
    {
        return null;
    }

    @Override
    public Vector2 getSpawnPosition(List<Vector2> availablePositions) {
        return null;
    }

    @Override
    public AgentType getAgentType() {
        return AgentType.Debug;
    }

    @Override
    public void Update() {

    }
}
