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
		super(initialX, initialY, mass, atlasX, atlasY, intialVelocityX, intialVelocityY);
		physicsObjectPolygon.setVertices(setPhysicsObjectPolygonVertices((int)atlasX));
	}
	
	@Override
	protected float[] setPhysicsObjectPolygonVertices(int image){
		float[] vertices = null;
		
		switch(image){
			case 2:
				vertices = new float[]{(72%32)-16, 16-0, 
						83%32-16, 16-8, 
						91%32-16, 16-19, 
						85%32-16, 16-30, 
						70%32-16, 16-25, 
						69%32-16, 16-17, 
						67%32-16, 16-6};
			break;
			case 3:
				vertices = new float[]{(103%32)-16, 16-3,
						(113%32)-16, 16-3,
						(118%32)-16, 16-5,
						(120%32)-16, 16-5,
						(121%32)-16, 16-9,
						(123%32)-16, 16-15,
						(126%32)-16, 16-19,
						(126%32)-16, 16-25,
						(122%32)-16, 16-30,
						(112%32)-16, 16-30,
						(105%32)-16, 16-29,
						(100%32)-16, 16-26,
						(100%32)-16, 16-20,
						(101%32)-16, 16-16,
						(99%32)-16, 16-12,
						(99%32)-16, 16-8};
			break;
			case 4:
				vertices = new float[]{(140%32)-16, 16-2,
						(147%32)-16, 16-2,
						(153%32)-16, 16-4,
						(155%32)-16, 16-7,
						(156%32)-16, 16-11,
						(156%32)-16, 16-17,
						(155%32)-16, 16-22,
						(151%32)-16, 16-27,
						(139%32)-16, 16-27,
						(133%32)-16, 16-21,
						(131%32)-16, 16-16,
						(131%32)-16, 16-10,
						(134%32)-16, 16-5};
			break;
		}
		
		for(int i = 0; i < vertices.length; i++){
			if(i%2==0)
				vertices[i] += this.getPosition2().x;
			else
				vertices[i] += this.getPosition2().y;
		}
		
		return vertices;
	}
		
	public void Destroy() {
		for (int i = 0; i < this.physicsObjectMass; i++) {
			Driver.level.AddResource(physicsObject.getX() - 10 + Driver.random.nextFloat() * 20, physicsObject.getY() - 10 + Driver.random.nextFloat() * 10);
		}
		
		Driver.level.destructiblesToDestroy.add(this);
	}
	
	@Override
	public void ObjectUpdate() {	
		if (Driver.random.nextFloat() > .999) {
			// this.Destroy();
		}
	}
}
