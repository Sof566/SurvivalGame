package com.mygdx.game.Screen;

import com.badlogic.gdx.Gdx;
        import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
        import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Block.Block;
import com.mygdx.game.Block.DartThrower;
import com.mygdx.game.Block.Grass;
import com.mygdx.game.Block.Rock;
import com.mygdx.game.Block.Spike;
import com.mygdx.game.Entity.Entity;
import com.mygdx.game.Entity.Player;
import com.mygdx.game.GameObject;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Projectile.Dart;
import com.mygdx.game.Projectile.Projectile;
import com.mygdx.game.ResourseManager;
        import com.mygdx.game.TouchPad;
import com.mygdx.game.UiHearts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class ScreenPlay extends Screen{
    private GameScreenManager screenManager;
    private ResourseManager resourseManager;
    private Player player;
    private Rock rock;
    private Rock rock2;
    private Spike spike;
    private Grass grass;
    private DartThrower dartThrower;
    private Dart dart;
    private UiHearts uiHealthBar;
    private List<Entity> entities;
    private List<Block> blocks;
    private List<Projectile> projectiles = new ArrayList<>();
    private List<GameObject> renderList = new ArrayList<>();
    Vector2 PlayerWorldStartPosition = new Vector2(0, 0);
    private TouchPad joystick;
    public int WorldStateTime = 0;
    protected ScreenPlay(GameScreenManager screenManager, ResourseManager resourseManager) {
        super(screenManager, resourseManager);
        this.screenManager = screenManager;
        this.resourseManager = resourseManager;
        loadingCreaturesAndBlocks();
        camera.setToOrtho(false, MyGdxGame.SCR_WIDTH, MyGdxGame.SCR_HEIGHT);

        joystick = new TouchPad(camera, resourseManager);
        uiHealthBar = new UiHearts(resourseManager.getTexture(ResourseManager.uiHeartTexture),resourseManager.getTexture(ResourseManager.uiHungerTexture));
    }

    public void loadingCreaturesAndBlocks(){
        entities = new ArrayList<>();
        blocks = new ArrayList<>();
        player = new Player(resourseManager, PlayerWorldStartPosition);
        Vector2 pon = new Vector2(-100, 100);
        Vector2 pon2 = new Vector2(-300, 100);
        Vector2 pon3 = new Vector2(-500, 100);
        Vector2 pon4 = new Vector2(-700, 100);
        rock = new Rock(resourseManager, pon);
        rock2 = new Rock(resourseManager, pon2);
        spike = new Spike(resourseManager, pon3);
        dartThrower = new DartThrower(resourseManager, pon4, 45);
        entities.add(player);
        blocks.add(rock);
        blocks.add(rock2);
        blocks.add(spike);
        blocks.add(dartThrower);
        renderList.add(rock);
        renderList.add(rock2);
        renderList.add(spike);
        renderList.add(dartThrower);
        renderList.add(player);
    }

    @Override
    protected void update(float dt) {
        WorldStateTime += 1;
        mousePosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        inputTap();

        player.setVelocity(joystick.getKnobPercentWalk());
        player.update(dt, camera);
        rock.update(dt);
        rock2.update(dt);
        spike.update(dt);
        dartThrower.update(dt);

        if (WorldStateTime % 200 == 0) {
            dart = dartThrower.Shot();
            projectiles.add(dart);
            renderList.add(dart);
        }
        player.setVelocity(joystick.getKnobPercentWalk());

        uiHealthBar.update(dt, player.getHealth(), player.maxHp, player.getHunger());
        camera.update();

        for (Entity entity : entities) {
            entity.Collision(entities, blocks);
            if (entity.stun > 0){
                entity.stun -= 1;
            }
            if (entity.hp <= 0) {
                entity.lifeState = Entity.LifeState.DEAD;
            }
            if (entity.hp > entity.maxHp) {
                entity.hp = entity.maxHp;
            }
            entity.stateTime = entity.stateTime + 1;
        }

        for (Projectile projectile : projectiles) {
            projectile.Collision(entities, blocks, projectiles);
        }

        if (player.lifeState == Entity.LifeState.DEAD){
            player.dispose();
            screenManager.setScreen(new ScreenMenu(screenManager, resourseManager));
        }
    }

    @Override
    protected void render(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        Collections.sort(renderList);

        for (GameObject obj : renderList) {
            obj.render(batch);
        }

        uiHealthBar.render(batch, new Vector2(camera.position.x, camera.position.y));
        batch.end();

        joystick.render(batch);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glClearColor(0,0,0,1);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
        resourseManager.reload();
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    protected void inputTap() {

    }
}
