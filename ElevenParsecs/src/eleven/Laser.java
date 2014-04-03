package eleven;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Laser {
	
	private Vector2 position;
	private Vector2 laserEndPoint;
	private float laserLength;
	private ShapeRenderer shapeBatch;
	
	public Laser(float x, float y, float rotation, float length) {
		this.position = new Vector2(x, y);
		this.laserLength = length;
		this.laserEndPoint = new Vector2((MathUtils.cosDeg(rotation) * this.laserLength) + x, (MathUtils.sinDeg(rotation) * this.laserLength) + y);
		shapeBatch = new ShapeRenderer();
		shapeBatch.setProjectionMatrix(Driver.laserCamera.combined);
	}
	
	public void updatePosition(float x, float y, float rotation) {
		this.position.x = x;
		this.position.y = y;
		this.laserEndPoint.x = (MathUtils.cosDeg(rotation) * this.laserLength) + x;
		this.laserEndPoint.y = (MathUtils.sinDeg(rotation) * this.laserLength) + y;
	}
	
	public Vector2 getLaserPosition() {
		return this.position;
	}
	
	public Vector2 getLaserEndPointPosition() {
		return this.laserEndPoint;
	}
	
	public void drawLaser() {
		shapeBatch.begin(ShapeType.Line);
		shapeBatch.setColor(1, 0, 0, 1);
		shapeBatch.line(position, laserEndPoint);
		shapeBatch.end();
	}
}
