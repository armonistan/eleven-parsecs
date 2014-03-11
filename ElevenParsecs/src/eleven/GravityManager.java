package eleven;

import java.util.ArrayList;
import java.util.LinkedList;

import com.badlogic.gdx.math.Vector2;

public class GravityManager {
	LinkedList<Destructible> destructiblesToRemove;
	public static float g = 100f;
	
	public GravityManager() {
		this.destructiblesToRemove = new LinkedList<Destructible>();
	}
	
	public void ComputeGravity() {
		float dist, force;
		Vector2 direction = new Vector2();
		
		for (Destructible d1 : Driver.level.destructibles) {
			Vector2 combinedForce = new Vector2();
			
			for (Destructible d2 : Driver.level.destructibles) {
				if (d1 != d2) {
					dist = CollisionHelper.distanceSquared(d1.GetPosition(), d2.GetPosition());
					if (dist > 1) {
						direction = d2.GetPosition().sub(d1.GetPosition()).nor();
						force = (g * d1.GetMass() * d2.GetMass()) / dist;
						combinedForce.add(direction.scl(force));
					}
				}
			}
			
			d1.addForce(combinedForce);
		}
	public void RemoveDestructible(Destructible destroy) {
		this.destructiblesToRemove.add(destroy);
	}
	
	}
	
	public LinkedList<Destructible> GetDestructiblesToRemove() {
		return this.destructiblesToRemove;
	}
	
	public void CollectDestructibles() {
		for (Destructible d : this.destructiblesToRemove) {
			this.destructibles.remove(d);
		}
		this.destructiblesToRemove.clear();
	}
}
