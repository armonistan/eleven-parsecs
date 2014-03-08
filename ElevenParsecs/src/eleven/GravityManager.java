package eleven;

import java.util.ArrayList;

public class GravityManager {

	public static ArrayList<Destructible> destructibles;
	
	public GravityManager() {
		destructibles = new ArrayList<Destructible>();
	}
	
	public void GenerateDestructibles() {
		destructibles.clear();
	}
}
