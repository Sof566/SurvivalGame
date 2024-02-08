package com.mygdx.game.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Animator;
import com.mygdx.game.ResourseManager;

public class Player extends Entity{
    ResourseManager resourseManager;
    public static final Vector2 playerSize = new Vector2(100, 100);
    private static final int BASICSPEED = 5;
    private MoveState playerMoveState;
    Animator animation;
    private int hunger = 100;
    private int invulnerability = 0;
    private Rectangle checkDist;

    public Player(ResourseManager resourseManager, Vector2 startPosition) {
        this.resourseManager = resourseManager;
        initPosition(startPosition);
        velocity = new Vector2();
        playerMoveState = MoveState.NONE;

        hp = maxHp = 10;
        hunger = 100;

        size = playerSize;
        checkDist = new Rectangle(0, 0, 200, 200);
        rectangle = new Rectangle(position.x, position.y, size.x, size.y);
        animation = new Animator(resourseManager.getTexture(ResourseManager.xuita), 2, 2, 0.5f, 0, 0);
        this.stateTime = 0;
        invulnerability = 60;
    }


    void initPosition(Vector2 vector2) {
        position = vector2;
    }


    @Override
    public void update(float dt, OrthographicCamera camera) {
        if (lifeState == LifeState.LIFE) {
            float cameraSpeed = velocity.len();
            camera.position.set(position.x + (size.x / 2), position.y + (size.y / 2), 0);
            camera.translate(velocity.x * dt * cameraSpeed, velocity.y * dt * cameraSpeed);
            rectangle.setPosition(position);
            position.x = rectangle.getX();
            position.y = rectangle.getY();
            checkDist.setPosition(camera.position.x-100, camera.position.y-100);
            starvation(stateTime);
            if (invulnerability > 0){
                invulnerability -= 1;
            }
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        if(lifeState == LifeState.LIFE){
            batch.draw(animation.getFrame(), position.x, position.y, size.x, size.y);
        }
    }

    @Override
    public void updatePosition() {
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            velocity.x = BASICSPEED;
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            velocity.x = -BASICSPEED;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            velocity.y = BASICSPEED;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            velocity.y = -BASICSPEED;
        }

        position.x += velocity.x;
        position.y += velocity.y;
        rectangle.setPosition(this.position);
    }

    public void MoveState(Vector2 velocity) {

        this.velocity.x = velocity.x * BASICSPEED;
        this.velocity.y = velocity.y * BASICSPEED;

        if (velocity.y > 0.8) {
            playerMoveState = MoveState.UP;
        } else if (velocity.y < -0.8) {
            playerMoveState = MoveState.DOWN;
        } else if (velocity.x > 0.5) {
            playerMoveState = MoveState.RIGHT;
        } else if (velocity.x < -0.5) {
            playerMoveState = MoveState.LEFT;
        } else {
            playerMoveState = MoveState.NONE;
            this.velocity.x = 0;
            this.velocity.y = 0;
        }

    }



    @Override
    public void dispose() {

    }

    public void setVelocity(Vector2 velocity) {
        this.velocity.x = velocity.x * BASICSPEED;
        this.velocity.y = velocity.y * BASICSPEED;
    }

    @Override
    public Rectangle getRectangle() {
        return rectangle;
    }

    public int getHealth() {
        return hp;
    }
    @Override
    public void getDamage(int damage){
        if (invulnerability == 0){
            this.hp -= damage;
            invulnerability = 60;
        }
    }
    public void starvation(int statetime){
        if (statetime % 300 == 0){
            hunger -= 1;
            if (hunger <= 0){
                this.hp -= 1;
                hunger = 0;
            }
        }
    }

    public int getHunger() {
        return hunger;
    }
    public Rectangle getCheckDist() {return checkDist;}
}
