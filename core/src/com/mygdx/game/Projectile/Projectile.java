package com.mygdx.game.Projectile;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Block.Block;
import com.mygdx.game.Entity.Entity;
import com.mygdx.game.GameObject;

import java.util.List;

public abstract class Projectile extends GameObject {
    public enum LifeState {
        LIFE, DEAD, DYING
    }
    public LifeState lifeState = LifeState.LIFE;

    public Vector2 size = new Vector2(0,0);
    public Texture texture = null;
    public Vector2 velocity = new Vector2(0, 0);
    public int damage, stateTime, lifetime;

    public enum BulletType {
        solid,
        soft,
    }

    public Rectangle rectangle = null;
    public BulletType bulletType;
    public abstract void update(float dt, OrthographicCamera camera);
    public abstract void updatePosition();
    public abstract void dispose();
    public abstract Rectangle getRectangle();

    public void Collision(List<Entity> entities, List<Block> blocks, List<Projectile> projectiles) {
        updatePosition();
        for (Projectile projectile : projectiles) {
            if (stateTime >= lifetime){
                projectile.dispose();
            }
            for (Entity entity : entities) {
                if (Intersector.overlaps(entity.getRectangle(), projectile.getRectangle())) {
                    entity.getDamage(projectile.damage);
                    if (projectile.bulletType == BulletType.solid) {
                        projectile.dispose();
                    }
                }
            }
            for (Block block : blocks) {
                if (Intersector.overlaps(projectile.getRectangle(), block.getRectangle())) {
                    if (projectile.bulletType == BulletType.solid){
                        if (projectile.bulletType == BulletType.solid){
                            projectile.dispose();
                        }
                    }
                }
            }
        }
    }
}
