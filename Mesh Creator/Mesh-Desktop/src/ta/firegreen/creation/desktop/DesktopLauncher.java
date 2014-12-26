package ta.firegreen.creation.desktop;

import java.awt.Dimension;
import java.awt.Toolkit;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import ta.firegreen.creation.creator;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		{
		final Dimension desktopDimension = Toolkit.getDefaultToolkit().getScreenSize();
		config.x = desktopDimension.width/2;
		config.y = desktopDimension.height/4;
		}
		new LwjglApplication(new creator(), config);
	}
}
