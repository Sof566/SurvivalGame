package com.mygdx.game.GameInterface;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGame;

public class UiHearts {

    private Texture texture, texture2;
    private int healthCount;
    private int MaxhealthCount;
    private int hunger;

    public UiHearts(Texture texture, Texture texture2) {
        this.texture = texture;
        this.texture2 = texture2;
    }

    public void update(float dt, int playerHealth, int playerMaxHealth, int playerhunger) {
        healthCount = playerHealth;
        MaxhealthCount = playerMaxHealth;
        hunger = (int) Math.ceil(playerhunger / 10.0);
    }

    public void render(SpriteBatch batch, Vector2 cameraPos) {
        int shift = 45;
        for (int i = 0; i < healthCount; i++) {
            batch.draw(texture, cameraPos.x + shift * i - 600, cameraPos.y - MyGdxGame.SCR_HEIGHT / 2 + 650, 40, 40);
        }
        for (int i = 0; i < hunger; i++) {
            batch.draw(texture2, cameraPos.x + shift * i - 600, cameraPos.y - MyGdxGame.SCR_HEIGHT / 2 + 600, 40, 40);
        }
    }

    public void dispose() {
        texture.dispose();
        texture2.dispose();
    }
}