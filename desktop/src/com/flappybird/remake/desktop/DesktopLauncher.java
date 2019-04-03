package com.flappybird.remake.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.flappybird.remake.FlappyBirdRemake;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.width = FlappyBirdRemake.WIDTH;
		config.height = FlappyBirdRemake.HEIGHT;

		config.title = FlappyBirdRemake.TITLE;

		new LwjglApplication(new FlappyBirdRemake(), config);
	}
}
