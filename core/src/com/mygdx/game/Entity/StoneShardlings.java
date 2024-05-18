package com.mygdx.game.Entity;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.ResourseManager;

public class StoneShardlings extends Entity{ //каменные осколочки, не спрашивай))))

    private ResourseManager resourseManager;
    public static final Vector2 size = new Vector2(40,  40);
    private Rectangle chekDist;

    public StoneShardlings(ResourseManager resourseManager, Vector2 startPosition) {
        this.resourseManager = resourseManager;

        hp = maxHp = 1;
        initPosition(startPosition);
        this.stateTime = 0;
        rectangle = new Rectangle(position.x, position.y, size.x, size.y);
        chekDist = new Rectangle(position.x, position.y, 20, 20);
    }

    void initPosition(Vector2 vector2) {
        position = vector2;
    }

    @Override
    public void update(float dt, OrthographicCamera camera) {
        rectangle.setPosition(camera.position.x+200, camera.position.y-100);
        position.x = rectangle.getX();
        position.y = rectangle.getY();
        chekDist.setPosition(position.x, position.y);
    }

    @Override
    public void updatePosition() {

    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(resourseManager.getTexture(ResourseManager.stoneShardling), position.x, position.y, size.x, size.y);
    }

    @Override
    public void dispose() {

    }

    @Override
    public Rectangle getRectangle() {
        return null;
    }


}
