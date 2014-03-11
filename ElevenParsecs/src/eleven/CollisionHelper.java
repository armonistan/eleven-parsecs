package eleven;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.*;

public class CollisionHelper {
	public static boolean checkCollide(Sprite caller, Sprite calledByCaller){
		float dist = getDistance(new Vector2(caller.getX(), caller.getY()), 
				new Vector2(calledByCaller.getX(), calledByCaller.getY()));
		
		return (dist < 30);
	}
	
	public static float getDistance(Vector2 position1, Vector2 position2) {
		return position1.dst(position2);
	}
	
	public static boolean checkCollideSAT(Polygon caller, Polygon calledByCaller){
		getClosestCornerToOtherPolygon(caller, calledByCaller);
		
		return false;
	}
	
	private static Vector2 getDeltaVectorFromAToB(Vector2 a, Vector2 b){
		return a.sub(b);
	}
	
	private static Vector2 getClosestCornerToOtherPolygon(Polygon caller, Polygon calledByCaller){
		Vector2 closestCorner = new Vector2();
		
		for(float corner: caller.getTransformedVertices())
			System.out.println(corner);
		System.out.println(caller.getOriginX());
		
		return closestCorner;
	}
}
