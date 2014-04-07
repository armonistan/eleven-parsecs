package eleven;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

public class Laser {
	
	private Vector2 position;
	private Vector2 laserEndPoint;
	private float laserLength;
	private ShapeRenderer shapeBatch;
	private int lineSegments;
	private int segmentLength;
	private Vector2 testPoint;
	private Vector2 rayCast;
	private boolean active;
	private boolean colliding;
	private float rotation;
	
	public Laser(float x, float y, float rotation, float length, int segments) {
		this.position = new Vector2(x, y);
		this.laserLength = length;
		this.laserEndPoint = new Vector2((MathUtils.cosDeg(rotation) * this.laserLength) + x, (MathUtils.sinDeg(rotation) * this.laserLength) + y);
		shapeBatch = new ShapeRenderer();
		shapeBatch.setProjectionMatrix(Driver.laserCamera.combined);
		this.lineSegments = segments;
		this.segmentLength = (int) (this.laserLength / this.lineSegments);
		this.testPoint = new Vector2();
		this.rayCast = new Vector2();
		this.active = false;
		this.colliding = false;
	}
	
	public void updatePosition(float x, float y, float rotation) {
		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			this.active = true;
		} else {
			this.active = false;
		}
		this.position.x = x;
		this.position.y = y;
		this.rotation = rotation;
		if (!this.colliding) {
			//this.laserEndPoint.x = (MathUtils.cosDeg(rotation) * this.laserLength) + x;
			//this.laserEndPoint.y = (MathUtils.sinDeg(rotation) * this.laserLength) + y;
		}
	}
	
	public boolean isLaserActive() {
		return this.active;
	}
	
	public Vector2 getLaserPosition() {
		return this.position;
	}
	
	public Vector2 getLaserEndPointPosition() {
		return this.laserEndPoint;
	}
	
	public boolean isLaserWithinPolygon(Polygon p) {
		this.laserEndPoint.x = (MathUtils.cosDeg(rotation) * this.laserLength) + this.position.x;
		this.laserEndPoint.y = (MathUtils.sinDeg(rotation) * this.laserLength) + this.position.y;
		testPoint = this.laserEndPoint.cpy().sub(this.position.cpy()).nor().scl(segmentLength);
		this.rayCast = this.position.cpy();
		for (int i = 0; i < this.lineSegments; i++) {
			this.rayCast.add(testPoint);
			this.laserEndPoint.x = rayCast.x;
			this.laserEndPoint.y = rayCast.y;
			if (p.contains(rayCast.x, rayCast.y)) {
				this.colliding = true;
				return this.colliding;
			}
		}
		this.colliding = false;
		return this.colliding;
	}
	
	public void drawLaser() {
		shapeBatch.begin(ShapeType.Line);
		shapeBatch.setColor(1, 0, 0, 1);
		shapeBatch.line(position, laserEndPoint);
		shapeBatch.end();
	}
}
