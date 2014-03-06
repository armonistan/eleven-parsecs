package eleven;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Base {
	private Sprite base;
	//TODO: Hold number of resources
	
	public Base(Vector2 position) {
		base = new Sprite(Driver.assets.getAtlasRegion(new Vector2(1, 0)));
		
		base.setPosition(position.x, position.y);
	}
	
	public void render(SpriteBatch batch) {
		//TODO: Check for players
		
		base.draw(batch);
	}
}
