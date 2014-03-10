package eleven;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class AssetManager {
	private Texture atlas;
	private Vector2 defaultAtlasBound;
	
	public AssetManager(Vector2 atlasBound) {
		atlas = new Texture(Gdx.files.internal("data/spriteAtlas.png"));
		atlas.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		defaultAtlasBound = atlasBound;
	}
	
	public TextureRegion getAtlasRegion(Vector2 atlasPosition) {
		return getAtlasRegion(atlasPosition, new Vector2(1, 1));
	}
	
	public TextureRegion getAtlasRegion(Vector2 atlasPosition, Vector2 atlasSize) {
		return new TextureRegion(atlas, (int)(atlasPosition.x * defaultAtlasBound.x), (int)(atlasPosition.y * defaultAtlasBound.y),
				(int)(defaultAtlasBound.x * atlasSize.x), (int)(defaultAtlasBound.y * atlasSize.y));
	}
}
