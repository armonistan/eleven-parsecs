package eleven;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class LevelManager {
	//TODO: Hold Player
	//TODO: Hold level tilemap
	private Sprite sprite;
	
	public LevelManager() {
		//TODO: Generate background
		//TODO: Generate asteroids
		//TODO: Init Player

		TextureRegion test = Driver.assets.getAtlasRegion(new Vector2(0, 0));
		sprite = new Sprite(test);
		//sprite.setSize(0.9f, 0.9f * sprite.getHeight() / sprite.getWidth());
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		sprite.setPosition(-sprite.getWidth()/2, -sprite.getHeight()/2);
	}
	
	public void render(SpriteBatch batch) {
		//TODO: Update Player
		//TODO: Update asteroids (and the like)
		
		sprite.draw(batch);
	}
}
