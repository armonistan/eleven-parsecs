package eleven;

import java.util.Random;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Config;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Driver implements ApplicationListener {
	//Static members
	public static AssetManager assets;
	public static LevelManager level;
	public static GravityManager gravity;
	public static GUI gui;//BB
	public static Random random;
	
	public static OrthographicCamera camera;
	public static OrthographicCamera guicamera;
	public static OrthographicCamera laserCamera;
	private SpriteBatch batch;
	private ShapeRenderer shapeBatch;
	//private SpriteBatch txtbatch;//BB
	
	@Override
	public void create() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		random = new Random();
		
		camera = new OrthographicCamera(w, h);
		guicamera = new OrthographicCamera(w,h);//BB
		laserCamera = new OrthographicCamera(w, h);
		batch = new SpriteBatch();
		shapeBatch = new ShapeRenderer();
		gravity = new GravityManager();
		assets = new AssetManager(new Vector2(32, 32));
		level = new LevelManager();
		gui = new GUI();
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void render() {	
		//This clears the screen.
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			//camera.zoom += .1f;
		} else if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
			//camera.zoom -= .1f;
		}
		
		//Set the camera position
		Vector3 lerped = camera.position.lerp(level.player.getPosition3(), 0.2f);
		camera.position.x = lerped.x;
		camera.position.y = lerped.y;
		camera.update();
		
		laserCamera.position.x = lerped.x;
		laserCamera.position.y = lerped.y;
		laserCamera.update();
		
		//Set the GUI camera position
		guicamera.position.x = 0;
		guicamera.position.y = 0;
		guicamera.update();
		
		shapeBatch.setProjectionMatrix(laserCamera.combined);
		
		//Set the camera to our camera.
		batch.setProjectionMatrix(camera.combined);
		level.render(batch);
		
		if (level.player.laser.isLaserActive()) {
			shapeBatch.begin(ShapeType.Line);
			shapeBatch.setColor(1, 0, 0, 1);
			shapeBatch.line(level.player.laser.getLaserPosition(), level.player.laser.getLaserEndPointPosition());
			//shapeBatch.line(0, 0, 100, 100);
			shapeBatch.end();
		}
		
		batch.setProjectionMatrix(guicamera.combined);//BB
		gui.render(batch);//BB
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
