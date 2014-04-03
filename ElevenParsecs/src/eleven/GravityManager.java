package eleven;

import java.util.ArrayList;
import java.util.LinkedList;

import com.badlogic.gdx.math.Vector2;

public class GravityManager {
	public static float g = 1000f;
	private Vector2 direction;
	
	public GravityManager() {
		direction = new Vector2();
	}
	
	//TODO: Remove new Vector2's
	public void ComputeGravity() {
		float dist, force;
		direction.set(0, 0);
		
		for (Destructible d1 : Driver.level.destructibles) {
			for (Destructible d2 : Driver.level.destructibles) {
				if (d1 != d2 && !d1.ignoreTheseCollisions.contains(d2)) {
					dist = CollisionHelper.getDistanceSquared(d1.getOriginPosition().x, d1.getOriginPosition().y,
							d2.getOriginPosition().x, d2.getOriginPosition().y);
					
					if (dist > 1) {
						direction.set(d2.getOriginPosition().x - d1.getOriginPosition().x, 
								d2.getOriginPosition().y - d1.getOriginPosition().y);
						direction.nor();
						force = (g * d1.getMass() * d2.getMass()) / dist;
						d1.addForceToPhysicsObject(direction.x * force, direction.y * force);
					}
				}
			}
			
			//Now add force to player
			dist = CollisionHelper.getDistanceSquared(Driver.level.player.getOriginPosition().x, Driver.level.player.getOriginPosition().y,
					d1.getOriginPosition().x, d1.getOriginPosition().y);
			direction.set(d1.getOriginPosition().x - Driver.level.player.getOriginPosition().x,
					d1.getOriginPosition().y - Driver.level.player.getOriginPosition().y);
			direction.nor();
			force = (g * Driver.level.player.getMass() * d1.getMass()) / dist;
			Driver.level.player.addForceToPhysicsObject(direction.x * force, direction.y * force);
			
			//And player force to d1
			direction.set(Driver.level.player.getOriginPosition().x - d1.getOriginPosition().x,
					Driver.level.player.getOriginPosition().y - d1.getOriginPosition().y);
			direction.nor();
			d1.addForceToPhysicsObject(direction.x * force, direction.y * force);
		}
	}
}
