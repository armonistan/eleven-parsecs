package eleven;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.*;

public class PhysicsObject {
	protected Sprite physicsObject;
	
	private Polygon physicsObjectPolygon;
	
	protected Vector2 forceOnPhysicsObject;
	protected Vector2 physicsObjectAcceleration;
	protected Vector2 physicsObjectVelocity;
	protected Vector2 physicsObjectChangeInDistance;
	
	protected float physicsObjectMass;
	
	private Vector2 position2;
	private Vector2 originPosition;
	private Vector3 position3;
	
	public PhysicsObject(float initialX, float initialY, float mass, 
			float regionX, float regionY){
		
		forceOnPhysicsObject = new Vector2();
		physicsObjectAcceleration = new Vector2();
		physicsObjectVelocity = new Vector2();
		physicsObjectChangeInDistance = new Vector2();
		
		physicsObject = new Sprite(Driver.assets.getAtlasRegion(new Vector2(regionX, regionY)));
		physicsObject.setPosition(initialX, initialY);
		
		physicsObjectMass = mass;
		
		physicsObjectPolygon = new Polygon();
		//physicsObjectPolygon.setOrigin(physicsObject.getOriginX(), physicsObject.getOriginY());
		//physicsObjectPolygon.setVertices(setPhysicsObjectPolygonVertices());
		
		position2 = new Vector2();
		position2.set(physicsObject.getX(), physicsObject.getY());
		originPosition = new Vector2();
		originPosition.set(position2.x + physicsObject.getOriginX(), position2.y + physicsObject.getOriginY());
		position3 = new Vector3();
		position3.set(position2, 0);
	}
	
	protected float[] setPhysicsObjectPolygonVertices(){
		return null;
	}
	
	public void render(SpriteBatch batch) {
		//TODO: Add movement logic
		update();
		ObjectUpdate();
		physicsObject.draw(batch);
	}
	
	//TODO: Remove new Vector2's
	public Vector3 getPosition3() {
		return position3;
	}
	
	public Vector2 getOriginPosition() {
		return originPosition;
	}
	
	public Vector2 getPosition(){
		return position2;
	}
	
	protected void ObjectUpdate() {
		
	}
	
	//player update method
	protected void update(){
		calculateAcceleration();
		calculateVelocity();
		calculateChangeInPlayerPosition();
		movePhysicsObject();
		resetForceOnPhysicsObject();
		
		position2.set(physicsObject.getX(), physicsObject.getY());
		originPosition.set(position2.x + physicsObject.getOriginX(), position2.y + physicsObject.getOriginY());
		position3.set(position2, 0);
	}
	
	private void resetForceOnPhysicsObject(){
		forceOnPhysicsObject.set(0, 0);
	}
	
	public void addForceToPhysicsObject(float x, float y){
		forceOnPhysicsObject.add(x, y);
	}

	protected void calculateAcceleration(){
		physicsObjectAcceleration.add(forceOnPhysicsObject.div(physicsObjectMass));
	}
	
	protected void calculateVelocity(){
		physicsObjectVelocity.add(physicsObjectAcceleration.scl(Gdx.graphics.getDeltaTime()));
	}
	
	protected void calculateChangeInPlayerPosition(){
		physicsObjectChangeInDistance.add(physicsObjectVelocity.scl(Gdx.graphics.getDeltaTime()));
	}
	
	protected void movePhysicsObject(){
		physicsObject.setPosition(physicsObject.getX() + physicsObjectChangeInDistance.x, physicsObject.getY() + physicsObjectChangeInDistance.y);
	}
	
	protected void changeRotation(float changeInDegrees){
		physicsObject.setRotation(physicsObject.getRotation() + changeInDegrees);
	}
	
	//getters and setters
	public Sprite getSprite(){
		return physicsObject;
	}
	
	public Polygon getPhysicsObjectPolygon(){
		return physicsObjectPolygon;
	}
	
	public float getMass(){
		return physicsObjectMass;
	}
}
