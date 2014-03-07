package eleven;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Eleven Parsecs";
		cfg.useGL20 = false;
		cfg.width = 480;
		cfg.height = 480;
		cfg.resizable = false;
		
		new LwjglApplication(new Driver(), cfg);
	}
}
