package eleven;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

public class Destructible extends PhysicsObject{
	
	public Destructible(float initialX, float initialY, 
			float atlasX, float atlasY, float intialVelocityX, float intialVelocityY, float mass){
		super(initialX, initialY, mass, atlasX, atlasY);
		physicsObjectVelocity.set(intialVelocityX, intialVelocityY);
	}
}
