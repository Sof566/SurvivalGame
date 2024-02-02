package com.mygdx.game.Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;

public abstract class Materials extends Items{
    protected Materials() {
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/main_font.ttf"));
        this.font = generator.generateFont(parameter);
    }

    public static final Vector2 materialSize = new Vector2(100, 100);
    Texture materialTexture;
    public String id;
    public String name;
    public String description;
    protected BitmapFont font;


    public abstract void render(SpriteBatch batch);
    public abstract void update(float dt, OrthographicCamera camera);
    public abstract void dispose();
}
