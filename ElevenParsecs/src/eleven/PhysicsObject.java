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
	
	protected LinkedList<Vector2> listOfForces;
	
	protected float physicsObjectMass;
	
	public PhysicsObject(float initialX, float initialY, float mass, 
			float regionX, float regionY){
		listOfForces = new LinkedList<Vector2>();
		
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
	}
	
	protected float[] setPhysicsObjectPolygonVertices(){
		return null;
	}
	
	public void render(SpriteBatch batch) {
		//TODO: Add movement logic
		update();
		physicsObject.draw(batch);
	}
	
	public Vector3 getPosition3() {
		return new Vector3(physicsObject.getOriginX() + physicsObject.getX(), physicsObject.getOriginY() + physicsObject.getY(), 0);
	}
	
	public Vector2 getPosition2() {
		return new Vector2(physicsObject.getOriginX() + physicsObject.getX(), physicsObject.getOriginY() + physicsObject.getY());
	}
	
	public Vector2 getPosition(){
		return new Vector2(physicsObject.getX(), physicsObject.getY());
	}
	
	//player update method
	protected void update(){
		sumForcesOnPhysicsObject();
		calculateAcceleration();
		calculateVelocity();
		calculateChangeInPlayerPosition();
		movePhysicsObject();
		clearForcesOnPhysicsObject();
		resetForceOnPhysicsObject();
	}
	
	//takes all forces and adds them up
	protected void sumForcesOnPhysicsObject(){
		for(Vector2 force: listOfForces){
			forceOnPhysicsObject.add(force);
		}
	}
	
	private void resetForceOnPhysicsObject(){
		forceOnPhysicsObject = new Vector2(0,0);
	}
	
	public void addForceToPhysicsObject(Vector2 newForce){
		listOfForces.add(newForce);
	}
	
	protected void clearForcesOnPhysicsObject(){
		listOfForces.clear();
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
