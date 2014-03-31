package eleven;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.*;

public class Player extends PhysicsObject{
	private int numResourcesGathered;
	
	public Laser laser;
	//movement vectors
	Vector2 forward; 
	Vector2 backward;
	
	
	//constants
	final float engineForce = 1000;
	final float playerMass = 10;
	final float LEFT = 3;//in degrees for turning
	final float RIGHT = -3;
	//TODO: Add shooting variables
	
	public Player(int x, int y) {
		super(x, y, 10, 0, 0);
		numResourcesGathered = 0;
		laser = new Laser(x, y, physicsObject.getRotation(), 100);
	}

	//player update method
	@Override
	protected void update(){
		calculateEngineForces();
		checkInput();
		laser.updatePosition(physicsObject.getOriginX(), physicsObject.getOriginY(), physicsObject.getRotation());
		super.update();
	}
	
	private void calculateEngineForces(){
		forward = new Vector2(engineForce *  MathUtils.cosDeg(physicsObject.getRotation()), 
				engineForce *  MathUtils.sinDeg(physicsObject.getRotation()));
		backward = new Vector2(-1*engineForce *  MathUtils.cosDeg(physicsObject.getRotation()), 
				-1*engineForce *  MathUtils.sinDeg(physicsObject.getRotation()));
	}
	
	//method that checks for player input
	private void checkInput(){
		if(Gdx.input.isKeyPressed(Keys.W)){
			addForceToPhysicsObject(forward);
		}
		if(Gdx.input.isKeyPressed(Keys.A)){
			changeRotation(LEFT);
		}
		if(Gdx.input.isKeyPressed(Keys.S)){
			addForceToPhysicsObject(backward);
		}
		if(Gdx.input.isKeyPressed(Keys.D)){
			changeRotation(RIGHT);
		}
		if(Gdx.input.isKeyPressed(Keys.SPACE)){
			//laser.drawLaser();
		}
	}
	
	public void addResource(){
		numResourcesGathered++;
	}
	public int getResourcesAmt(){
		return numResourcesGathered;
	}
	
	public int depositResources(){
		int passedNumResourcesGathered = numResourcesGathered;
		numResourcesGathered = 0;
		
		return passedNumResourcesGathered;
	}

}
