package eleven;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.*;

public class Player {
	private Sprite ship;
	private LinkedList<Vector2> listOfForces;
	private Vector2 forceOnPlayer;
	private Vector2 playerAcceleration;
	private Vector2 playerVelocity;
	private Vector2 changeInPlayerMovement;
	//movement vectors
	Vector2 forward; 
	Vector2 backward;
	
	
	//constants
	final float engineForce = 1000;
	final float playerMass = 10;
	final float LEFT = 3;//in degrees for turning
	final float RIGHT = -3;
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
		return new Vector3(ship.getOriginX() + ship.getX(), ship.getOriginY() + ship.getY(), 0);
	}
	
	public Vector2 getPosition2() {
		return new Vector2(ship.getOriginX() + ship.getX(), ship.getOriginY() + ship.getY());
	}
	
	//player update method
	private void update(){
		calculateEngineForces();
		checkInput();
		sumForcesOnPlayer();
		calculateAcceleration();
		calculateVelocity();
		calculateChangeInPlayerPosition();
		movePlayer();
		clearForcesOnPlayer();
		resetForceOnPlayer();
	}
	
	private void calculateEngineForces(){
		forward = new Vector2(engineForce *  MathUtils.cosDeg(ship.getRotation()), 
				engineForce *  MathUtils.sinDeg(ship.getRotation()));
		backward = new Vector2(-1*engineForce *  MathUtils.cosDeg(ship.getRotation()), 
				-1*engineForce *  MathUtils.sinDeg(ship.getRotation()));
	}
	
	//method that checks for player input
	private void checkInput(){
		if(Gdx.input.isKeyPressed(Keys.W)){
			addForceToPlayer(forward);
		}
		if(Gdx.input.isKeyPressed(Keys.A)){
			changeRotation(LEFT);
		}
		if(Gdx.input.isKeyPressed(Keys.S)){
			addForceToPlayer(backward);
		}
		if(Gdx.input.isKeyPressed(Keys.D)){
			changeRotation(RIGHT);
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
		playerAcceleration.add(forceOnPlayer.div(playerMass));
	}
	
	private void calculateVelocity(){
		playerVelocity.add(playerAcceleration.scl(Gdx.graphics.getRawDeltaTime()));
	}
	
	private void calculateChangeInPlayerPosition(){
		changeInPlayerMovement.add(playerVelocity.scl(Gdx.graphics.getRawDeltaTime()));
	}
	
	private void movePlayer(){
		ship.setPosition(ship.getX() + changeInPlayerMovement.x, ship.getY() + changeInPlayerMovement.y);
	}
	
	private void changeRotation(float changeInDegrees){
		ship.setRotation(ship.getRotation() + changeInDegrees);
	}
}
