package eleven;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.*;

public class CollisionHelper {

	private static int callerIndex = 0;
	private static int calledIndex = 0;
	
	public static boolean checkCollide(Sprite caller, Sprite calledByCaller){
		float dist = getDistanceSquared(caller.getX(), caller.getY(), 
				calledByCaller.getX(), calledByCaller.getY());
		
		return (dist < 32f * 32f);
	}
	
	public static boolean checkCollideSAT(Polygon caller, Polygon calledByCaller){
		getClosestCorners(caller, calledByCaller);
		
		int i = 0;
		if(callerIndex + 1 > caller.getVertices().length)
			i++;
		if(calledIndex + 1 > calledByCaller.getVertices().length)
			i++;
		
		float callerCornerX = caller.getVertices()[callerIndex];
		float callerCornerY = caller.getVertices()[callerIndex+1];
		float callerCornerAX;
		float callerCornerAY;
		float callerCornerBX;
		float callerCornerBY;
		
		if(callerIndex+2 == caller.getVertices().length){
			callerCornerAX = caller.getVertices()[callerIndex-2];
			callerCornerAY = caller.getVertices()[callerIndex-1];
			callerCornerBX = caller.getVertices()[0];
			callerCornerBY = caller.getVertices()[1];
		}
		else if(callerIndex == 0){
			callerCornerAX = caller.getVertices()[caller.getVertices().length-2];
			callerCornerAY = caller.getVertices()[caller.getVertices().length-1];
			callerCornerBX = caller.getVertices()[callerIndex + 2];
			callerCornerBY = caller.getVertices()[callerIndex + 3];
		}
		else{
			callerCornerAX = caller.getVertices()[callerIndex-2];
			callerCornerAY = caller.getVertices()[callerIndex-1];
			callerCornerBX = caller.getVertices()[callerIndex+2];
			callerCornerBY = caller.getVertices()[callerIndex+3];
		}
		
		float calledCornerX = calledByCaller.getVertices()[calledIndex];
		float calledCornerY = calledByCaller.getVertices()[calledIndex+1];
		float calledCornerAX;
		float calledCornerAY;
		float calledCornerBX;
		float calledCornerBY;
		
		if(calledIndex+2 == calledByCaller.getVertices().length){
			calledCornerAX = calledByCaller.getVertices()[calledIndex-2];
			calledCornerAY = calledByCaller.getVertices()[calledIndex-1];
			calledCornerBX = calledByCaller.getVertices()[0];
			calledCornerBY = calledByCaller.getVertices()[1];
		}
		else if(calledIndex == 0){
			calledCornerAX = calledByCaller.getVertices()[calledByCaller.getVertices().length-2];
			calledCornerAY = calledByCaller.getVertices()[calledByCaller.getVertices().length-1];
			calledCornerBX = calledByCaller.getVertices()[calledIndex + 2];
			calledCornerBY = calledByCaller.getVertices()[calledIndex + 3];
		}
		else{
			calledCornerAX = calledByCaller.getVertices()[calledIndex-2];
			calledCornerAY = calledByCaller.getVertices()[calledIndex-1];
			calledCornerBX = calledByCaller.getVertices()[calledIndex+2];
			calledCornerBY = calledByCaller.getVertices()[calledIndex+3];
		}
		
		boolean doesACross = doLineSegmentsIntersect(callerCornerAX, callerCornerAY, callerCornerX, callerCornerY, calledCornerAX, calledCornerAY, calledCornerX, calledCornerY) 
				|| doLineSegmentsIntersect(callerCornerAX, callerCornerAY, callerCornerX, callerCornerY, calledCornerBX, calledCornerBY, calledCornerX, calledCornerY);
		
		boolean doesBCross = doLineSegmentsIntersect(callerCornerBX, callerCornerBY, callerCornerX, callerCornerY, calledCornerBX, calledCornerBY, calledCornerX, calledCornerY)
				|| doLineSegmentsIntersect(callerCornerBX, callerCornerBY, callerCornerX, callerCornerY, calledCornerAX, calledCornerAY, calledCornerX, calledCornerY);
		
		if(doesACross || doesBCross)
			return true;
		else
			return false;
	}
	
	public static float getDistance(float x1, float y1, float x2, float y2) {
		return (float)Math.sqrt(getDistanceSquared(x1, y1, x2, y2));
	}
		
	public static float getDistanceSquared(float x1, float y1, float x2, float y2){
		return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
	}
	
	private static void getClosestCorners(Polygon caller, Polygon calledByCaller){
		float minDistance = Float.MAX_VALUE;
				
		for(int i = 0; i < caller.getVertices().length; i+=2){
			float testCornerX = caller.getVertices()[i];
			float testCornerY = caller.getVertices()[i+1];
			
			for(int j = 0; j < calledByCaller.getVertices().length; j+=2){
				float testCorner2X = calledByCaller.getVertices()[j];
				float testCorner2Y = calledByCaller.getVertices()[j+1];
				
				float distance = CollisionHelper.getDistanceSquared(testCornerX, testCornerY, testCorner2X, testCorner2Y);
				if(distance < minDistance){
					minDistance = distance;
					callerIndex = i;
					calledIndex = j;
				}
			}
		}
		
		return;
	}
	
	private static boolean doLineSegmentsIntersect(float callerStartX, float callerStartY, float callerEndX, float callerEndY, float calledStartX, float calledStartY, float calledEndX, float calledEndY){
		float callerSlope = (callerStartY - callerEndY)/(callerStartX - callerEndX);
		float callerConstant = callerStartY - (callerStartX * callerSlope);
		
		float calledSlope = (calledStartY - calledEndY)/(calledStartX - calledEndX);
		float calledConstant = calledStartY - (calledStartX * calledSlope);
		
		float x = (callerConstant - calledConstant)/(calledSlope - callerSlope);
		
		if((callerEndX >= callerStartX)&&(x <= callerEndX)&&(x >= callerStartX)){
			if((calledEndX >= calledStartX)&&(x <= calledEndX)&&(x >= calledStartX))
				return true;
			else if((calledEndX <= calledStartX)&&(x >= calledEndX)&&(x <= calledStartX))
				return true;
			else
				return false;
		}
		else if((callerEndX <= callerStartX)&&(x >= callerEndX)&&(x <= callerStartX)){
			if((calledEndX >= calledStartX)&&(x <= calledEndX)&&(x >= calledStartX))
				return true;
			else if((calledEndX <= calledStartX)&&(x >= calledEndX)&&(x <= calledStartX))
				return true;
			else
				return false;
		}
		else
			return false;
	
	}
}
