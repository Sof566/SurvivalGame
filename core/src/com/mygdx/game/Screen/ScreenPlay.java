package com.mygdx.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Block.Baobab;
import com.mygdx.game.Block.Block;
import com.mygdx.game.Block.Crystal;
import com.mygdx.game.Block.DartThrower;
import com.mygdx.game.Block.Grass;
import com.mygdx.game.Block.Mushroom;
import com.mygdx.game.Block.Rock;
import com.mygdx.game.Block.Spike;
import com.mygdx.game.Button;
import com.mygdx.game.Chunk;
import com.mygdx.game.Entity.Entity;
import com.mygdx.game.Entity.Player;
import com.mygdx.game.GameInterface.InventorySlot;
import com.mygdx.game.GameInterface.UiInventory;
import com.mygdx.game.GameInterface.UniversalButton;
import com.mygdx.game.GameObject;
import com.mygdx.game.Items.Items;
import com.mygdx.game.MapGenerator;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Projectile.Dart;
import com.mygdx.game.Projectile.Projectile;
import com.mygdx.game.ResourseManager;
        import com.mygdx.game.GameInterface.TouchPad;
import com.mygdx.game.GameInterface.UiHearts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ScreenPlay extends Screen{
    private GameScreenManager screenManager;
    Texture texture;

    private ResourseManager resourseManager;
    private MapGenerator mapGenerator;
    private UiInventory uiInventory;
    private Player player;
    private Rock rock;
    private Spike spike;
    private Grass grass;
    private Mushroom mushroom;
    private Crystal crystal;
    private Baobab baobab;
    private DartThrower dartThrower;
    private Dart dart;
    private UiHearts uiHealthBar;
    private List<Entity> entities;
    private List<Block> blocks;
    private List<Projectile> projectiles = new ArrayList<>();
    private List<GameObject> renderList = new ArrayList<>();
    private List<Chunk> chunkList = new ArrayList<>();
    private List<InventorySlot> itemsList = new ArrayList<>();
    Vector2 PlayerWorldStartPosition = new Vector2(0, 0);
    private TouchPad joystick;
    private UiInventory inventory;
    public int WorldStateTime = 0;
    public long SEED = 6942L;
    int X_CHUNK, Y_CHUNK;
    private UniversalButton universal;
    private Button universalButton;
    Button bttInventory;
    protected ScreenPlay(GameScreenManager screenManager, ResourseManager resourseManager) {
        super(screenManager, resourseManager);
        this.screenManager = screenManager;
        this.resourseManager = resourseManager;
        //mapGenerator = new MapGenerator(resourseManager, 121, SEED);
        //chunkList = mapGenerator.generateMap();
        uiInventory = new UiInventory(resourseManager);
        itemsList = uiInventory.GenerateInventory();
        loadingCreaturesAndBlocks();
        bttInventory = new Button(resourseManager.getTexture(ResourseManager.bttBackLock), 400, -250, 100, 100);
        camera.setToOrtho(false, MyGdxGame.SCR_WIDTH, MyGdxGame.SCR_HEIGHT);
        joystick = new TouchPad(camera, resourseManager);
        universal = new UniversalButton(resourseManager, camera);
        universalButton = new Button(resourseManager.getTexture(ResourseManager.universalButton), camera.position.x+400, camera.position.y-100, 100, 100);
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
        Vector2 pon6 = new Vector2(-100, -100);
        Vector2 pon7 = new Vector2(-200, -100);
        Vector2 pon8 = new Vector2(-800, -100);
        rock = new Rock(resourseManager, pon2);
        spike = new Spike(resourseManager, pon3);
        grass = new Grass(resourseManager, pon5);
        mushroom = new Mushroom(resourseManager, pon6);
        crystal = new Crystal(resourseManager, pon7);
        baobab = new Baobab(resourseManager, pon8);
        dartThrower = new DartThrower(resourseManager, pon4, 45);
        entities.add(player);
        blocks.add(rock);
        blocks.add(spike);
        blocks.add(dartThrower);
        blocks.add(grass);
        blocks.add(mushroom);
        blocks.add(crystal);
        blocks.add(baobab);
        renderList.add(rock);
        renderList.add(spike);
        renderList.add(dartThrower);
        renderList.add(player);
        renderList.add(grass);
        renderList.add(mushroom);
        renderList.add(crystal);
        renderList.add(baobab);
    }

    @Override
    protected void update(float dt) {
        WorldStateTime += 1;
        mousePosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        inputTap();

        player.setVelocity(joystick.getKnobPercentWalk());
        player.update(dt, camera);


        universal.update(camera);
        universalButton.update(camera);
        bttInventory.update(camera);
        universalButton.updatePosition(camera.position.x + 400, camera.position.y - 100);
        bttInventory.updatePosition(camera.position.x + 400, camera.position.y - 200);


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
        for (InventorySlot inventorySlot : itemsList) {
            inventorySlot.update(dt);
        }
        for (Iterator<Block> iterator = blocks.iterator(); iterator.hasNext();) {
            Block block = iterator.next();
            block.update(dt);
            if (block.Strength <= 0) {
                block.collection(uiInventory);
                iterator.remove();
                renderList.remove(block);
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
        for (InventorySlot inventorySlot : itemsList) {
            inventorySlot.render(batch, new Vector2(camera.position.x, camera.position.y));
        }
        universalButton.render(batch);
        bttInventory.render(batch);
        batch.end();
        joystick.render(batch);
        universal.render(batch);
    }

    public boolean ChunkManager(Chunk chunk) {
        X_CHUNK = (int) Math.ceil((double) player.position.x / MyGdxGame.CHUNK);
        Y_CHUNK = (int) Math.ceil((double) player.position.y / MyGdxGame.CHUNK);
        if (chunk.getX() >= X_CHUNK - 1 && chunk.getX() <= X_CHUNK + 1) {
            if (chunk.getY() >= Y_CHUNK - 1 && chunk.getY() <= Y_CHUNK + 1) {
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
        mousePosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePosition);
        bttInventory.setClickListener(new Button.onClickListener() {
            @Override
            public void click() {
                uiInventory.UpInventory();
            }
        });
        universalButton.setClickListener(new Button.onClickListener() {
            @Override
            public void click() {
                for (Block block : blocks) {
                    if (Intersector.overlaps(player.getRectangle(), block.getRectangle())) {
                        universal.Interaction(block.interactionType, block);
                    }
                }
            }
        });
    }
}
