package eleven;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Destructible {
	
	private Sprite destructible;
	
	private LinkedList<Vector2> listOfForces;
	
	private Vector2 forceOnDestructible;
	private Vector2 destructibleAcceleration;
	private Vector2 destructibleVelocity;
	private Vector2 changeInDestructibleMovement;
	
	private float destructibleMass;
	
	public Destructible(Vector2 startPosition, Vector2 atlasPosition, Vector2 initialVelocity, float mass){
		destructible = new Sprite(Driver.assets.getAtlasRegion(atlasPosition));
		destructible.setPosition(startPosition.x, startPosition.y);
		
		listOfForces = new LinkedList<Vector2>();
		
		forceOnDestructible = new Vector2();
		destructibleAcceleration = new Vector2();
		destructibleVelocity = new Vector2();
		destructibleVelocity.set(initialVelocity);
		changeInDestructibleMovement = new Vector2();
		
		destructibleMass = mass;
	}
	
	public void render(SpriteBatch spriteBatch){
		update();
		destructible.draw(spriteBatch);
	}
	
	public void addForce(Vector2 newForce){
		listOfForces.add(newForce);
	}
	
	public Vector2 GetPosition() {
		return new Vector2(destructible.getX(), destructible.getY());
	}
	
	public float GetMass() {
		return this.destructibleMass;
	}
	
	private void update(){
		calculateForceOnDestructible();
		calculateAcceleration();
		calculateVelocity();
		calculateChangeInMovement();
		moveDestructible();
		clearListOfForces();
	}
	
	private void calculateForceOnDestructible(){
		for(Vector2 force : listOfForces){
			forceOnDestructible.add(force);
		}
	}
	
	private void calculateAcceleration(){
		destructibleAcceleration.add(forceOnDestructible.div(destructibleMass));
	}
	
	private void calculateVelocity(){
		destructibleVelocity.add(destructibleAcceleration.scl(Gdx.graphics.getDeltaTime()));
	}
	
	private void calculateChangeInMovement(){
		changeInDestructibleMovement.add(destructibleVelocity.scl(Gdx.graphics.getDeltaTime()));
	}
	
	private void moveDestructible(){
		destructible.setPosition(destructible.getX() + changeInDestructibleMovement.x, destructible.getY() + changeInDestructibleMovement.y);
	}
	
	private void clearListOfForces(){
		listOfForces.clear();
	}
	
}
