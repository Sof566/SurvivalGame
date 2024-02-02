package com.mygdx.game.Projectile;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Animator;
import com.mygdx.game.Block.Block;
import com.mygdx.game.Entity.Entity;
import com.mygdx.game.ResourseManager;

public class Dart extends Projectile{
    ResourseManager resourseManager;
    private static final Vector2 bulletSize = new Vector2(70, 20);
    private static final int BASICSPEED = 10;
    private float rotation;

    public Dart(ResourseManager resourseManager, Vector2 startPosition, float rotationDegrees) {
        this.resourseManager = resourseManager;
        initPosition(startPosition);
        size = bulletSize;
        rotation = rotationDegrees;  // Переместите эту строку вверх
        velocity = new Vector2(MathUtils.cosDeg(rotation), MathUtils.sinDeg(rotation)).scl(BASICSPEED);
        bulletType = BulletType.soft;
        rectangle = new Rectangle(position.x , position.y, 20, 20);
        texture = resourseManager.getTexture(ResourseManager.Dart);
        stateTime = 0;
        damage = 3;
        lifetime = 200;
    }

    private void initPosition(Vector2 vector2) {
        position = vector2;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y, size.x / 2, size.y / 2, size.x, size.y, 1, 1, rotation, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }

    @Override
    public void update(float dt, OrthographicCamera camera) {
        if (lifeState == LifeState.LIFE) {
            this.stateTime = this.stateTime + 1;
        }
    }

    @Override
    public void updatePosition() {
        position.x += velocity.x;
        position.y += velocity.y;
        rectangle.setPosition(this.position);
    }

    @Override
    public void dispose() {

    }

    @Override
    public Rectangle getRectangle() {
        return rectangle;
    }
}
