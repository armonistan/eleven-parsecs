package eleven;

import java.util.LinkedList;
import java.util.Random;

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
		
		newRand = new Random();
	public void Destroy() {
		for (int i = 0; i < this.destructibleMass; i++) {
			Driver.level.AddResource(destructible.getX() - 10 + newRand.nextFloat() * 20, destructible.getY() - 10 + newRand.nextFloat() * 10);
		}
		
		Driver.level.destructiblesToDestroy.add(this);
	}
	
		
		if (newRand.nextFloat() > .999) {
			//this.Destroy();
		}
	}
}
