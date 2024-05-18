package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.MusicLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.HashMap;
import java.util.Map;

import javax.swing.plaf.PanelUI;

public class ResourseManager {
    public static final String bcgSetting = "images/SettingsInterface/a1.png";
    public static final String bcgMenu = "images/MenuInterface/2.png";
    public static final String txtSettings = "images/MenuInterface/button_setting.png";
    public static final String txtExit = "images/MenuInterface/button_back.png";
    public static final String txtVolumePlus = "images/SettingsInterface/txtPlus.png";
    public static final String txtVolumeMinus = "images/SettingsInterface/txtMinus.png";
    public static final String txtPlayer = "images/player.png";
    public static final String bttPlay = "images/MenuInterface/img_play.png";
    public static final String animKapitochca = "images/hb.png";
    public static final String touchBackground = "images/PlayInterface/touchBackground.png";
    public static final String touchKnob = "images/PlayInterface/touchKnob.png";
    public static final String bttTranslTranslationRUS = "images/SettingsInterface/bttRus.png";
    public static final String bttTranslTranslationANGL = "images/SettingsInterface/bttAngl.png";

    public static final String xuita = "images/Player/Xuita.png";
    public static String rockBlock = "images/Blocks/rock.png";
    public static String spikeBlock = "images/Blocks/spike.png";
    public static String uiHeartTexture = "images/PlayInterface/HP.png";
    public static String uiHungerTexture = "images/PlayInterface/Hunger.png";
    public static String grassBlock = "images/Blocks/grass.png";
    public static String DartBlock = "images/Blocks/Dart.png";
    public static String Dart = "images/Projectiles/Dart.png";
    public static String backpack = "images/PlayInterface/backpack.png";
    public static String inventory = "images/PlayInterface/inventory.png";
    public static String bttBackLock = "images/PlayInterface/bttBackLock.png";
    public static String universalButton = "images/PlayInterface/universalButton.png";
    public static String mashroom = "images/Blocks/mashroom.png";
    public static String crystal = "images/Blocks/kristall.png";
    public static String baobab = "images/Blocks/baobab.png";
    public static String tailSnake = "images/Blocks/snake_tail.png";

    public static String stoneShardling = "images/Enemies/StoneShardlings.png";

    public Texture texture;


    public static final String music = "sound/music/audio_gameKapy.mp3";
    AssetManager assetManager;

    private BitmapFont font;

    Map<String, String[]> all = new HashMap<>();



    public void loading() {
        assetManager = new AssetManager();
        loadingImg();
        loadingMusic();
        loadFonts();
        allTranslator();
    }

    public void allTranslator() {
        all.put("play", new String[] {"Play", "Играть"});
        all.put("settings", new String[]{"Settings", "Настройки"});
    }
    public void loadingImg() {
        assetManager.setLoader(Texture.class, new TextureLoader(new InternalFileHandleResolver()));
        //assetManager.load(bcgLoading, Texture.class);
        assetManager.load(bcgMenu, Texture.class);
        assetManager.load(txtSettings, Texture.class);
        assetManager.load(txtExit, Texture.class);
        assetManager.load(bcgSetting, Texture.class);
        assetManager.load(txtVolumePlus, Texture.class);
        assetManager.load(txtVolumeMinus, Texture.class);
        assetManager.load(txtPlayer, Texture.class);
        assetManager.load(bttPlay, Texture.class);
        assetManager.load(animKapitochca, Texture.class);
        assetManager.load(touchBackground, Texture.class);
        assetManager.load(touchKnob, Texture.class);
        assetManager.load(xuita, Texture.class);
        assetManager.load(rockBlock, Texture.class);
        assetManager.load(spikeBlock, Texture.class);
        assetManager.load(uiHeartTexture, Texture.class);
        assetManager.load(uiHungerTexture, Texture.class);
        assetManager.load(grassBlock, Texture.class);
        assetManager.load(DartBlock, Texture.class);
        assetManager.load(Dart, Texture.class);
        assetManager.load(backpack, Texture.class);
        assetManager.load(inventory, Texture.class);
        assetManager.load(bttBackLock, Texture.class);
        assetManager.load(universalButton, Texture.class);
        assetManager.load(mashroom, Texture.class);
        assetManager.load(crystal, Texture.class);
        assetManager.load(baobab, Texture.class);
        assetManager.load(tailSnake, Texture.class);
        assetManager.load(bttTranslTranslationRUS, Texture.class);
        assetManager.load(bttTranslTranslationANGL, Texture.class);

        assetManager.load(stoneShardling, Texture.class);
    }

    public void loadingMusic() {
        assetManager.setLoader(Music.class, new MusicLoader(new InternalFileHandleResolver()));
        assetManager.load(music, Music.class);
    }

    private void loadFonts() {
        this.font = new BitmapFont();
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/main_font.ttf"));
        parameter.size = 25;
        parameter.color = new Color(1.0F, 1.0F, 1.0F, 1.0F);
        this.font = generator.generateFont(parameter);
    }


    public boolean isLoad() {
        return assetManager.update();
    }

    public void reload() {
        this.loading();
    }

    public Texture getTexture(String name) {
        return assetManager.get(name);
    }

    public Map<String, String[]> getTranslator() {
        return all;
    }

    public BitmapFont getFont() {
        return this.font;
    }
    public Music getMusic(String name) {
        return  assetManager.get(name);
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public void dispose() {
        assetManager.clear();
        assetManager.dispose();
    }


}
