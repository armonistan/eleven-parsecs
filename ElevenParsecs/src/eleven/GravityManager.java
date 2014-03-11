package eleven;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

public class GravityManager {

	ArrayList<Destructible> destructibles;
	public static float g = 1000f;
	
	public GravityManager() {
		this.destructibles = new ArrayList<Destructible>();
	}
	
	public void ClearDestructibles() {
		this.destructibles.clear();
	}
	
	public void ComputeGravity() {
		float dist, force;
		Vector2 direction = new Vector2();
		for (Destructible d1 : this.destructibles) {
			for (Destructible d2 : this.destructibles) {
				//temp
				//CollisionHelper.checkCollideSAT(d1.getPolygon(), d2.getPolygon());
				if (!d1.equals(d2)) {
					dist = CollisionHelper.getDistance(d1.getPosition2(), d2.getPosition2());
					if (dist > 1) {
						direction = d2.getPosition().sub(d1.getPosition()).nor();
						force = (g * d1.getMass() * d2.getMass()) / (dist * dist);
						d1.addForceToPhysicsObject(direction.scl(force));
					}
				}
			}
		}
	}
	
	public void AddDestructible(Destructible newDestructible) {
		this.destructibles.add(newDestructible);
	}
	
	public ArrayList<Destructible> GetDestructibles() {
		return this.destructibles;
	}
}
