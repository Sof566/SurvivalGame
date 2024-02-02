package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Animator{
    private int frame_cols, frame_rows; //кол-во столбцов, кол-во строк
    Animation animation;
    Texture texture;
    TextureRegion[] animArray;
    float stateTime;
    float frameDuration;
    int width, height;

    public Animator(Texture texture, int frame_cols, int frame_rows, float frameDuration, int width, int height) {
        this.texture = texture;
        this.frame_cols = frame_cols;
        this.frame_rows = frame_rows;
        this.frameDuration = frameDuration;
        this.width = width;
        this.height = height;
        TextureRegion[][] tr = TextureRegion.split(texture, texture.getWidth()/frame_cols, texture.getHeight()/frame_rows);
        animArray = new TextureRegion[frame_cols*frame_rows];
        int index = 0;
        for (int i = 0; i < frame_rows; i++) {
            for (int j = 0; j < frame_cols; j++) {
                animArray[index++] = tr[i][j];
            }
            animation = new Animation(frameDuration, animArray);
            stateTime = 0f;
        }

    }

    public TextureRegion getFrame() {
        stateTime += Gdx.graphics.getDeltaTime();
        return  (TextureRegion) animation.getKeyFrame(stateTime, true);
    }
}

