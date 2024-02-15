package com.mygdx.game.GameInterface;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Block.Block;
import com.mygdx.game.Block.Block.InteractionType;
import com.mygdx.game.ResourseManager;

public class UniversalButton {
    private ResourseManager resourceManager;
    private Texture texture;
    public enum state {
        EMPTY, BREAK
    }
    public state buttonState;
    Block currentBlock;
    private Rectangle rectangle;

    public UniversalButton(ResourseManager resourceManager, Camera camera) {
        this.resourceManager = resourceManager;
        this.texture = resourceManager.getTexture(ResourseManager.universalButton);
        rectangle = new Rectangle(camera.position.x+400, camera.position.y-100, 100, 100);
        this.buttonState = state.BREAK;
    }
    public void update(Camera camera) {
        rectangle.setPosition(camera.position.x+400, camera.position.y-100);
    }
    public void render(SpriteBatch batch) {
        batch.begin();
        batch.draw(resourceManager.getTexture(ResourseManager.universalButton), rectangle.x, rectangle.y);
        batch.end();
    }
    public void Interaction(InteractionType interactionType, Block block){
        switch (interactionType){
            case SIMPLE:
                switch (buttonState){
                    case EMPTY:
                        break;
                    case BREAK:
                        block.Strength -= 50;
                }
                break;
        }
    }
    public void setButtonState(state newState) {
        buttonState = newState;
    }
}


