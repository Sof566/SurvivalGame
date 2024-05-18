package com.mygdx.game.GameInterface;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.ResourseManager;

public class TouchPad {

    private OrthographicCamera camera;
    private Touchpad touchpad;
    ResourseManager resourseManager;

    public TouchPad(OrthographicCamera camera, ResourseManager resourseManager) {
        this.camera = camera;
        this.resourseManager = resourseManager;

        Viewport viewport = new FitViewport(MyGdxGame.SCR_WIDTH, MyGdxGame.SCR_HEIGHT, camera);

        Skin touchpadSkin = new Skin();
        touchpadSkin.add(ResourseManager.touchBackground, resourseManager.getTexture(ResourseManager.touchBackground));
        touchpadSkin.add(ResourseManager.touchKnob, resourseManager.getTexture(ResourseManager.touchKnob));
        Touchpad.TouchpadStyle touchpadStyle = new Touchpad.TouchpadStyle();
        Drawable touchBackground = touchpadSkin.getDrawable(ResourseManager.touchBackground);
        Drawable touchKnob = touchpadSkin.getDrawable(ResourseManager.touchKnob);
        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;
        touchpad = new Touchpad(10, touchpadStyle);

        Stage stage = new Stage(viewport, new SpriteBatch());
        stage.addActor(touchpad);
        Gdx.input.setInputProcessor(stage);

    }

    public Vector2 getKnobPercentWalk(){
        return new Vector2(touchpad.getKnobPercentX(), touchpad.getKnobPercentY());
    }

    public void render(SpriteBatch batch) {
        batch.begin();
        touchpad.setBounds(camera.position.x - camera.viewportWidth / 2 + 30,
                camera.position.y - camera.viewportHeight / 2 + 30, 270, 270);

        touchpad.draw(batch, 1);
        batch.end();
    }
}