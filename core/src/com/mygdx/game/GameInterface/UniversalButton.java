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

public class UniversalButton {
    private ResourseManager resourceManager;
    private Button button;
    private Texture texture;
    private state buttonState;
    Block currentBlock;

    public UniversalButton(ResourseManager resourceManager, Camera camera) {
        this.resourceManager = resourceManager;
        this.texture = resourceManager.getTexture(ResourseManager.universalButton);
        this.button = new Button(texture, camera.position.x + 400, camera.position.y - 100, 100, 100);
        this.buttonState = state.EMPTY;
    }

    public void update(Camera camera) {
        button.updatePosition(camera.position.x + 400, camera.position.y - 100);
        button.update(camera);
    }

    public void render(SpriteBatch batch) {
        batch.begin();
        button.render(batch);
        batch.end();
    }

    public void isCollision(final Block block) {
        this.currentBlock = block;
        button.setClickListener(new Button.onClickListener() {
            @Override
            public void click() {
                if (buttonState == state.EMPTY) {
                    if(currentBlock.interactionType == Block.InteractionType.SIMPLE)
                        currentBlock.collection(currentBlock);
                }
            }
        });
    }

    // Установка типа кнопки
    public void setButtonState(state newState) {
        buttonState = newState;
    }


    public enum state {
        EMPTY
    }
}


