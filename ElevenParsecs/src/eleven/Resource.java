package eleven;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Resource {
	private Sprite resource;

	public Resource(Vector2 position) {
		resource = new Sprite(Driver.assets.getAtlasRegion(new Vector2(6, 0)));
		resource.setPosition(position.x, position.y);
	}
	
	public void render(SpriteBatch batch) {
		//TODO: Attraction towards player
		
		resource.draw(batch);
	}
}
