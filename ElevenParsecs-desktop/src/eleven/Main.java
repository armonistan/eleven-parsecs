package eleven;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Eleven Parsecs";
		cfg.useGL20 = false;
		cfg.width = 1000;
		cfg.height = 700;
		cfg.resizable = false;
		cfg.vSyncEnabled = true;
		//cfg.foregroundFPS = 30;
		new LwjglApplication(new Driver(), cfg);
	}
}