package com.mygdx.game.GameInterface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Block.Block;
import com.mygdx.game.Block.Rock;
import com.mygdx.game.Button;
import com.mygdx.game.ResourseManager;
import com.mygdx.game.Screen.ScreenPlay;

public class UniversalButton {
    private ResourseManager resourceManager;
    private Texture texture;
    public state buttonState;
    Block currentBlock;
    private Rectangle rectangle;

    public UniversalButton(ResourseManager resourceManager, Camera camera) {
        this.resourceManager = resourceManager;
        this.texture = resourceManager.getTexture(ResourseManager.universalButton);
        rectangle = new Rectangle(camera.position.x+400, camera.position.y-100, 100, 100);
        this.buttonState = state.EMPTY;
    }

    public void update(Camera camera) {
        rectangle.setPosition(camera.position.x+400, camera.position.y-100);
    }

    public void render(SpriteBatch batch) {
        batch.begin();
        batch.draw(resourceManager.getTexture(ResourseManager.universalButton), rectangle.x, rectangle.y);
        batch.end();
    }

    /*public void isCollision(final Block block) {
        if (buttonState == state.EMPTY) {
            if(currentBlock.interactionType == Block.InteractionType.SIMPLE) {
                block.Strength -= 50;
                if (block.Strength <= 0) {
                    ScreenPlay.disposeBlock(block);
                }
            }
        }
    }*/

    // Установка типа кнопки
    public void setButtonState(state newState) {
        buttonState = newState;
    }


    public enum state {
        EMPTY
    }
}


