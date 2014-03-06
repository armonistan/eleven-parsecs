package eleven;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Driver implements ApplicationListener {
	//Static members
	public static AssetManager assets;
	public static LevelManager level;
	
	public static OrthographicCamera camera;
	private SpriteBatch batch;
	
	@Override
	public void create() {		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera(w, h);
		batch = new SpriteBatch();
		
		assets = new AssetManager(new Vector2(32, 32));
		level = new LevelManager();
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void render() {	
		//This clears the screen.
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		//Set the camera position
		Vector3 lerped = camera.position.lerp(level.player.getPosition3(), 0.2f);
		camera.position.x = lerped.x;
		camera.position.y = lerped.y;
		camera.update();
		
		//Set the camera to our camera.
		batch.setProjectionMatrix(camera.combined);
		level.render(batch);
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
