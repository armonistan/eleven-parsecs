package eleven;

import java.util.ArrayList;

public class GravityManager {

	ArrayList<Destructible> destructibles;
	
	public GravityManager() {
		this.destructibles = new ArrayList<Destructible>();
	}
	
	public void ClearDestructibles() {
		this.destructibles.clear();
	}
	
	public void AddDestructible(Destructible newDestructible) {
		this.destructibles.add(newDestructible);
	}
	
	public ArrayList<Destructible> GetDestructibles() {
		return this.destructibles;
	}
}
