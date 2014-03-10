package eleven;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.*;

public class CollisionHelper {
	public static boolean checkCollide(Sprite caller, Sprite calledByCaller){
		float dist = distance(new Vector2(caller.getX(), caller.getY()), 
				new Vector2(calledByCaller.getX(), calledByCaller.getY()));
		
		return (dist < 30);
	}
	
	public static float distance(Vector2 position1, Vector2 position2) {
		return position1.dst(position2);
	}
}
