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
	public static int mapPxSize = mapSize * 32;
	public static int playerStartX = mapPxSize / 2;
	public static int playerStartY = playerStartX;
	OrthogonalTiledMapRenderer mapRenderer;

	public static int numDestructibles = 20;
	
	public static Player player;
	public static Base base;
	
	public static ArrayList<Resource> resources;
	public static LinkedList<Resource> resourcesToDestroy;
	
	public static ArrayList<Destructible> destructibles;
	public static LinkedList<Destructible> destructiblesToDestroy;
	
	public LevelManager() {
		player = new Player(playerStartX, playerStartY);
		Driver.camera.position.x = playerStartX;
		Driver.camera.position.y = playerStartY;

		base = new Base(new Vector2(playerStartX, playerStartY));

		resources = new ArrayList<Resource>();
		resourcesToDestroy = new LinkedList<Resource>();
		
		destructibles = new ArrayList<Destructible>(numDestructibles);
		destructiblesToDestroy = new LinkedList<Destructible>();
		
		generator = new LevelGenerator(this.mapSize, this.numDestructibles);
		map = generator.getMap();
		mapRenderer = new OrthogonalTiledMapRenderer(map, 1);
		
		//temp
		resources.add(new Resource(new Vector2(playerStartX + 604, playerStartY + 604)));
	}
	
	public void render(SpriteBatch batch) {
		mapRenderer.setView(Driver.camera);
		mapRenderer.render();

		Driver.gravity.ComputeGravity();
		
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
		
		for (int i = 0; i < destructibles.size(); i++) {
			Destructible d = destructibles.get(i);
			for (int j = i+1; j < destructibles.size(); j++) {
				Destructible d2 = destructibles.get(j);
				if(d != d2 && 
						CollisionHelper.checkCollideSAT(d.getPhysicsObjectPolygon(), d2.getPhysicsObjectPolygon())){
					d.calculateCollision(d2);
				}
			}
		}
		
		for (Destructible d: destructibles){
			d.render(batch);
		}
		
		for (Destructible d : destructiblesToDestroy) {
			destructibles.remove(d);
		}
		destructiblesToDestroy.clear();
		//TODO: Update asteroids (and the like)
		batch.end();
		
	}
	
	public void AddResource(float x, float y) {
		Resource newRes = new Resource(new Vector2(x, y));
		resources.add(newRes);
	}
}
