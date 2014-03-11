package eleven;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;

import java.util.*;

public class LevelGenerator {
	int mapSize;
	TiledMap map;
	Texture tiles;
	TileRegion space;
	TileRegion stars;
	TileRegion destructibles;
	Random randomGenerator;
	
	private class TileRegion {
		int startX, startY, width, length;
		Random rand;
		
		public TileRegion(int startX, int startY, int width, int length) {
			this.startX = startX;
			this.startY = startY;
			this.width = width;
			this.length = length;
			rand = new Random();
		}
		
		public int GetRandomX() {
			return this.rand.nextInt(this.width + 1) + this.startX;
		}
		
		public int GetRandomY() {
			return this.rand.nextInt(this.length + 1) + this.startY;
		}
		
	}
	
	public LevelGenerator(int mapSize, int numDestructibles) {
		this.mapSize = mapSize;
		
		this.space = new TileRegion(0, 6, 0, 0);
		this.stars = new TileRegion(1, 6, 1, 1);
		this.destructibles = new TileRegion(2, 0, 2, 0);
		
		this.tiles = new Texture(Gdx.files.internal("data/spriteAtlas.png"));
		this.map = new TiledMap();
		
		this.randomGenerator = new Random();
		
		this.RandomizeField();
		this.RandomizeDestructibles(numDestructibles);
		
		System.gc();
	}
	
	public TiledMap getMap() {
		return this.map;
	}
	
	private void RandomizeField(){
		TextureRegion[][] splitTiles = TextureRegion.split(this.tiles, 32, 32);
		MapLayers layers = this.map.getLayers();
		TiledMapTileLayer spaceLayer = new TiledMapTileLayer(this.mapSize, this.mapSize, 32, 32);
		TiledMapTileLayer starsLayer = new TiledMapTileLayer(this.mapSize, this.mapSize, 32, 32);
		
		for (int x = 0; x < this.mapSize; x++) {
			for (int y = 0; y < this.mapSize; y++) {
				int ty = this.space.GetRandomY();
				int tx = this.space.GetRandomX();
				
				int sy = this.stars.GetRandomY();
				int sx = this.stars.GetRandomX();
				
				Cell cell = new Cell();
				cell.setTile(new StaticTiledMapTile(splitTiles[ty][tx]));
				spaceLayer.setCell(x, y, cell);
				
				Cell sCell = new Cell();
				sCell.setTile(new StaticTiledMapTile(splitTiles[sy][sx]));
				starsLayer.setCell(x, y, sCell);
			}
		}
		
		layers.add(spaceLayer);
		layers.add(starsLayer);
		
	}

	private void RandomizeDestructibles(int numDestructibles) {
		//Driver.gravity.ClearDestructibles();
		
		Vector2 destPosition = new Vector2();
		Vector2 destVelocity = new Vector2(0, 0);
		Vector2 destImg = new Vector2(0, 0);
		for (int i = 0; i < numDestructibles; i++) {
			destPosition.x = Driver.level.playerStartX + randomGenerator.nextFloat() * 1000 - 500;
			destPosition.y = Driver.level.playerStartY + randomGenerator.nextFloat() * 1000 - 500;
			
			//destVelocity.x = randomGenerator.nextFloat() * 100 * (randomGenerator.nextFloat() > .5 ? 1 : -1);
			//destVelocity.y = randomGenerator.nextFloat() * 100 * (randomGenerator.nextFloat() > .5 ? 1 : -1);
			
			destImg.x = destructibles.GetRandomX();
			
			Driver.level.destructibles.add(new Destructible(destPosition, destImg, destVelocity, 10));
		}
	}
}