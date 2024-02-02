package com.mygdx.game.Screen;


import com.mygdx.game.Screen.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Iterator;
import  java.util.Stack;


public class GameScreenManager {
    private Stack<Screen> typeScreen;

    public GameScreenManager() {
        typeScreen = new Stack<Screen>();
    }

    public void popScreen() {
        typeScreen.pop();
    }

    public void pushScreen(Screen screen) {
        if (typeScreen.size() > 0) {
            typeScreen.peek().dispose();
        }
        typeScreen.push(screen);
    }

    public void setScreen(Screen screen) {
        popScreen();
        pushScreen(screen);
    }

    public void pause() {
        typeScreen.peek().pause();
    }

    public void update(float dt) {
        typeScreen.peek().update(dt);
    }

    public void render(SpriteBatch batch) {
        Stack<Screen> tempStack = new Stack<Screen>();
        tempStack.addAll(typeScreen);
        Iterator<Screen> iterator = tempStack.iterator();
        while (iterator.hasNext()) {
            Screen state = iterator.next();
            state.render(batch);
        }
    }
    public void dispose() {
        typeScreen.clear();
    }
}
