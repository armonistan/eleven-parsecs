package eleven;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.*;

public class CollisionHelper {

	private static int callerIndex = 0;
	private static int calledIndex = 0;
	
	public static boolean checkCollide(Sprite caller, Sprite calledByCaller){
		float dist = getDistance(new Vector2(caller.getX(), caller.getY()), 
				new Vector2(calledByCaller.getX(), calledByCaller.getY()));
		
		return (dist < 40);
	}
	
	public static boolean checkCollideSAT(Polygon caller, Polygon calledByCaller){
		getClosestCorners(caller, calledByCaller);
		
		int i = 0;
		if(callerIndex + 1 > caller.getVertices().length)
			i++;
		if(calledIndex + 1 > calledByCaller.getVertices().length)
			i++;
		
		Vector2 callerCorner = new Vector2(caller.getVertices()[callerIndex], caller.getVertices()[callerIndex+1]);
		Vector2 callerCornerA;
		Vector2 callerCornerB;
		
		if(callerIndex+2 == caller.getVertices().length){
			callerCornerA = new Vector2(caller.getVertices()[callerIndex-2], caller.getVertices()[callerIndex-1]);
			callerCornerB = new Vector2(caller.getVertices()[0], caller.getVertices()[1]);
		}
		else if(callerIndex == 0){
			callerCornerA = new Vector2(caller.getVertices()[caller.getVertices().length-2], caller.getVertices()[caller.getVertices().length-1]);
			callerCornerB = new Vector2(caller.getVertices()[callerIndex + 2], caller.getVertices()[callerIndex + 3]);
		}
		else{
			callerCornerA = new Vector2(caller.getVertices()[callerIndex-2], caller.getVertices()[callerIndex-1]);
			callerCornerB = new Vector2(caller.getVertices()[callerIndex+2], caller.getVertices()[callerIndex+3]);
		}
		
		Vector2 calledCorner = new Vector2(calledByCaller.getVertices()[calledIndex], calledByCaller.getVertices()[calledIndex+1]);
		Vector2 calledCornerA;
		Vector2 calledCornerB;
		
		if(calledIndex+2 == calledByCaller.getVertices().length){
			calledCornerA = new Vector2(calledByCaller.getVertices()[calledIndex-2], calledByCaller.getVertices()[calledIndex-1]);
			calledCornerB = new Vector2(calledByCaller.getVertices()[0], calledByCaller.getVertices()[1]);
		}
		else if(calledIndex == 0){
			calledCornerA = new Vector2(calledByCaller.getVertices()[calledByCaller.getVertices().length-2], calledByCaller.getVertices()[calledByCaller.getVertices().length-1]);
			calledCornerB = new Vector2(calledByCaller.getVertices()[calledIndex + 2], calledByCaller.getVertices()[calledIndex + 3]);
		}
		else{
			calledCornerA = new Vector2(calledByCaller.getVertices()[calledIndex-2], calledByCaller.getVertices()[calledIndex-1]);
			calledCornerB = new Vector2(calledByCaller.getVertices()[calledIndex+2], calledByCaller.getVertices()[calledIndex+3]);
		}
		
		boolean doesACross = doLineSegmentsIntersect(callerCornerA, callerCorner, calledCornerA, calledCorner) || doLineSegmentsIntersect(callerCornerA, callerCorner, calledCornerB, calledCorner);
		boolean doesBCross = doLineSegmentsIntersect(callerCornerB, callerCorner, calledCornerB, calledCorner) || doLineSegmentsIntersect(callerCornerB, callerCorner, calledCornerA, calledCorner);
		
		if(doesACross || doesBCross)
			return true;
		else
			return false;
	}
	
	public static float getDistance(Vector2 position1, Vector2 position2) {
		return position1.dst(position2);
	}

	public static float distanceSquared(Vector2 a, Vector2 b){
		return a.dst2(b);
	}
	
	private static void getClosestCorners(Polygon caller, Polygon calledByCaller){
		float minDistance = 100000;
				
		for(int i = 0; i < caller.getVertices().length; i+=2){
			Vector2 testCorner = new Vector2(caller.getVertices()[i], caller.getVertices()[i+1]);
			for(int j = 0; j < calledByCaller.getVertices().length; j+=2){
				Vector2 testCorner2 = new Vector2(calledByCaller.getVertices()[j], calledByCaller.getVertices()[j+1]);
				float distance = CollisionHelper.getDistance(testCorner, testCorner2);
				if(distance < minDistance){
					minDistance = distance;
					callerIndex = i;
					calledIndex = j;
				}
			}
		}
		
		return;
	}
	
	private static boolean doLineSegmentsIntersect(Vector2 callerStart, Vector2 callerEnd, Vector2 calledStart, Vector2 calledEnd){
		float callerSlope = (callerStart.y - callerEnd.y)/(callerStart.x - callerEnd.x);
		float callerConstant = callerStart.y - (callerStart.x * callerSlope);
		
		float calledSlope = (calledStart.y - calledEnd.y)/(calledStart.x - calledEnd.x);
		float calledConstant = calledStart.y - (calledStart.x * calledSlope);
		
		float x = (callerConstant - calledConstant)/(calledSlope - callerSlope);
		
		if((callerEnd.x >= callerStart.x)&&(x <= callerEnd.x)&&(x >= callerStart.x)){
			if((calledEnd.x >= calledStart.x)&&(x <= calledEnd.x)&&(x >= calledStart.x))
				return true;
			else if((calledEnd.x <= calledStart.x)&&(x >= calledEnd.x)&&(x <= calledStart.x))
				return true;
			else
				return false;
		}
		else if((callerEnd.x <= callerStart.x)&&(x >= callerEnd.x)&&(x <= callerStart.x)){
			if((calledEnd.x >= calledStart.x)&&(x <= calledEnd.x)&&(x >= calledStart.x))
				return true;
			else if((calledEnd.x <= calledStart.x)&&(x >= calledEnd.x)&&(x <= calledStart.x))
				return true;
			else
				return false;
		}
		else
			return false;
	
	}
}
