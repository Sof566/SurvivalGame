package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class GameObject implements Comparable<GameObject> {

    public Vector2 position = new Vector2(0,0);

    @Override
    public int compareTo(GameObject other) {
        return Float.compare(other.position.y, this.position.y);
    }

    public abstract void render(SpriteBatch batch);
}

