package com.mygdx.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Animator;
import com.mygdx.game.Button;
import com.mygdx.game.Entity.Player;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.ResourseManager;

public class ScreenMenu extends Screen{
    GameScreenManager screenManager;
    ResourseManager resourseManager;
    Texture texture;
    Music music;
    Animation animation;

    Vector2 vectorExit = new Vector2(-640,260);
    Vector2 vectorSettings = new Vector2(520, 240);
    Vector2 vectorStart = new Vector2(-150, -75);
    Button bttExit, bttSettings, bttPlay;

    public ScreenMenu(GameScreenManager screenManager, ResourseManager resourseManager) {
        super(screenManager, resourseManager);
        camera.setToOrtho(false, MyGdxGame.SCR_WIDTH, MyGdxGame.SCR_HEIGHT);
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        this.screenManager = screenManager;
        this.resourseManager = resourseManager;

        texture = resourseManager.getTexture(ResourseManager.bcgMenu);
        bttExit = new Button(resourseManager.getTexture(ResourseManager.txtExit), camera.position.x+ vectorExit.x, camera.position.y+vectorExit.y, 200, 100);
        bttSettings = new Button(resourseManager.getTexture(ResourseManager.txtSettings), camera.position.x+ vectorSettings.x, camera.position.y+vectorSettings.y, 100,100);
        bttPlay = new Button(resourseManager.getTexture(ResourseManager.bttPlay), camera.position.x+ vectorStart.x, camera.position.y+ vectorStart.y, 300, 150);
        music = resourseManager.getMusic(ResourseManager.music);
        music.setLooping(true);
        music.setVolume(MyGdxGame.VOLUME);
        music.play();

    }

    @Override
    protected void update(float dt) {
        mousePosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        inputTap();
        camera.update();
    }

    @Override
    protected void render(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(texture, 0,0, MyGdxGame.SCR_WIDTH, MyGdxGame.SCR_HEIGHT);
        bttSettings.render(batch);
        bttExit.render(batch);
        bttPlay.render(batch);
        batch.end();

        bttExit.update(camera);
        bttSettings.update(camera);
        bttPlay.update(camera);
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
        resourseManager.reload();
    }

    @Override
    public void hide() {

    }

    @Override
    public void inputTap() {
        mousePosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePosition);
        bttExit.setClickListener(new Button.onClickListener() {
            @Override
            public void click() {
                music.stop();
                Gdx.app.exit();
            }
        });
        bttSettings.setClickListener(new Button.onClickListener() {
            @Override
            public void click() {
                music.stop();
                screenManager.setScreen(new ScreenSetting(screenManager, resourseManager));
            }
        });
        bttPlay.setClickListener(new Button.onClickListener() {
            @Override
            public void click() {
                screenManager.setScreen(new ScreenPlay(screenManager, resourseManager));
            }
        });
    }

    @Override
    public void dispose() {
        texture.dispose();
        bttExit.dispose();
        bttSettings.dispose();
        bttPlay.dispose();
        music.dispose();
    }
}
