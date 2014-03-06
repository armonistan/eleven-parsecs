package eleven;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Player {
	private Sprite ship;
	private LinkedList<Vector2> listOfForces;
	private Vector2 forceOnPlayer;
	private Vector2 playerAcceleration;
	private Vector2 playerVelocity;
	private Vector2 changeInPlayerMovement;
	
	//constants
	final float engineForce = 10000;
	final float playerMass = 10;
	final Vector2 UP = new Vector2(0,engineForce);
	final Vector2 DOWN = new Vector2(0,-1 * engineForce);
	final Vector2 LEFT = new Vector2(-1 * engineForce,0);
	final Vector2 RIGHT = new Vector2(engineForce,0);
	//TODO: Add shooting variables
	
	public Player() {
		//TODO: Set init position
		ship = new Sprite(Driver.assets.getAtlasRegion(new Vector2(0, 0)));
		listOfForces = new LinkedList<Vector2>();
		forceOnPlayer = new Vector2();
		playerAcceleration = new Vector2();
		playerVelocity = new Vector2();
		changeInPlayerMovement = new Vector2();
	}

	public void render(SpriteBatch batch) {
		//TODO: Add movement logic
		update();
		ship.draw(batch);
	}
	
	public Vector3 getPosition3() {
		return new Vector3(ship.getOriginX(), ship.getOriginY(), 0);
	}
	
	public Vector2 getPosition2() {
		return new Vector2(ship.getOriginX(), ship.getOriginY());
	}
	
	//player update method
	private void update(){
		checkInput();
		sumForcesOnPlayer();
		calculateAcceleration();
		calculateVelocity();
		calculateChangeInPlayerPosition();
		movePlayer();
		clearForcesOnPlayer();
		resetForceOnPlayer();
	}
	
	//method that checks for player input
	private void checkInput(){
		if(Gdx.input.isKeyPressed(Keys.W)){
			addForceToPlayer(UP);
		}
		if(Gdx.input.isKeyPressed(Keys.A)){
			addForceToPlayer(LEFT);
		}
		if(Gdx.input.isKeyPressed(Keys.S)){
			addForceToPlayer(DOWN);
		}
		if(Gdx.input.isKeyPressed(Keys.D)){
			addForceToPlayer(RIGHT);
		}
	}
	
	//takes all forces and adds them up
	private void sumForcesOnPlayer(){
		for(Vector2 force: listOfForces){
			forceOnPlayer.add(force);
		}
	}
	
	private void resetForceOnPlayer(){
		forceOnPlayer = new Vector2(0,0);
	}
	
	public void addForceToPlayer(Vector2 newForce){
		listOfForces.add(newForce);
	}
	
	private void clearForcesOnPlayer(){
		listOfForces.clear();
	}

	private void calculateAcceleration(){
		playerAcceleration = forceOnPlayer.div(playerMass);
	}
	
	private void calculateVelocity(){
		playerVelocity = playerAcceleration.scl(Gdx.graphics.getRawDeltaTime());
	}
	
	private void calculateChangeInPlayerPosition(){
		changeInPlayerMovement = playerVelocity.scl(Gdx.graphics.getRawDeltaTime());
	}
	
	private void movePlayer(){
		ship.setPosition(ship.getX() + changeInPlayerMovement.x, ship.getY() + changeInPlayerMovement.y);
	}
}
