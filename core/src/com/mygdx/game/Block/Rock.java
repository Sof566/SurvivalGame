package com.mygdx.game.Block;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Entity.Entity;
import com.mygdx.game.ResourseManager;

public class Rock extends Block{
    private ResourseManager resourseManager;
    public static final Vector2 Size = new Vector2(120, 120);
    private Texture texture;

    public Rock(ResourseManager resourseManager, Vector2 startPosition) {
        this.resourseManager = resourseManager;
        initPosition(startPosition);
        Strength = MaxStrength = 500;
        size = Size;
        blockType = BlockType.solid;
        rectangle = new Rectangle(position.x + 20, position.y, Size.x - 40, Size.y - 20);
        texture = resourseManager.getTexture(ResourseManager.rockBlock);
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

        }
    }

    @Override
    public void dispose() {

    }

    @Override
    public Rectangle getRectangle() {
        return rectangle;
    }
}
