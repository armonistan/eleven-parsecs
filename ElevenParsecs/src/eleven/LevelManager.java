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
	public static int mapSize = 1000;
	public static int playerStartX = mapSize * 32 / 2;
	public static int playerStartY = playerStartX;
	OrthogonalTiledMapRenderer mapRenderer;

	public static int numDestructibles = 100;
	
	public static Player player;
	public static Base base;
	
	public static ArrayList<Resource> resources;
	public static LinkedList<Resource> resourcesToDestroy;
	
	public static ArrayList<Destructible> destructibles;
	public static LinkedList<Destructible> destructiblesToDestroy;
	
	public LevelManager() {
		//TODO: Generate background
		//TODO: Generate asteroids
		player = new Player(playerStartX, playerStartY);
		Driver.camera.position.x = playerStartX;
		Driver.camera.position.y = playerStartY;
		generator = new LevelGenerator(this.mapSize, this.numDestructibles);
		map = generator.getMap();
		mapRenderer = new OrthogonalTiledMapRenderer(map, 1);

		base = new Base(new Vector2(playerStartX, playerStartY));

		resources = new ArrayList<Resource>();
		resourcesToDestroy = new LinkedList<Resource>();
		
		//temp
		resources.add(new Resource(new Vector2(64, 64)));
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
		//Manage the destructibles
		//TODO: added provisions for destructibles making destructibles
		for (Destructible d : Driver.gravity.GetDestructibles()) {
			d.render(batch);
		}
				
		//TODO: Update asteroids (and the like)
		batch.end();
		
	}
}
