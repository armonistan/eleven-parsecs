package eleven;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
public class GUI {
	BitmapFont font;
	float screenHeight;
	float screenWidth;

	//draw positions
	float playerResourcesX;
	float playerResourcesY;

	float baseResourcesX;
	float baseResourcesY;
	
	float deltaPlayerBaseX;
	float deltaPlayerBaseY;
	
	float playerVelocityX;
	float playerVelocityY;
	
	final float leftX = -4f/10f;
	final float rightX = 2f/10f;
	
	final float topY = 5f/11f;
	final float botY = -5f/11f;
	
	//values taken from player and base
	Vector2 playerVelocity;
	Vector2 deltaPlayerBase;
	int playerResources;
	int baseResources;
	
public GUI()
{
	font = new BitmapFont(Gdx.files.internal("fonts/font.fnt"));
	playerVelocity = new Vector2();
	deltaPlayerBase = new Vector2();
	playerResources = 0;
	baseResources = 0;
	
	screenHeight = Gdx.graphics.getHeight();;
	screenWidth = Gdx.graphics.getWidth();

	//draw positions
	playerResourcesX = screenWidth*leftX;
	playerResourcesY = screenHeight*topY;

	baseResourcesX = screenWidth*rightX;
	baseResourcesY = screenHeight*topY;
	
	deltaPlayerBaseX = screenWidth*leftX;
	deltaPlayerBaseY = screenHeight*botY;
	
	playerVelocityX = screenWidth*rightX;
	playerVelocityY = screenHeight*botY;
}
public void render(SpriteBatch batch)
{
	update();
	font.setScale(1f);
	
	batch.begin();
	
	//drawing amount of resources of player and player
	String score = "Player Resources: " + String.valueOf(playerResources);
	font.draw(batch, score, playerResourcesX, playerResourcesY);
	
	score = "Base Resources: " + String.valueOf(baseResources);
	font.draw(batch, score, baseResourcesX, baseResourcesY);
	
	//drawing player speed
	String coords = "Speed: " + String.valueOf((int)playerVelocity.x)+","+String.valueOf((int)playerVelocity.y);
	font.draw(batch, coords, playerVelocityX, playerVelocityY);
	
	coords = "Distance from Base: " + String.valueOf((int)deltaPlayerBase.x)+","+String.valueOf((int)deltaPlayerBase.y);
	font.draw(batch, coords, deltaPlayerBaseX, deltaPlayerBaseY);
	
	
	batch.end();
}

public void update(){
	//draw x and ys
	screenHeight = Gdx.graphics.getHeight();;
	screenWidth = Gdx.graphics.getWidth();

	//draw positions
	playerResourcesX = screenWidth*leftX;
	playerResourcesY = screenHeight*topY;

	baseResourcesX = screenWidth*rightX;
	baseResourcesY = screenHeight*topY;
	
	deltaPlayerBaseX = screenWidth*leftX;
	deltaPlayerBaseY = screenHeight*botY;
	
	playerVelocityX = screenWidth*rightX;
	playerVelocityY = screenHeight*botY;
	
	//player speed
	playerVelocity.set(Driver.level.player.getVelocity());
	//distance from base
	deltaPlayerBase.set(new Vector2(Driver.level.player.getPosition2().x - Driver.level.base.getOriginPosition().x, 
			Driver.level.player.getPosition2().y - Driver.level.base.getOriginPosition().y));
	//player resources
	playerResources = Driver.level.player.getResourcesAmt();
	//base resources
	baseResources = Driver.level.base.getNumResourcesHeld();
}

}
