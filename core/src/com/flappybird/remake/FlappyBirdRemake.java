package com.flappybird.remake;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.flappybird.remake.states.GameStateManager;
import com.flappybird.remake.states.MenuState;

public class FlappyBirdRemake extends ApplicationAdapter {

    public static final int WIDTH = 480;
    public static final int HEIGHT = 800;

    public static final String TITLE = "Flappy Bird";

    private GameStateManager gameStateManager;

    /*These are really large files. Therefore, it'd be a better option to have only one in a game.*/
	private SpriteBatch batch;

	private Music music;

	@Override
	public void create () {
	    gameStateManager = new GameStateManager();
		batch = new SpriteBatch();

		/*Just clears the RGB and alpha color.*/
        Gdx.gl.glClearColor(1, 0, 0, 1);

        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music.setLooping(true);
        music.setVolume(0.1f); /*Here 1 means 100%; and 0.1 means 10% and so on.*/
        music.play();

        gameStateManager.push(new MenuState(gameStateManager));
	}

	@Override
	public void render () {

        /*Clears the screen and renders it again.*/
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		/*Always remember, a game's state first need*/
		gameStateManager.update(Gdx.graphics.getDeltaTime());
		gameStateManager.render(batch);
	}

	@Override
	public void dispose () {
		batch.dispose();
		music.dispose();
	}
}
