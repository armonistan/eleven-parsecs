package eleven;

import java.util.LinkedList;

import com.badlogic.gdx.math.MathUtils;

public class Destructible extends PhysicsObject{
	
	private int resourcesHeld;
	private int subDestructiblesHeld;
	public LinkedList<Destructible> ignoreTheseCollisions; //It's fine, don't worry
	private int health;
	
	public Destructible(float initialX, float initialY, 
			float atlasX, float atlasY, float intialVelocityX, float intialVelocityY, int mass){
		super(initialX, initialY, mass, atlasX, atlasY, intialVelocityX, intialVelocityY);
		physicsObjectPolygon.setVertices(setPhysicsObjectPolygonVertices((int)atlasX));
		//a variable that tells how much resource the destructible holds
		resourcesHeld = Driver.random.nextInt(3);//arbitrary number atm
		//variable that says how many sub destructibles are made
		subDestructiblesHeld = 2; //arbitrary number
		
		ignoreTheseCollisions = new LinkedList<Destructible>();
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
	
	public void damageDestructible(int damage) {
		this.health -= damage;
	}
	
	public int getHealth() {
		return this.health;
	}
	
	public void destroy() {
		//spawn resources
		for (int i = 0; i < resourcesHeld * getMass() / 10 + 1; i++) {
			Driver.level.addResource(physicsObject.getX() - 10 + Driver.random.nextFloat() * 20, physicsObject.getY() - 10 + Driver.random.nextFloat() * 10);
		}
		
		ignoreTheseCollisions.clear();
		
		if (this.getMass() > 1) {
			//spawn destructibles
			for(int i = 0; i < subDestructiblesHeld; i++){
				float angle = MathUtils.PI2 * (float)i / subDestructiblesHeld + MathUtils.atan2(this.getVelocity().x, this.getVelocity().y);
				float newX = /*MathUtils.cos(angle) * physicsObject.getOriginX() * 2 + */
						physicsObject.getX() - physicsObject.getOriginX();
				float newY = /*MathUtils.sin(angle) * physicsObject.getOriginY() * 2 + */
					physicsObject.getY() - physicsObject.getOriginY();
				float velX = MathUtils.cos(angle) * this.physicsObjectVelocity.len();
				float velY = MathUtils.sin(angle) * this.physicsObjectVelocity.len();
				
				Destructible temp = Driver.level.addDestructible(newX, newY, velX, velY,
						this.getMass() / this.subDestructiblesHeld);
				
				if (temp != null) {
					ignoreTheseCollisions.add(temp);
				}
			}
			
			for (Destructible d : ignoreTheseCollisions) {
				//Sketchy method at the moment.
				for (Destructible otherD : ignoreTheseCollisions) {
					if (d != otherD) {
						d.ignoreTheseCollisions.add(otherD);
					}
				}
			}
		}
		
		//exterminate exterminate exterminate
		Driver.level.destructiblesToDestroy.add(this);
	}
}
