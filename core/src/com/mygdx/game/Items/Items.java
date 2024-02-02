package com.mygdx.game.Items;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class Items {
    protected Items() {
    }
    public String id;
    public String name;
    public String description;

    public Texture texture = null;
    public Vector2 size = new Vector2(0,0);
    public int SpaseInventoy;

    public abstract void render(SpriteBatch batch);
    public abstract void update(float dt, OrthographicCamera camera);
    public abstract void dispose();
}
