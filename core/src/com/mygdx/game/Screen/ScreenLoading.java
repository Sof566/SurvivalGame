package com.mygdx.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Screen.GameScreenManager;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.ResourseManager;

public class ScreenLoading extends Screen {
    private Texture texture;


    public ScreenLoading(GameScreenManager screenManager, ResourseManager resourseManager) {
        super(screenManager, resourseManager);
        texture = new Texture(Gdx.files.internal("images/a1.png"));
        resourseManager.loading();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
    }

    public void render(SpriteBatch batch) {
        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(texture, -(MyGdxGame.SCR_WIDTH/2), -(MyGdxGame.SCR_HEIGHT/2), MyGdxGame.SCR_WIDTH, MyGdxGame.SCR_HEIGHT);
        batch.end();
    }

    @Override
    public void update(float delta) {
        if (resourseManager.isLoad()) {
            screenManager.setScreen(new ScreenMenu(screenManager, resourseManager));
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void inputTap() {

    }

    @Override
    public void dispose() {

    }
}
