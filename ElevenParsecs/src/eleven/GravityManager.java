package eleven;

import java.util.ArrayList;
import java.util.LinkedList;

import com.badlogic.gdx.math.Vector2;

public class GravityManager {
	public static float g = 10f;
	
	public GravityManager() {
	}
	
	public void ComputeGravity() {
		float dist, force;
		Vector2 direction = new Vector2();
		
		for (Destructible d1 : Driver.level.destructibles) {
			Vector2 combinedForce = new Vector2();
			
			for (Destructible d2 : Driver.level.destructibles) {
				if (d1 != d2) {
					dist = CollisionHelper.distanceSquared(d1.getPosition2(), d2.getPosition2());
					if (dist > 1) {
						direction = d2.getPosition2().sub(d1.getPosition2()).nor();
						force = (g * d1.getMass() * d2.getMass()) / dist;
						combinedForce.add(direction.scl(force));
					}
				}
			}
			
			d1.addForceToPhysicsObject(combinedForce);
			
			//Now add force to player
			dist = CollisionHelper.distanceSquared(Driver.level.player.getPosition2(), d1.getPosition2());
			direction = d1.getPosition2().sub(Driver.level.player.getPosition2()).nor();
			force = (g * Driver.level.player.getMass() * d1.getMass()) / dist;
			Driver.level.player.addForceToPhysicsObject(new Vector2(direction.x, direction.y).scl(force));
			
			//And player force to d1
			direction = Driver.level.player.getPosition2().sub(d1.getPosition2());
			d1.addForceToPhysicsObject(new Vector2(direction.x, direction.y).scl(force));
			
			//System.gc();
		}
	}
}
