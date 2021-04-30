package GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import agent.AgentType;
import agent.IAgent;
import cards.ICard;
import gameBoard.Robot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameScreen implements Screen, IAgent
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

    private List<TiledMapTileLayer.Cell> botCells = new ArrayList<>();


    private GUI gui;
    public GameScreen(String mapPath, GUI gui)
    {
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


        botCells.add(new TiledMapTileLayer.Cell());
        botCells.get(botCells.size()-1).setTile(new StaticTiledMapTile(new Sprite(new TextureRegion(new Texture("assets/player.png")).split(CELL_SIZE, CELL_SIZE)[0][0])));
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
                //TODO: Signal to gameController that bot is shut down
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
        for (Robot b : bots)
        {
            Vector2 pos = b.getRobotPos();

            botCells.get(b.getId()).setRotation(b.getFacing().getDegrees());
            playerLayer.setCell((int)pos.x,(int)pos.y,botCells.get(b.getId()));
        }
    }
}
