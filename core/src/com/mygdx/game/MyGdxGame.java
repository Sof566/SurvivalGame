package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Screen.GameScreenManager;
import com.mygdx.game.Screen.ScreenLoading;

public class MyGdxGame extends ApplicationAdapter {
	private SpriteBatch spriteBatch;
	private GameScreenManager screenManager;
	private ResourseManager resourseManager;
	public static final int SCR_WIDTH = 1280, SCR_HEIGHT = 720;

	public static float VOLUME = 1;
	
	@Override
	public void create () {
		spriteBatch = new SpriteBatch();
		screenManager = new GameScreenManager();
		resourseManager = new ResourseManager();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.input.setCatchBackKey(true);
		screenManager.pushScreen(new ScreenLoading(screenManager, resourseManager));

	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		screenManager.update(Gdx.graphics.getDeltaTime());
		screenManager.render(spriteBatch);
	}
	
	@Override
	public void dispose () {
		screenManager.dispose();
		spriteBatch.dispose();
		resourseManager.dispose();
		super.dispose();
	}

	@Override
	public void pause() {
		screenManager.pause();
		super.pause();
	}
}
