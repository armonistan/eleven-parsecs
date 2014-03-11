package eleven;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
public class GUI {
	CharSequence str = "Hello World!";
	spriteBatch = new SpriteBatch();
	font = new BitmapFont();

	spriteBatch.begin();
	font.draw(spriteBatch, str, 10, 10);
	spriteBatch.end();
	spriteBatch.begin();
	
}
}


