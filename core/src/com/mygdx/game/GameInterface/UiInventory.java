package com.mygdx.game.GameInterface;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Button;
import com.mygdx.game.ResourseManager;

public class UiInventory {
    public Button bttOpening, bttBack;
    ResourseManager resourseManager;
    private boolean isOpen;
    Vector2 cameraPos;
    private Rectangle inventoryPos;

    public UiInventory(ResourseManager resourseManager, Vector2 cameraPos) {
        this.resourseManager = resourseManager;
        bttOpening = new Button(resourseManager.getTexture(ResourseManager.backpack), cameraPos.x+400, cameraPos.y-350, 100, 100);
        bttBack = new Button(resourseManager.getTexture(ResourseManager.bttBackLock), cameraPos.x+340, cameraPos.y-350, 50, 50);
        inventoryPos = new Rectangle(cameraPos.x, cameraPos.y, 200, 200);
        isOpen = false;
        this.cameraPos = cameraPos;
    }
    public void update(float dt, Vector2 cameraPos, Camera camera) {
        bttOpening.updatePosition(cameraPos.x+400, cameraPos.y-350);
        bttOpening.update(camera);
        bttBack.update(camera);
        bttBack.updatePosition(cameraPos.x+360, cameraPos.y-350);
        inventoryPos.setPosition(cameraPos.x+400, cameraPos.y-350);

        if(!isOpen) {
            bttOpening.setClickListener(new Button.onClickListener() {
                @Override
                public void click() {
                    isOpen = true;
                }
            });
        }
        if(isOpen) {
            bttBack.setClickListener(new Button.onClickListener() {
                @Override
                public void click() {
                    isOpen = false;
                }
            });
        }

    }
    public void render(SpriteBatch batch) {
        batch.begin();
        if(!isOpen) bttOpening.render(batch);
        if(isOpen) {
            batch.draw(resourseManager.getTexture(ResourseManager.inventory), inventoryPos.x, inventoryPos.y, inventoryPos.width, inventoryPos.height);
            bttBack.render(batch);
        }
        batch.end();
    }

    public void dispose() {
    }
}
