package com.mygdx.game.Block;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.ResourseManager;
import com.mygdx.game.Screen.ScreenPlay;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Grass extends Block{
    private ResourseManager resourseManager;
    public static final Vector2 Size = new Vector2(100, 100);
    private Texture texture;

    public Grass(ResourseManager resourseManager, Vector2 startPosition) {
        this.resourseManager = resourseManager;
        initPosition(startPosition);
        Strength = MaxStrength = 50;
        size = Size;
        blockType = BlockType.soft;
        interactionType = InteractionType.SIMPLE;
        rectangle = new Rectangle(position.x, position.y, Size.x, Size.y);
        texture = resourseManager.getTexture(ResourseManager.grassBlock);
        stateTime = 0;
    }

    private void initPosition(Vector2 vector2) {
        position = vector2;
    }

    @Override
    public void render(SpriteBatch batch) {
        if(lifeStateblock == LifeStateBlock.LIFE){
            batch.draw(texture, position.x, position.y, size.x, size.y);
        }
    }

    @Override
    public void update(float dt) {
        if (lifeStateblock == LifeStateBlock.LIFE) {
            stateTime += 1;
        }
    }



    @Override
    public void dispose() {
        this.texture.dispose();
    }

    @Override
    public Rectangle getRectangle() {
        return rectangle;
    }

    @Override
    public void collection(Block block) {
        dispose();
    }


}

