package eleven;

import java.util.ArrayList;
import java.util.LinkedList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;

public class LevelManager {
<<<<<<< HEAD
	Player player;
	LevelGenerator generator;
	TiledMap map;
	int mapSize = 1000;
=======
	public static Player player;
	public static Base base;
	
	public static ArrayList<Resource> resources;
	public static LinkedList<Resource> resourcesToDestroy;
>>>>>>> e049640647048f30f6dcaff5222cad6f7cc955ac
	
	public LevelManager() {
		//TODO: Generate background
		//TODO: Generate asteroids
		player = new Player();
<<<<<<< HEAD
		generator = new LevelGenerator(this.mapSize);
		map = generator.getMap();
		/*TextureRegion test = Driver.assets.getAtlasRegion(new Vector2(0, 0));
		sprite = new Sprite(test);
		//sprite.setSize(0.9f, 0.9f * sprite.getHeight() / sprite.getWidth());
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		sprite.setPosition(-sprite.getWidth()/2, -sprite.getHeight()/2);*/
=======
		base = new Base(new Vector2(-32, -32));

		resources = new ArrayList<Resource>();
		resourcesToDestroy = new LinkedList<Resource>();
		
		resources.add(new Resource(new Vector2(32, 32)));
>>>>>>> e049640647048f30f6dcaff5222cad6f7cc955ac
	}
	
	public void render(SpriteBatch batch) {
		base.render(batch);
		player.render(batch);
		
		//Manage the resources
		for (Resource r : resources) {
			r.render(batch);
		}
		for (Resource r : resourcesToDestroy) {
			resources.remove(r);
		}
		
		//TODO: Update asteroids (and the like)
	}
}
