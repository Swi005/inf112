package inf112.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.agent.AgentType;
import inf112.agent.IAgent;
import inf112.cards.ICard;
import inf112.gameBoard.Robot;

import java.util.List;

public class DebugGUI implements Screen, IAgent
{
    private final int CELL_SIZE =300;

    private final TiledMapTileLayer playerLayer;
    private final TiledMapTileLayer boardLayer;
    private final OrthogonalTiledMapRenderer renderer;
    private final OrthographicCamera camera;

    private Stage stage;
    private final Viewport view;

    //Tables
    private Table availableTable;//Avaiable cards
    private Table chosenTable; // Cards chosen by player

    //Buttons
    private Table buttons;
    protected TextButton powerDown;//Power botdown
    protected TextButton nextTurn; //Do the next turn
    protected TextButton clear; //clear chosen cards

    private GUI gui;
    public DebugGUI(String mapPath, GUI gui) {
        this.gui = gui;
        TiledMap map = new TmxMapLoader().load(mapPath);
        boardLayer = (TiledMapTileLayer)map.getLayers().get("Board");
        playerLayer = (TiledMapTileLayer)map.getLayers().get("player");

        final int height = boardLayer.getHeight() * CELL_SIZE;
        final int width = boardLayer.getWidth() * CELL_SIZE;

        view = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        camera = new OrthographicCamera();
        camera.setToOrtho(false, width * CELL_SIZE, height * CELL_SIZE);
        camera.position.x = CELL_SIZE * width /2;
        camera.update();

        renderer = new OrthogonalTiledMapRenderer(map, 1);
        renderer.setView(camera);
    }


    @Override
    public void show()
    {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Table cardSlots = new Table();
        Table placements = new Table();
        placements.setBounds(Gdx.graphics.getWidth()/2f,0,Gdx.graphics.getWidth()/6f,Gdx.graphics.getHeight());
        cardSlots.setBounds(Gdx.graphics.getWidth()/2f,0,Gdx.graphics.getWidth()/6f,Gdx.graphics.getHeight());


        chosenTable = new Table();
        availableTable = new Table();

        buttons.setBounds(Gdx.graphics.getWidth()-(Gdx.graphics.getWidth()/6f),0,Gdx.graphics.getWidth()/6f,Gdx.graphics.getHeight()/5f);

        powerDown = new TextButton("Power Down",gui.getSkin());
        powerDown.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                //TODO: Signal to gamecontroller that bot is shut down
            }
        });


        nextTurn = new TextButton("Next Turn", gui.getSkin());
        powerDown.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                //TODO: Signal to gamecontroller that bot is shut down
            }
        });

        buttons = new Table();
        stage.addActor(placements);
        stage.addActor(chosenTable);
        stage.addActor(cardSlots);
        stage.addActor(availableTable);
        stage.addActor(buttons);
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
        return availablePositions.get(0);
    }

    @Override
    public AgentType getAgentType() {
        return AgentType.Player;
    }

    @Override
    public void Update(List<Robot> bots) {

    }
}
