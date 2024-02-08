package com.mygdx.game.Block;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Projectile.Dart;
import com.mygdx.game.Projectile.Projectile;
import com.mygdx.game.ResourseManager;
import com.mygdx.game.Screen.ScreenPlay;

import java.util.ArrayList;
import java.util.List;

public class DartThrower extends Block{
    private ResourseManager resourseManager;
    private static final Vector2 Size = new Vector2(120, 120);
    private Texture texture;
    private float rotation;
    private Dart dart;

    public DartThrower(ResourseManager resourseManager, Vector2 startPosition, float rotationDegrees) {
        this.resourseManager = resourseManager;
        initPosition(startPosition);
        Strength = MaxStrength = 1000;
        size = Size;
        blockType = BlockType.solid;
        rectangle = new Rectangle(position.x , position.y, Size.x, Size.y);
        texture = resourseManager.getTexture(ResourseManager.DartBlock);
        stateTime = 0;
        rotation = rotationDegrees;
    }

    private void initPosition(Vector2 vector2) {
        position = vector2;
    }

    @Override
    public void render(SpriteBatch batch) {
        if (lifeStateblock == LifeStateBlock.LIFE) {
            batch.draw(texture, position.x, position.y, size.x / 2, size.y / 2, size.x, size.y, 1, 1, rotation, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
        }
    }

    @Override
    public void update(float dt) {
        if (lifeStateblock == LifeStateBlock.LIFE) {

        }
    }
    public Dart Shot(){
        dart = new Dart(resourseManager, new Vector2(position), rotation);
        return dart;
    }

    @Override
    public void dispose() {

    }

    @Override
    public Rectangle getRectangle() {
        return rectangle;
    }

    @Override
    public void collection(Block block) {

    }
}
