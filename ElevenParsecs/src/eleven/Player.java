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
		super(x, y, 10, 0, 0, 0, 0);
		numResourcesGathered = 0;
		laser = new Laser(x, y, physicsObject.getRotation(), 100, 100);
		forward =  new Vector2();
		backward = new Vector2();
	}

	//player update method
	@Override
	protected void update(){
		calculateEngineForces();
		checkInput();
		super.update();
		laser.updatePosition(physicsObject.getX() + 16, physicsObject.getY() + 16, physicsObject.getRotation());
	}
	
	private void calculateEngineForces(){
		forward.set(engineForce *  MathUtils.cosDeg(physicsObject.getRotation()), 
				engineForce *  MathUtils.sinDeg(physicsObject.getRotation()));
		backward.set(-1*engineForce *  MathUtils.cosDeg(physicsObject.getRotation()), 
				-1*engineForce *  MathUtils.sinDeg(physicsObject.getRotation()));
	}
	
	//method that checks for player input
	private void checkInput(){
		if(Gdx.input.isKeyPressed(Keys.W)){
			addForceToPhysicsObject(forward.x, forward.y);
		}
		if(Gdx.input.isKeyPressed(Keys.A)){
			changeRotation(LEFT);
		}
		if(Gdx.input.isKeyPressed(Keys.S)){
			addForceToPhysicsObject(backward.x, backward.y);
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
