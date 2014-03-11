package eleven;

import java.util.ArrayList;
import java.util.LinkedList;

import com.badlogic.gdx.math.Vector2;

public class GravityManager {

	ArrayList<Destructible> destructibles;
	LinkedList<Destructible> destructiblesToRemove;
	public static float g = 100f;
	
	public GravityManager() {
		this.destructibles = new ArrayList<Destructible>();
		this.destructiblesToRemove = new LinkedList<Destructible>();
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
	
	public void RemoveDestructible(Destructible destroy) {
		this.destructiblesToRemove.add(destroy);
	}
	
	public ArrayList<Destructible> GetDestructibles() {
		return this.destructibles;
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
