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
	public static LinkedList<Resource> resourcesToAdd;
	
	
	public static ArrayList<Destructible> destructibles;
	public static LinkedList<Destructible> destructiblesToDestroy;
	public static LinkedList<Destructible> destructiblesToAdd;
	
	public LevelManager() {
		player = new Player(playerStartX, playerStartY);
		Driver.camera.position.x = playerStartX;
		Driver.camera.position.y = playerStartY;

		base = new Base(new Vector2(playerStartX, playerStartY));

		resources = new ArrayList<Resource>();
		resourcesToDestroy = new LinkedList<Resource>();
		resourcesToAdd = new LinkedList<Resource>();
		
		destructibles = new ArrayList<Destructible>(numDestructibles);
		destructiblesToDestroy = new LinkedList<Destructible>();
		destructiblesToAdd = new LinkedList<Destructible>();
		
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
		for (Resource r : resourcesToAdd) {
			resources.add(r);
		}
		resourcesToDestroy.clear();
		resourcesToAdd.clear();
		
		//Manage the destructibles
		//TODO: added provisions for destructibles making destructibles
		
		//Does collisions for all destructibles
		for (int i = 0; i < destructibles.size(); i++) {
			Destructible d = destructibles.get(i);
			for (int j = i+1; j < destructibles.size(); j++) {
				Destructible d2 = destructibles.get(j);
				if(d != d2 && 
						CollisionHelper.checkCollideSAT(d.getPhysicsObjectPolygon(), d2.getPhysicsObjectPolygon())){
					d.calculateCollision(d2);
					d.destroy();
					d2.destroy();
				}
			}
		}
		
		for (Destructible d: destructibles){
			d.render(batch);
		}
		
		for (Destructible d : destructiblesToDestroy) {
			destructibles.remove(d);
		}
		
		for (Destructible d : destructiblesToAdd) {
			destructibles.add(d);
		}
		
		destructiblesToDestroy.clear();
		destructiblesToAdd.clear();
		
		//TODO: Update asteroids (and the like)
		batch.end();
		
	}
	
	public void addResource(float x, float y) {
		Resource newRes = new Resource(new Vector2(x, y));
		resourcesToAdd.add(newRes);
	}
	
	public void addDestructible(float x, float y, int mass) {
		if(destructibles.size() < 30)
			destructiblesToAdd.add(new Destructible(x, y, this.calculateDestructibleAtalasX(mass), this.calculateDestructibleAtalasY(mass), 0, 0, mass));
	}

	//logic in, actual numbers to be decided when atlas sheet is complete
	public int calculateDestructibleAtalasX(int mass){
		if(mass < 10)
			return 2;
		else if(mass < 20){
			return 3;
		}
		else
			return 4;
	}
	
	//logic in, actual numbers to be decided when atlas sheet is complete
	public int calculateDestructibleAtalasY(int mass){
		if(mass < 10)
			return 0;
		else
			return 0;
	}
}
