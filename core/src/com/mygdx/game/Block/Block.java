package com.mygdx.game.Block;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameObject;

public abstract class Block extends GameObject {
    protected Block() {
    }
    public enum LifeStateBlock {
        Installation,
        LIFE,
        DEAD,
        Destruction
    }

    public enum BlockType {
        solid,
        soft,
        pricking
    }

    public enum InteractionType {
        SIMPLE
    }

    public LifeStateBlock lifeStateblock = LifeStateBlock.LIFE;
    public BlockType blockType;
    public InteractionType interactionType;
    public Vector2 size = new Vector2(0,0);
    public Texture texture = null;
    public int Strength, MaxStrength, stateTime, blockdamage;
    public Rectangle rectangle = null;

    public abstract void update(float dt);
    public abstract void dispose();
    public abstract Rectangle getRectangle();
    public abstract void collection(Block block);
}
