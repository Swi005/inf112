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
    private boolean isStarted = false;

    //constructor args
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

    private final GUI gui;

    public GUI(String mapPath)
    {
        super();
        this.gui = this;
        this.mapPath = mapPath;
    }

    @Override
    public void create()
    {
        //Setup game and add this as a player
        skin = new Skin(new FileHandle("assets/skin/uiskin.json"));
        ITile[][] brd= BoardParser.parseBoard("assets/tileMap.tmx");
        game = new GameController(new GameBoard(brd));
        game.addActor(gui);


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
        availableTable.setBounds(Gdx.graphics.getWidth()/1.5f, 0, Gdx.graphics.getWidth()*0.3f, Gdx.graphics.getHeight()*0.8f);
        availableTable.setDebug(true);

        Table btnTable = new Table();
        //Buttons
        nextTurn = new TextButton("Next Turn", skin);
        nextTurn.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                availableCards.clear();

                game.programRegister(gui, chosenCards);

                chosenCards.clear();

                chosenTable.clear();

                availableCards.addAll(game.getCards(gui));
                renderAvailableCards();

                showBots(game.executeTurn(gui));
            }
        });

        TextButton start = new TextButton("Start Game", skin);
        start.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if(!isStarted)
                {
                    availableCards.addAll(game.getCards(gui));
                    renderAvailableCards();
                    System.out.println(availableCards);
                    isStarted=true;

                    showBots(game.getRobots());
                }
            }
        });
        btnTable.add(start, nextTurn);
        btnTable.bottom().left();

        botCells.add(new TiledMapTileLayer.Cell());
        botCells.get(botCells.size()-1).setTile(new StaticTiledMapTile(new Sprite(new TextureRegion(new Texture("assets/player.png")).split(CELL_SIZE, CELL_SIZE)[0][0])));

        stage.addActor(btnTable);
        stage.addActor(chosenTable);
        stage.addActor(availableTable);

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
    }
    private void renderAvailableCards()
    {
        availableTable.clear();
        int i = 0;
        for (final ICard card: availableCards)
        {
            final TextButton btn = new TextButton(card.toString(),skin);
            btn.addListener(new ChangeListener()
            {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    chosenCards.add(card);
                    availableCards.remove(card);
                    availableTable.removeActor(btn);
                    chosenTable.add(btn);
                }
            });
            i++;
            availableTable.add(btn);
            if(i%2==0)
                availableTable.row();
        }
    }

    @Override
    public AgentType getAgentType() {
        return AgentType.Player;
    }

    public void showBots(List<Robot> bots)
    {
        for (Robot b : bots)
        {
            Vector2 pos = b.getRobotPos();

            botCells.get(b.getId()).setRotation(b.getFacing().getDegrees());
            playerLayer.setCell((int)pos.x,(int)pos.y,botCells.get(b.getId()));
        }
    }
}
