package eleven;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Base {
	private Sprite base;
	private Player player;
	private int numResourcesHeld;
	//TODO: Hold number of resources
	
	public Base(Vector2 position) {
		base = new Sprite(Driver.assets.getAtlasRegion(new Vector2(1, 0)));
		player = Driver.level.player;
		base.setPosition(position.x, position.y);
		numResourcesHeld = 0;
	}
	
	public void render(SpriteBatch batch) {
		//TODO: Check for players
		update();
		base.draw(batch);
	}
	
	private void update(){
		if(isCollideWithPlayer()){
			numResourcesHeld = player.depositResources();
		}
		
		System.out.println(numResourcesHeld);
	}
	
	private boolean isCollideWithPlayer(){
		return CollisionHelper.checkCollide(this.base, player.getSprite());
	}
}
