package eleven;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Resource {
	private Sprite resource;
	private Player player;
	private Vector2 resourceAcceleration;
	private Vector2 resourceVelocity;
	private Vector2 futureVelocity;
	private Vector2 resourceChangeInDistance;
	
	final float baseAcceleration = 100;
	final float MAX_VELOCITY = 3;

	public Resource(Vector2 position) {
		resource = new Sprite(Driver.assets.getAtlasRegion(new Vector2(6, 0)));//keep in mind the asset num
		resource.setPosition(position.x, position.y);
		player = Driver.level.player;
		resourceAcceleration = new Vector2();
		resourceVelocity = new Vector2();
		futureVelocity = new Vector2();
		resourceChangeInDistance = new Vector2();
		
	}
	
	public void render(SpriteBatch batch) {
		//TODO: Attraction towards player
		update();
		resource.draw(batch);
	}
	
	private void update(){
		if(isCollideWithPlayer()){
			player.addResource();
			Driver.level.resourcesToDestroy.add(this);
		}
		else{
			setRotation();
			calculateAcceleration();
			calculateVelocity();
			calculateChangeInDistance();
			moveResource();
		}
	}
	
	private boolean isCollideWithPlayer(){
		return CollisionHelper.checkCollide(this.resource, player.getSprite());
	}
	
	private void setRotation(){
		float deltaXOfResourceToPlayer = player.getSprite().getX() - resource.getX();
		float deltaYOfResourceToPlayer = player.getSprite().getY() - resource.getY();
		float radian = MathUtils.atan2(deltaYOfResourceToPlayer, deltaXOfResourceToPlayer);
		
		resource.setRotation(MathUtils.radiansToDegrees * radian);
	}
	
	private void calculateAcceleration(){
		
		float distAcceleration = baseAcceleration * 1/(1);
		resourceAcceleration.add(new Vector2(baseAcceleration * MathUtils.cosDeg(resource.getRotation()),
				baseAcceleration * MathUtils.sinDeg(resource.getRotation())));
	}
	
	private void calculateVelocity(){
		resourceVelocity.add(resourceAcceleration.scl(Gdx.graphics.getRawDeltaTime()));
	}
	
	private void calculateChangeInDistance(){
		resourceChangeInDistance.add(resourceVelocity.scl(Gdx.graphics.getRawDeltaTime()));
	}
	
	private void moveResource(){
		resource.setPosition(resource.getX() + resourceChangeInDistance.x, resource.getY() + resourceChangeInDistance.y);
	}

}
