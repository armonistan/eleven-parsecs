package eleven;

import java.util.ArrayList;
import java.util.LinkedList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

public class LevelManager {
	LevelGenerator generator;
	TiledMap map;
	int mapSize = 1000;
	int playerStartX = mapSize * 32 / 2;
	int playerStartY = playerStartX;
	OrthogonalTiledMapRenderer mapRenderer;

	public static Player player;
	public static Base base;
	
	public static ArrayList<Resource> resources;
	public static LinkedList<Resource> resourcesToDestroy;
	
	public LevelManager() {
		//TODO: Generate background
		//TODO: Generate asteroids
		player = new Player(playerStartX, playerStartY);
		Driver.camera.position.x = playerStartX;
		Driver.camera.position.y = playerStartY;
		generator = new LevelGenerator(this.mapSize);
		map = generator.getMap();
		mapRenderer = new OrthogonalTiledMapRenderer(map, 1);

		base = new Base(new Vector2(playerStartX, playerStartY));

		resources = new ArrayList<Resource>();
		resourcesToDestroy = new LinkedList<Resource>();
		
		resources.add(new Resource(new Vector2(playerStartX + 64, playerStartY + 64)));
	}
	
	public void render(SpriteBatch batch) {
		mapRenderer.setView(Driver.camera);
		mapRenderer.render();

		batch.begin();
		base.render(batch);
		player.render(batch);
		
		//Manage the resources
		for (Resource r : resources) {
			r.render(batch);
		}
		for (Resource r : resourcesToDestroy) {
			resources.remove(r);
		}
		resourcesToDestroy.clear();
		
		//TODO: Update asteroids (and the like)
		batch.end();
		
	}
}
