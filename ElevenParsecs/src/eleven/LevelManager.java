package eleven;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;

public class LevelManager {
	Player player;
	LevelGenerator generator;
	TiledMap map;
	int mapSize = 1000;
	
	public LevelManager() {
		//TODO: Generate background
		//TODO: Generate asteroids
		player = new Player();
		generator = new LevelGenerator(this.mapSize);
		map = generator.getMap();
		/*TextureRegion test = Driver.assets.getAtlasRegion(new Vector2(0, 0));
		sprite = new Sprite(test);
		//sprite.setSize(0.9f, 0.9f * sprite.getHeight() / sprite.getWidth());
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		sprite.setPosition(-sprite.getWidth()/2, -sprite.getHeight()/2);*/
	}
	
	public void render(SpriteBatch batch) {
		player.render(batch);
		//TODO: Update asteroids (and the like)
	}
	
	public Player getPlayer() {
		return player;
	}
}
