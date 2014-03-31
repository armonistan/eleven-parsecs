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
	float radius;
	
	private class TileRegion {
		int startX, startY, width, length;
		
		public TileRegion(int startX, int startY, int width, int length) {
			this.startX = startX;
			this.startY = startY;
			this.width = width;
			this.length = length;
		}
		
		public int GetRandomX() {
			return Driver.random.nextInt(this.width + 1) + this.startX;
		}
		
		public int GetRandomY() {
			return Driver.random.nextInt(this.length + 1) + this.startY;
		}
		
	}
	
	public LevelGenerator(int mapSize, int numDestructibles) {
		this.mapSize = mapSize;
		this.radius = 500;
		
		this.space = new TileRegion(0, 6, 0, 0);
		this.stars = new TileRegion(1, 6, 1, 1);
		this.destructibles = new TileRegion(2, 0, 2, 0);
		
		this.tiles = new Texture(Gdx.files.internal("data/spriteAtlas.png"));
		this.map = new TiledMap();
		
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
		
		float deg2Rad = (float) (Math.PI / 180);
		
		for (int i = 0; i < 360; i++) {
<<<<<<< HEAD
			if (i % 30 == 0) {
=======
			if (i % 40 == 0) {
>>>>>>> c8a0cf338fd3c4f2af954d4cd3f101a63560e0be
				destPosition.x = (float) (Driver.level.playerStartX + (Math.cos(i * deg2Rad) * this.radius));
				destPosition.y = (float) (Driver.level.playerStartY + (Math.sin(i * deg2Rad) * this.radius));
				
				destVelocity.x = (destPosition.y - Driver.level.playerStartY);
				destVelocity.y = -(destPosition.x - Driver.level.playerStartX);

				destVelocity.nor().scl(1);

				destImg.x = this.calculateDestructibleAtalasX(5);
				destImg.y = this.calculateDestructibleAtalasY(5);

				Driver.level.destructibles.add(new Destructible(destPosition.x, destPosition.y, destImg.x, destImg.y, destVelocity.x, destVelocity.y, 10));
			}
		}
<<<<<<< HEAD
	}
}
		/*
		for (int i = 0; i < numDestructibles; i++) {
=======
		
		
		for (int i = 10000; i < numDestructibles; i++) {
>>>>>>> c8a0cf338fd3c4f2af954d4cd3f101a63560e0be
			destPosition.x = Driver.level.playerStartX + Driver.random.nextFloat() * 32000 - 16000;
			destPosition.y = Driver.level.playerStartY + Driver.random.nextFloat() * 32000 - 16000;
			
			//destVelocity.x = randomGenerator.nextFloat() * 100 * (randomGenerator.nextFloat() > .5 ? 1 : -1);
			//destVelocity.y = randomGenerator.nextFloat() * 100 * (randomGenerator.nextFloat() > .5 ? 1 : -1);
			
			destImg.x = destructibles.GetRandomX();
			
			Driver.level.destructibles.add(new Destructible(destPosition.x, destPosition.y, destImg.x, destImg.y, destVelocity.x, destVelocity.y, 10));
		}
<<<<<<< HEAD
		*/
=======
		
		//Driver.level.destructibles.add(new Destructible(Driver.level.playerStartX - 100, Driver.level.playerStartY + 50, 2, destImg.y, 1, 0, 10));
		//Driver.level.destructibles.add(new Destructible(Driver.level.playerStartX + 100, Driver.level.playerStartY + 50, 2, destImg.y, -1, 0, 10));
	}
	
	//logic in, actual numbers to be decided when atlas sheet is complete
	private int calculateDestructibleAtalasX(int mass){
		if(mass < 10)
			return 2;
		else if(mass < 20){
			return 3;
		}
		else
			return 4;
	}
	
	//logic in, actual numbers to be decided when atlas sheet is complete
	private int calculateDestructibleAtalasY(int mass){
		if(mass < 10)
			return 0;
		else
			return 0;
	}
}
>>>>>>> c8a0cf338fd3c4f2af954d4cd3f101a63560e0be
