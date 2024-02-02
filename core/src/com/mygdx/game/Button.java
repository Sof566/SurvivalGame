package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.math.Rectangle;


public class Button {
    private Texture texture;
    private  Rectangle rectangle;
    private boolean isPressed;
    private onClickListener clickListener;


    public Button(Texture texture, float x, float y, float width, float height) {
        this.texture = texture;
        this.rectangle = new Rectangle(x, y, width, height);
        this.isPressed = false;
    }

    public void setClickListener(onClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void update(Camera camera) {
        Vector3 plaseClick = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        plaseClick = camera.unproject(plaseClick);
        isPressed = rectangle.contains(plaseClick.x, plaseClick.y);
        if(isPressed & Gdx.input.justTouched()) {
            clickListener.click();
        }
    }

    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }

    public interface onClickListener {
        void click();
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }
}
