package eleven;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.*;

public class PhysicsObject {
	protected Sprite physicsObject;
	
	protected Polygon physicsObjectPolygon;
	
	protected Vector2 forceOnPhysicsObject;
	protected Vector2 physicsObjectAcceleration;
	protected Vector2 physicsObjectVelocity;
	protected Vector2 physicsObjectChangeInDistance;
	
	protected LinkedList<Vector2> listOfForces;
	
	protected float physicsObjectMass;
	
	public PhysicsObject(float initialX, float initialY, float mass, 
			float regionX, float regionY, float intialVelocityX, float intialVelocityY){
		listOfForces = new LinkedList<Vector2>();
		
		forceOnPhysicsObject = new Vector2();
		physicsObjectAcceleration = new Vector2();
		physicsObjectVelocity = new Vector2();
		physicsObjectVelocity.set(intialVelocityX, intialVelocityY);
		physicsObjectChangeInDistance = new Vector2();
		
		physicsObject = new Sprite(Driver.assets.getAtlasRegion(new Vector2(regionX, regionY)));
		physicsObject.setPosition(initialX, initialY);
		
		physicsObjectMass = mass;
		
		physicsObjectPolygon = new Polygon();
		physicsObjectPolygon.setOrigin(this.getPosition2().x, this.getPosition2().y);
	}
	
	protected float[] setPhysicsObjectPolygonVertices(int image){
		return null;
	}
	
	public void render(SpriteBatch batch) {
		//TODO: Add movement logic
		update();
		ObjectUpdate();
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
	
	protected void ObjectUpdate() {
		
	}
	
	//update method
	protected void update(){
		sumForcesOnPhysicsObject();
		calculateAcceleration();
		calculateVelocity();
		calculateChangeInPlayerPosition();
		movePhysicsObject();
		clearForcesOnPhysicsObject();
		resetForceOnPhysicsObject();
		updatePolygon();
	}
	
	public void calculateCollision(PhysicsObject other){
		Vector2 thisFinalVelocity = new Vector2();
		Vector2 otherFinalVelocity = new Vector2();
		
		thisFinalVelocity.x = ((this.getMass() - other.getMass())*this.physicsObjectVelocity.x + 
				2 * other.getMass() * other.getVelocity().x)/(this.getMass() + other.getMass());
		thisFinalVelocity.y = ((this.getMass() - other.getMass())*this.physicsObjectVelocity.y + 
				2 * other.getMass() * other.getVelocity().y)/(this.getMass() + other.getMass());
		
		otherFinalVelocity.x = (2*this.getMass()*this.getVelocity().x - (this.getMass() - 
				other.getMass())*other.getVelocity().x)/(this.getMass() + other.getMass());
		otherFinalVelocity.y = (2*this.getMass()*this.getVelocity().y - (this.getMass() - 
				other.getMass())*other.getVelocity().y)/(this.getMass() + other.getMass());
		
		this.setVelocity(thisFinalVelocity);
		this.setAcceleration(new Vector2(0,0));
		this.setChangeInDistance(new Vector2(0,0));
		this.setPosition(this.getPosition().add(thisFinalVelocity));
		other.setVelocity(otherFinalVelocity.scl(30));
		other.setAcceleration(new Vector2(0,0));
		other.setChangeInDistance(new Vector2(0,0));
		other.setPosition(other.getPosition().add(otherFinalVelocity.div(10)));
	}
	
	protected void setVelocity(Vector2 newVelocity){
		this.physicsObjectVelocity.set(newVelocity);
	}
	
	protected void setAcceleration(Vector2 newAcceleration){
		this.physicsObjectAcceleration.set(newAcceleration);
	}
	
	protected void setChangeInDistance(Vector2 newChangeInDistance){
		this.physicsObjectChangeInDistance.set(newChangeInDistance);
	}
	
	protected Vector2 getVelocity(){
		return this.physicsObjectVelocity;
	}
	
	protected void updatePolygon(){
		physicsObjectPolygon.setOrigin(this.getPosition2().x, this.getPosition2().y);
		
		for(int i = 0; i < physicsObjectPolygon.getVertices().length; i++){
			if(i%2==0)
				physicsObjectPolygon.getVertices()[i] += physicsObjectChangeInDistance.x;
			else
				physicsObjectPolygon.getVertices()[i] += physicsObjectChangeInDistance.y;
		}
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
		physicsObjectChangeInDistance.add(new Vector2(physicsObjectVelocity.x * Gdx.graphics.getDeltaTime(), physicsObjectVelocity.y));
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
	
	public void setPosition(Vector2 newPosition){
		this.physicsObject.setPosition(newPosition.x, newPosition.y);
	}
}
