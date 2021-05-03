package GUI;

import agent.AgentType;
import agent.IAgent;
import cards.ICard;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import gameBoard.GameBoard;
import gameBoard.GameController;
import gameBoard.Robot;
import misc.BoardParser;
import tiles.ITile;

import java.util.ArrayList;
import java.util.List;

public class GUI extends Game implements IAgent {

    //Consts
    private final int CELL_SIZE =300;

    private Skin skin;
    private GameController game;

    private SpriteBatch batch;
    private BitmapFont font;

    int height;
    int width;

    //Flags
    private boolean ready = false;

    //constructor args
    private int numPlayers;
    private String mapPath;


    private TiledMapTileLayer playerLayer;
    private TiledMapTileLayer boardLayer;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    //Viewport
    private Viewport appView;

    //Stage
    Stage stage;

    //Tables
    private Table availableTable;//Available cards
    private Table chosenTable; // Cards chosen by player

    //Buttons
    protected TextButton nextTurn; //Do the next turn

    private List<TiledMapTileLayer.Cell> botCells = new ArrayList<>();
    private List<ICard> availableCards = new ArrayList<>();
    private List<ICard> chosenCards = new ArrayList<>();


    public GUI(int nPlayers, String mapPath)
    {
        super();
        this.numPlayers = nPlayers;
        this.mapPath = mapPath;
    }

    @Override
    public void create()
    {
        //Setup game and add this as a player
        skin = new Skin(new FileHandle("assets/skin/uiskin.json"));
        ITile[][] brd= BoardParser.parseBoard("assets/tileMap.tmx");
        game = new GameController(new GameBoard(brd),numPlayers);
        game.addActor(this);


        //Load the map
        TiledMap map = new TmxMapLoader().load(mapPath);
        boardLayer = (TiledMapTileLayer)map.getLayers().get("Board");
        playerLayer = (TiledMapTileLayer)map.getLayers().get("Player");

        height = boardLayer.getHeight() * CELL_SIZE;
        width = boardLayer.getWidth() * CELL_SIZE;



        Viewport gameBoardView = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        appView= new FitViewport(width * 1.5f, height*1.2f);
        appView.update(width, height,true);

        camera = (OrthographicCamera) appView.getCamera();
        camera.update();

        //Renderer
        renderer = new OrthogonalTiledMapRenderer(map, 1);
        renderer.setView(camera);

        //Stage and Tables
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        chosenTable = new Table();
        chosenTable.left().top();
        chosenTable.setBounds(0, Gdx.graphics.getHeight()/1.2f,Gdx.graphics.getWidth(), Gdx.graphics.getHeight()*0.2f);
        chosenTable.setDebug(true);

        availableTable = new Table();
        availableTable.right();
        availableTable.setBounds(Gdx.graphics.getWidth()/1.5f, 0, Gdx.graphics.getWidth()*0.5f, Gdx.graphics.getHeight()*0.8f);
        availableTable.setDebug(true);
        //Buttons
        nextTurn = new TextButton("Next Turn", skin);
        nextTurn.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                ready = true;
            }
        });

        botCells.add(new TiledMapTileLayer.Cell());
        botCells.get(botCells.size()-1).setTile(new StaticTiledMapTile(new Sprite(new TextureRegion(new Texture("assets/player.png")).split(CELL_SIZE, CELL_SIZE)[0][0])));

        stage.addActor(nextTurn);
        stage.addActor(chosenTable);
        stage.addActor(availableTable);


        game.startGame();
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        renderer.render();
        stage.draw();
        renderAvailableCards();
        renderChosenCards();
    }
    private void renderChosenCards()
    {
        chosenTable.clear();
        renderer.getBatch().begin();
        for (final ICard card : chosenCards)
        {

            final TextButton btn = new TextButton(card.toString(), skin);
            chosenTable.add(btn);
        }
        renderer.getBatch().end();
    }
    private void renderAvailableCards()
    {
        renderer.getBatch().begin();
        for (final ICard card: availableCards) {
            final TextButton btn = new TextButton(card.toString(),skin);
            btn.addListener(new ChangeListener()
            {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    chosenCards.add(card);
                    availableTable.removeActor(btn);
                }
            });
            availableTable.add(btn);
        }

        renderer.getBatch().end();
    }
    @Override
    public List<ICard> getChosenCards(List<ICard> availableCards, int available) {
        availableCards.clear();
        this.availableCards = availableCards;
        availableCards.clear();
        while(!ready)
        {

        }
        ready = false;
        return chosenCards;
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
