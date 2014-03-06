package eleven;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Player {
	private Sprite ship;
	//TODO: Add shooting variables
	
	public Player() {
		//TODO: Set init position
		ship = new Sprite(Driver.assets.getAtlasRegion(new Vector2(0, 0)));
	}

	public void render(SpriteBatch batch) {
		//TODO: Add movement logic
		
		ship.draw(batch);
	}
	
	public Vector3 getPosition() {
		return new Vector3(ship.getOriginX(), ship.getOriginY(), 0);
	}
}
