package eleven;

import java.util.ArrayList;
import java.util.LinkedList;

import com.badlogic.gdx.math.Vector2;

public class GravityManager {
	public static float g = 1000f;
	
	public GravityManager() {
	}
	
	//TODO: Remove new Vector2's
	public void ComputeGravity() {
		float dist, force;
		Vector2 direction = new Vector2();
		
		for (Destructible d1 : Driver.level.destructibles) {
				//temp
				//CollisionHelper.checkCollideSAT(d1.getPolygon(), d2.getPolygon());
			
			for (Destructible d2 : Driver.level.destructibles) {
				if (d1 != d2) {
					dist = CollisionHelper.distanceSquared(d1.getOriginPosition(), d2.getOriginPosition());
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
			dist = CollisionHelper.distanceSquared(Driver.level.player.getOriginPosition(), d1.getOriginPosition());
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
