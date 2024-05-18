package com.mygdx.game.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Block.Block;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.ResourseManager;

public class Chunk {
    private ResourseManager resourseManager;
    public static final Vector2 size = new Vector2(MyGdxGame.CHUNK, MyGdxGame.CHUNK);
    private Texture texture;
    private Vector2 position;
    private int tipe = 0;
    private int X;
    private int Y;
    float rotation;

    public Chunk(ResourseManager resourseManager, Vector2 position, int tipe, int X, int Y) {
        this.resourseManager = resourseManager;// 0 - отсутствие чанка
        // 1 - джунгли
        // 2, 3, 4- море
        // 5 - пляж
        // 6 16- кристальный лес
        // 7 17- кристальный лес

        // 8 9 10 11 - джунгли, пляж
        // 12 13 14 15 - Море, пляж;
        this.position = position;
        setBiomeType(tipe);
        rotation = 0;
        this.X = X;
        this.Y = Y;
    }
    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y, size.x / 2, size.y / 2, size.x, size.y, 1, 1, rotation, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }
    public void setBiomeType(int i) {
        this.tipe = i;
        switch (i) {
            case 1:
                texture = new Texture("images/pol00.png");
                break;
            case 2:
            case 3:
            case 4:
                texture = new Texture("images/pol10.png");
                break;
            case 5:
                texture = new Texture("images/pol20.png");
                break;
            case 6:
                texture = new Texture("images/pol40.png");
                break;
            case 7:
                texture = new Texture("images/pol30.png");
                break;
            case 8:
                texture = new Texture("images/pol01.png");
                break;
            case 9:
                texture = new Texture("images/pol02.png");
                break;
            case 10:
                texture = new Texture("images/pol03.png");
                break;
            case 11:
                texture = new Texture("images/pol04.png");
                break;
            case 12:
                texture = new Texture("images/pol11.png");
                break;
            case 13:
                texture = new Texture("images/pol12.png");
                break;
            case 14:
                texture = new Texture("images/pol13.png");
                break;
            case 15:
                texture = new Texture("images/pol14.png");
                break;
            default:
                break;
        }
    }
    public void setRotation(float x){
        this.rotation = x;
    }
    public int getTipe(){
        return tipe;
    }
    public int getX(){
        return X;
    }
    public int getY(){
        return Y;
    }

    public void update(float dt) {
    }
}
