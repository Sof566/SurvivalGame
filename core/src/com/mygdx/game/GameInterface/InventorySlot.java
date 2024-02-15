package com.mygdx.game.GameInterface;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.ResourseManager;

public class InventorySlot {
    private ResourseManager resourseManager;
    public static final Vector2 size = new Vector2(70, 70);
    private Texture texture;
    private Vector2 position;
    private int ID = 0;
    private int cellNumber;
    public enum State {
        UP, DOWN, DOWNING, UPPING
    }
    public State state = State.DOWN;
    int Y = 0;
    // 0 - отсутствие чанка
    // 1 - джунгли
    // 2, 3, 4- море
    // 5 - пляж
    // 6 16- кристальный лес
    // 7 17- кристальный лес

    // 8 9 10 11 - джунгли, пляж
    // 12 13 14 15 - Море, пляж
    public InventorySlot(ResourseManager resourseManager, Vector2 position, int ID, int cellNumber) {
        this.resourseManager = resourseManager;
        this.position = position;
        setItemType(ID);
        this.cellNumber = cellNumber;
        this.state = State.DOWN;
    }
    public void render(SpriteBatch batch, Vector2 cameraPos) {
        batch.draw(texture, position.x + cameraPos.x , position.y + cameraPos.y - 500, size.x, size.y);
    }
    public void setItemType(int i) {
        this.ID = i;
        switch (i) {
            case 0:
                texture = new Texture("images/slot.png");
                break;
            case 1:
                texture = new Texture("images/items/slot1.png");
                break;
            case 2:
                texture = new Texture("images/items/slot2.png");
                break;
        }
    }
    public int getTipe(){
        return ID;
    }

    public int getSlot(){
        return cellNumber;
    }
    public void setVector(int i){
        if (i == -1){
            state = State.UPPING;
        }else {
            state = State.DOWNING;
        }
    }

    public void update(float dt) {
        if (state == State.UPPING){
            this.position.y += 7;
            Y += 7;
        }
        if (state == State.DOWNING){
            this.position.y -= 7;
            Y += 7;
        }
        if (Y >= 301){
            Y = 0;
            if (state == State.UPPING){
                state = State.UP;
            }
            if (state == State.DOWNING){
                state = State.DOWN;
            }
        }
    }
}
