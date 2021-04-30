package GUI;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import agent.IAgent;
import gameBoard.GameBoard;
import gameBoard.GameController;
import misc.BoardParser;
import tiles.ITile;

public class GUI extends Game
{
    private Screen gameScreen;
    private GameController game;

    private Skin skin;

    public GUI(int numPlayers)
    {
        super();
        ITile[][] brd= BoardParser.parseBoard("assets/tileMap.tmx");
        game = new GameController(new GameBoard(brd),numPlayers);
        gameScreen = new GameScreen("assets/tileMap.tmx", this);
        game.addActor((IAgent)gameScreen);


    }
    @Override
    public void create()
    {
        skin = new Skin(new FileHandle("assets/skin/uiskin.json"));
    }
    public Skin getSkin(){return skin;}

    @Override
    public void render() {
        super.render();
    }
}
