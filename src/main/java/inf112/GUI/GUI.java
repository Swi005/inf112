package inf112.GUI;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import inf112.agent.IAgent;
import inf112.gameBoard.GameBoard;
import inf112.gameBoard.GameController;
import inf112.misc.BoardParser;
import inf112.tiles.ITile;

public class GUI extends Game
{

    private Screen gameScreen;
    private GameController game;

    private Skin skin = new Skin(Gdx.files.internal("path"));
    public GUI()
    {
        super();
        ITile[][] brd= BoardParser.parseBoard("");
        game = new GameController(new GameBoard(brd, brd.length, brd[0].length),1);
        gameScreen = new DebugGUI("tbd", this);
        game.addActor((IAgent)gameScreen);

    }
    @Override
    public void create()
    {

    }
    public Skin getSkin(){return skin;}
}
