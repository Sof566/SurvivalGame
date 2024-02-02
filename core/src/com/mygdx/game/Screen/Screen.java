package com.mygdx.game.Screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.ResourseManager;

public abstract class Screen implements com.badlogic.gdx.Screen{
    protected GameScreenManager screenManager;
    protected ResourseManager resourseManager;
    protected OrthographicCamera camera;
    public Viewport viewport;
    protected Vector3 mousePosition;
    protected Vector2 sizeScreen;
    protected Screen(GameScreenManager screenManager, ResourseManager resourseManager) {
        this.resourseManager = resourseManager;
        this.screenManager = screenManager;
        camera = new OrthographicCamera();
        viewport = new FitViewport(MyGdxGame.SCR_WIDTH, MyGdxGame.SCR_HEIGHT, camera);
        mousePosition = new Vector3();
        sizeScreen = new Vector2(MyGdxGame.SCR_WIDTH, MyGdxGame.SCR_HEIGHT);
    }

    protected abstract void update(float dt);
    protected abstract void render(SpriteBatch batch);
    public abstract void dispose();
    protected abstract void inputTap();

}
