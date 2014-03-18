package eleven;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
public class GUI {
	BitmapFont font;


public GUI()
{
	font = new BitmapFont(Gdx.files.internal("fonts/font.fnt"));
}
public void render(SpriteBatch batch)
{

	int h = Gdx.graphics.getHeight();
	int w = Gdx.graphics.getWidth();
	font.setScale(1f);
	//drawing amount of resources
	batch.begin();
	int rscY = (h/2)-15;
	String score = String.valueOf(Driver.level.player.getResourcesAmt());
	font.draw(batch, score, -10, rscY);
	//drawing player position
	Vector2 playerVector = Driver.level.player.getPosition2();
	String coords = String.valueOf((int)playerVector.x)+","+String.valueOf((int)playerVector.y);
	font.draw(batch, coords, -100, -1*(h/2)+30);
	batch.end();
}

}
