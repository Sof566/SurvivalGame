package com.mygdx.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Block.Block;
import com.mygdx.game.Block.DartThrower;
import com.mygdx.game.Block.Grass;
import com.mygdx.game.Block.Rock;
import com.mygdx.game.Block.Spike;
import com.mygdx.game.Button;
import com.mygdx.game.Chunk;
import com.mygdx.game.Entity.Entity;
import com.mygdx.game.Entity.Player;
import com.mygdx.game.GameInterface.UiInventory;
import com.mygdx.game.GameInterface.UniversalButton;
import com.mygdx.game.GameObject;
import com.mygdx.game.MapGenerator;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Projectile.Dart;
import com.mygdx.game.Projectile.Projectile;
import com.mygdx.game.ResourseManager;
        import com.mygdx.game.GameInterface.TouchPad;
import com.mygdx.game.GameInterface.UiHearts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScreenPlay extends Screen{
    private GameScreenManager screenManager;
    Texture texture;

    private ResourseManager resourseManager;
    private MapGenerator mapGenerator;
    private Player player;
    private Rock rock;
    private Spike spike;
    private Grass grass;
    private DartThrower dartThrower;
    private Dart dart;
    private UiHearts uiHealthBar;
    private List<Entity> entities;
    private static List<Block> blocks;
    private List<Projectile> projectiles = new ArrayList<>();
    private List<GameObject> renderList = new ArrayList<>();
    private List<Chunk> chunkList = new ArrayList<>();
    Vector2 PlayerWorldStartPosition = new Vector2(0, 0);
    private TouchPad joystick;
    private UiInventory inventory;
    public int WorldStateTime = 0;
    public long SEED = 9125722410L;
    int X_CHUNK, Y_CHUNK;
    private UniversalButton universalButton;
    protected ScreenPlay(GameScreenManager screenManager, ResourseManager resourseManager) {
        super(screenManager, resourseManager);
        this.screenManager = screenManager;
        this.resourseManager = resourseManager;
        //mapGenerator = new MapGenerator(resourseManager, 121, SEED);
        //chunkList = mapGenerator.generateMap();
        loadingCreaturesAndBlocks();
        camera.setToOrtho(false, MyGdxGame.SCR_WIDTH, MyGdxGame.SCR_HEIGHT);
        joystick = new TouchPad(camera, resourseManager);
        inventory = new UiInventory(resourseManager, new Vector2(camera.position.x, camera.position.y));
        universalButton = new UniversalButton(resourseManager, camera);
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
        Vector2 pon5 = new Vector2(-900, 100);
        rock = new Rock(resourseManager, pon2);
        spike = new Spike(resourseManager, pon3);
        grass = new Grass(resourseManager, pon5);
        dartThrower = new DartThrower(resourseManager, pon4, 45);
        entities.add(player);
        blocks.add(rock);
        blocks.add(spike);
        blocks.add(dartThrower);
        blocks.add(grass);
        renderList.add(rock);
        renderList.add(spike);
        renderList.add(dartThrower);
        renderList.add(player);
        renderList.add(grass);
    }

    @Override
    protected void update(float dt) {
        WorldStateTime += 1;
        mousePosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        inputTap();

        player.setVelocity(joystick.getKnobPercentWalk());
        player.update(dt, camera);
        rock.update(dt);
        spike.update(dt);
        dartThrower.update(dt);

        universalButton.update(camera);

        if (WorldStateTime % 200 == 0) {
            dart = dartThrower.Shot();
            projectiles.add(dart);
            renderList.add(dart);
        }

        player.setVelocity(joystick.getKnobPercentWalk());

        X_CHUNK = (int) Math.ceil((double) player.position.x / MyGdxGame.CHUNK);
        Y_CHUNK = (int) Math.ceil((double) player.position.y / MyGdxGame.CHUNK);
        for (Chunk chunk : chunkList) {
            if (chunk.getX() == X_CHUNK || chunk.getX() + 1 == X_CHUNK || chunk.getX() - 1 == X_CHUNK){
                if (chunk.getY() == Y_CHUNK || chunk.getY() + 1 == Y_CHUNK || chunk.getY() - 1 == Y_CHUNK){
                    chunk.update(dt);
                }
            }
        }

        uiHealthBar.update(dt, player.getHealth(), player.maxHp, player.getHunger());
        inventory.update(dt, new Vector2(camera.position.x, camera.position.y), camera);
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

        for (Block block : blocks) {
            if (Intersector.overlaps(player.getCheckDist(), block.getRectangle())) {
                universalButton.isCollision(block);
            }
        }

        for (Projectile projectile : projectiles) {
            projectile.Collision(entities, blocks, projectiles);
            if (projectile.stateTime >= projectile.lifetime){
                projectile.dispose();
            }
        }

        if (player.lifeState == Entity.LifeState.DEAD){
            player.dispose();
            screenManager.setScreen(new ScreenMenu(screenManager, resourseManager));
        }

        for (Chunk chunk : chunkList) {
            if (ChunkManager(chunk)){
                chunk.update(dt);
            }
        }
    }

    @Override
    protected void render(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        for (Chunk chunk : chunkList) {
            if (ChunkManager(chunk)){
                chunk.render(batch);
            }
        }

        Collections.sort(renderList);
        for (GameObject obj : renderList) {
            obj.render(batch);
        }


        uiHealthBar.render(batch, new Vector2(camera.position.x, camera.position.y));

        batch.end();

        joystick.render(batch);
        inventory.render(batch);
        universalButton.render(batch);
    }

    public boolean ChunkManager(Chunk chunk) {
        X_CHUNK = (int) Math.ceil((double) player.position.x / MyGdxGame.CHUNK);
        Y_CHUNK = (int) Math.ceil((double) player.position.y / MyGdxGame.CHUNK);
        if (chunk.getX() >= X_CHUNK - 2 && chunk.getX() <= X_CHUNK + 2) {
            if (chunk.getY() >= Y_CHUNK - 2 && chunk.getY() <= Y_CHUNK + 2) {
                return true;
            }
        }
        if (MyGdxGame.TEST_CHUNK == true){
            return true;
        }
        return false;
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
        /*universalButton.setClickListener(new Button.onClickListener() {
            @Override
            public void click() {
                System.out.println("dacnkadne");
            }
        });*/
    }

    public static void disposeBlock(Block block) {

    }
}
