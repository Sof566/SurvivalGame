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

public class ResourseManager {
    public static final String bcgLoading = "images/a1.png";
    public static final String bcgMenu = "images/2.png";
    public static final String bcgSetting = "images/test.png";
    public static final String txtSettings = "images/button_setting.png";
    public static final String txtExit = "images/button_back.png";
    public static final String txtVolumePlus = "images/txtPlus.png";
    public static final String txtVolumeMinus = "images/txtMinus.png";
    public static final String txtPlayer = "images/player.png";
    public static final String bttPlay = "images/img_play.png";
    public static final String animKapitochca = "images/hb.png";
    public static final String touchBackground = "images/touchBackground.png";
    public static final String touchKnob = "images/touchKnob.png";

    public static final String xuita = "images/Xuita.png";
    public static String rockBlock = "images/Blocks/rock.png";
    public static String spikeBlock = "images/Blocks/spike.png";
    public static String uiHeartTexture = "images/HP.png";
    public static String uiHungerTexture = "images/Hunger.png";
    public static String grassBlock = "images/Blocks/grass.png";
    public static String DartBlock = "images/Blocks/Dart.png";
    public static String Dart = "images/Projectiles/Dart.png";

    public Texture texture;
    public static final String music = "sound/music/audio_gameKapy.mp3";
    AssetManager assetManager;
    private BitmapFont font;

    public void loading() {
        assetManager = new AssetManager();
        loadingImg();
        loadingMusic();
        //loadingFonts();
    }

    public void loadingImg() {
        assetManager.setLoader(Texture.class, new TextureLoader(new InternalFileHandleResolver()));
        assetManager.load(bcgLoading, Texture.class);
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
    }

    public void loadingMusic() {
        assetManager.setLoader(Music.class, new MusicLoader(new InternalFileHandleResolver()));
        assetManager.load(music, Music.class);
    }

    /*private void loadingFonts() {
        this.font = new BitmapFont();
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/main_font.ttf"));
        parameter.size = 25;
        parameter.color = new Color(1.0F, 1.0F, 1.0F, 1.0F);
        this.font = generator.generateFont(parameter);
    }*/

    public boolean isLoad() {
        return assetManager.update();
    }

    public void reload() {
        this.loading();
    }

    public Texture getTexture(String name) {
        return assetManager.get(name);
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
