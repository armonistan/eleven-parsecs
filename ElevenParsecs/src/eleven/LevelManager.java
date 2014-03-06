package eleven;

import java.util.ArrayList;
import java.util.LinkedList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class LevelManager {
	public static Player player;
	public static Base base;
	
	public static ArrayList<Resource> resources;
	public static LinkedList<Resource> resourcesToDestroy;
	
	public LevelManager() {
		//TODO: Generate background
		//TODO: Generate asteroids
		player = new Player();
		base = new Base(new Vector2(-32, -32));

		resources = new ArrayList<Resource>();
		resourcesToDestroy = new LinkedList<Resource>();
		
		resources.add(new Resource(new Vector2(32, 32)));
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
