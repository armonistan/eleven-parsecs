package eleven;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.*;

public class CollisionHelper {
	public static boolean checkCollide(Sprite caller, Sprite calledByCaller){
		float distance = (float)Math.sqrt(Math.pow(caller.getX() - calledByCaller.getX(), 2) + 
				Math.pow(caller.getY() - calledByCaller.getY(), 2));
		
		return (distance < 30);
	}
}
