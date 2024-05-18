package com.mygdx.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GameInterface.Button;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.ResourseManager;

public class ScreenSetting extends Screen {
    private GameScreenManager screenManager;
    private ResourseManager resourseManager;
    private Texture bcgtexture;
    private Button bttBack, bttVolumePlus, bttVolumeMinus, bttTrans;
    private Music music;


    Vector2 vectorExit = new Vector2(-640, 260);

    protected ScreenSetting(GameScreenManager screenManager, ResourseManager resourseManager, Music music) {
        super(screenManager, resourseManager);

        this.screenManager = screenManager;
        this.resourseManager = resourseManager;
        camera.setToOrtho(false, MyGdxGame.SCR_WIDTH, MyGdxGame.SCR_HEIGHT);

        bcgtexture = resourseManager.getTexture(ResourseManager.bcgSetting);
        bttBack = new Button(resourseManager.getTexture(ResourseManager.txtExit), camera.position.x + vectorExit.x, camera.position.y + vectorExit.y, 200, 100);
        bttVolumePlus = new Button(resourseManager.getTexture(ResourseManager.txtVolumePlus), 200, 400, 100, 100);
        bttVolumeMinus = new Button(resourseManager.getTexture(ResourseManager.txtVolumeMinus), 400, 400, 100, 100);
        bttTrans = new Button(resourseManager.getTexture(ResourseManager.bttTranslTranslationRUS), 100, 100, 100, 50);
        this.music = music;
    }

    @Override
    protected void update(float dt) {
        mousePosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        inputTap();
        camera.update();
        

    }

    @Override
    protected void render(SpriteBatch batch) {
        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(bcgtexture, 0, 0, MyGdxGame.SCR_WIDTH, MyGdxGame.SCR_HEIGHT);
        bttBack.render(batch);
        bttVolumeMinus.render(batch);
        bttVolumePlus.render(batch);
        bttTrans.render(batch);
        batch.end();

        bttBack.update(camera);
        bttVolumePlus.update(camera);
        bttVolumeMinus.update(camera);
        bttTrans.update(camera);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
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
    public void dispose() {

    }

    @Override
    protected void inputTap() {
        mousePosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePosition);
        bttBack.setClickListener(new Button.onClickListener() {
            @Override
            public void click() {
                screenManager.setScreen(new ScreenMenu(screenManager, resourseManager));
            }
        });
        bttVolumePlus.setClickListener(new Button.onClickListener() {
            @Override
            public void click() {
                MyGdxGame.VOLUME += 1;
                if (MyGdxGame.VOLUME > 10) {
                    MyGdxGame.VOLUME = 10;
                }
                music.setVolume(MyGdxGame.VOLUME / 10);
            }
        });
        bttVolumeMinus.setClickListener(new Button.onClickListener() {
            @Override
            public void click() {
                MyGdxGame.VOLUME -= 1;
                if (MyGdxGame.VOLUME < 0) {
                    MyGdxGame.VOLUME = 0;
                }
                music.setVolume(MyGdxGame.VOLUME / 10);
            }
        });

        bttTrans.setClickListener(new Button.onClickListener() {
            @Override
            public void click() {
                if(MyGdxGame.language == 1) {
                    bttTrans.changeTexture(resourseManager.getTexture(ResourseManager.bttTranslTranslationANGL));
                    MyGdxGame.language = 0;
                } else {
                    bttTrans.changeTexture(resourseManager.getTexture(ResourseManager.bttTranslTranslationRUS));
                    MyGdxGame.language = 1;
                }
            }
        });
    }
}
