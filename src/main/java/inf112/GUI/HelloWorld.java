package inf112.GUI;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class HelloWorld implements ApplicationListener {
    private SpriteBatch batch;
    private BitmapFont font;
    
    private TiledMap map;
    private TiledMapTileLayer boardLayer,playerLayer,holeLayer,flagLayer;

    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    public int MAP_SIZE_X = 5;
    public int MAP_SIZE_Y = 5;
    public int CELL_SIZE = 300;
    @Override
    public void create() {
         map = new TmxMapLoader().load("assets/tileMap.tmx");
        boardLayer = (TiledMapTileLayer) map.getLayers().get("Board");
        playerLayer = (TiledMapTileLayer) map.getLayers().get("Player");
        holeLayer = (TiledMapTileLayer) map.getLayers().get("Hole");
        flagLayer = (TiledMapTileLayer) map.getLayers().get("Flag");

        camera = new OrthographicCamera();
        camera.setToOrtho(false, MAP_SIZE_X * CELL_SIZE, MAP_SIZE_Y * CELL_SIZE);
        camera.position.x = CELL_SIZE * MAP_SIZE_X /2;
        camera.update();

        renderer = new OrthogonalTiledMapRenderer(map, 1);
        renderer.setView(camera);

        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        renderer.render();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}