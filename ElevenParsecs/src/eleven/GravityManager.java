package eleven;

import java.util.ArrayList;
import java.util.LinkedList;

import com.badlogic.gdx.math.Vector2;

public class GravityManager {
	public static float g = 100f;
	
	public GravityManager() {
	}
	
	public void ComputeGravity() {
		float dist, force;
		Vector2 direction = new Vector2();
		
		for (Destructible d1 : Driver.level.destructibles) {
				//temp
				//CollisionHelper.checkCollideSAT(d1.getPolygon(), d2.getPolygon());
			Vector2 combinedForce = new Vector2();
			
			for (Destructible d2 : Driver.level.destructibles) {
				if (d1 != d2) {
					dist = CollisionHelper.distanceSquared(d1.getPosition(), d2.getPosition());
					if (dist > 1) {
						direction = d2.getPosition().sub(d1.getPosition()).nor();
						force = (g * d1.getMass() * d2.getMass()) / dist;
						combinedForce.add(direction.scl(force));
					}
				}
			}
			
			d1.addForceToPhysicsObject(combinedForce);
		}	
	}
}
