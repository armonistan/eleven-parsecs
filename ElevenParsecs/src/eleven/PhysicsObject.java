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
	
	protected int physicsObjectMass;
	
	private Vector2 position2;
	private Vector2 originPosition;
	private Vector3 position3;
	
	public PhysicsObject(float initialX, float initialY, int mass, 
			float regionX, float regionY, float intialVelocityX, float intialVelocityY){
		
		forceOnPhysicsObject = new Vector2();
		physicsObjectAcceleration = new Vector2();
		physicsObjectVelocity = new Vector2();
		physicsObjectVelocity.set(intialVelocityX, intialVelocityY);
		physicsObjectChangeInDistance = new Vector2();
		
		physicsObject = new Sprite(Driver.assets.getAtlasRegion(new Vector2(regionX, regionY)));
		physicsObject.setPosition(initialX, initialY);
		
		physicsObjectMass = mass;
		
		position2 = new Vector2();
		position2.set(physicsObject.getX(), physicsObject.getY());
		originPosition = new Vector2();
		originPosition.set(position2.x + physicsObject.getOriginX(), position2.y + physicsObject.getOriginY());
		position3 = new Vector3();
		position3.set(position2, 0);
		
		physicsObjectPolygon = new Polygon();
		physicsObjectPolygon.setOrigin(this.getPosition2().x, this.getPosition2().y);
		
	}
	
	protected float[] setPhysicsObjectPolygonVertices(int image){
		return null;
	}
	
	public void render(SpriteBatch batch) {
		//TODO: Add movement logic
		update();
		physicsObject.draw(batch);
		Driver.gui.font.draw(batch, this.getMass() + "", this.position2.x, this.position2.y);
	}
	
	//TODO: Remove new Vector2's
	public Vector3 getPosition3() {
		return position3;
	}
	
	public Vector2 getOriginPosition() {
		return originPosition;
	}
	
	public Vector2 getPosition2(){
		return position2;
	}
	
	//update method
	protected void update(){
		keepObjectBounded();
		calculateAcceleration();
		calculateVelocity();
		calculateChangeInPlayerPosition();
		movePhysicsObject();
		resetForceOnPhysicsObject();
		updatePolygon();
	}
	
	protected void keepObjectBounded() {
		float scale = 0.5f;
		
		if (physicsObject.getX() < 0) {
			physicsObject.setPosition(0, physicsObject.getY());
			physicsObjectVelocity.set(physicsObjectVelocity.x * -1, physicsObjectVelocity.y);
			physicsObjectVelocity.scl(scale);
		}
		if (physicsObject.getX() >= LevelManager.mapPxSize) {
			physicsObject.setPosition(LevelManager.mapPxSize, physicsObject.getY());
			physicsObjectVelocity.set(physicsObjectVelocity.x * -1, physicsObjectVelocity.y);
			physicsObjectVelocity.scl(scale);
		}
		
		if (physicsObject.getY() < 0) {
			physicsObject.setPosition(physicsObject.getX(), 0);
			physicsObjectVelocity.set(physicsObjectVelocity.x, physicsObjectVelocity.y * -1);
			physicsObjectVelocity.scl(scale);
		}
		if (physicsObject.getY() >= LevelManager.mapPxSize) {
			physicsObject.setPosition(physicsObject.getX(), LevelManager.mapPxSize);
			physicsObjectVelocity.set(physicsObjectVelocity.x, physicsObjectVelocity.y * -1);
			physicsObjectVelocity.scl(scale);
		}
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
		
		this.setVelocity(thisFinalVelocity.x, thisFinalVelocity.y);
		this.setAcceleration(0,0);
		this.setChangeInDistance(0,0);
		//this.setPosition(this.getPosition().add(thisFinalVelocity));
		other.setVelocity(otherFinalVelocity.x, otherFinalVelocity.y);
		other.setAcceleration(0,0);
		other.setChangeInDistance(0,0);
		//other.setPosition(other.getPosition().add(otherFinalVelocity.div(10)));
	}
	
	protected void setVelocity(float newVelocityX, float newVelocityY){
		this.physicsObjectVelocity.set(newVelocityX, newVelocityY);
	}
	
	protected void setAcceleration(float newAccelerationX, float newAccelerationY){
		this.physicsObjectAcceleration.set(newAccelerationX, newAccelerationY);
	}
	
	protected void setChangeInDistance(float newChangeInDistanceX, float newChangeInDistanceY){
		this.physicsObjectChangeInDistance.set(newChangeInDistanceX, newChangeInDistanceY);
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
		physicsObjectAcceleration.set(new Vector2(forceOnPhysicsObject.x/(physicsObjectMass), forceOnPhysicsObject.y/(physicsObjectMass)));
	}
	protected void calculateVelocity(){
		physicsObjectVelocity.add(new Vector2(physicsObjectAcceleration.x * Gdx.graphics.getDeltaTime(), physicsObjectAcceleration.y * Gdx.graphics.getDeltaTime()));
	}
	
	protected void calculateChangeInPlayerPosition(){
		physicsObjectChangeInDistance.set(new Vector2(physicsObjectVelocity.x * Gdx.graphics.getDeltaTime(), physicsObjectVelocity.y * Gdx.graphics.getDeltaTime()));
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
	
	public int getMass(){
		return physicsObjectMass;
	}
	
	public void setPosition(Vector2 newPosition){
		this.physicsObject.setPosition(newPosition.x, newPosition.y);
	}
}
