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
				if (!d1.equals(d2)) {
					dist = CollisionHelper.distance(d1.GetPosition(), d2.GetPosition());
					if (dist > 1) {
						direction = d2.GetPosition().sub(d1.GetPosition()).nor();
						force = (g * d1.GetMass() * d2.GetMass()) / (dist * dist);
						d1.addForce(direction.scl(force));
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
