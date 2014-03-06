package eleven;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Resource {
	private Sprite resource;
	private Vector2 playerPosition;
	private Player player;

	public Resource(Vector2 position) {
		resource = new Sprite(Driver.assets.getAtlasRegion(new Vector2(6, 0)));
		resource.setPosition(position.x, position.y);
		playerPosition = new Vector2();
		player = Driver.level.player;
	}
	
	public void render(SpriteBatch batch) {
		//TODO: Attraction towards player
		update();
		resource.draw(batch);
	}
	
	private void update(){
		updatePlayerPosition();
		if(isCollideWithPlayer()){
			player.addResource();
			Driver.level.resourcesToDestroy.add(this);
		}
	}
	
	private void updatePlayerPosition(){
		playerPosition = player.getPosition2();
	}
	
	private boolean isCollideWithPlayer(){
		return CollisionHelper.checkCollide(this.resource, player.getSprite());
	}
	
}
