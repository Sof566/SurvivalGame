package com.mygdx.game.GameInterface;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Button;
import com.mygdx.game.Chunk;
import com.mygdx.game.Entity.Entity;
import com.mygdx.game.Items.Items;
import com.mygdx.game.ResourseManager;

import java.util.ArrayList;
import java.util.List;

public class UiInventory {
    ResourseManager resourseManager;
    private boolean isOpen;
    private int  numRows = 3;
    private int  numCols = 5;
    private InventorySlot[][] items;
    private List<InventorySlot> itemsList = new ArrayList<>();
    public enum State {
        UP, DOWN
    }
    public State state = State.DOWN;

    public UiInventory(ResourseManager resourseManager) {
        this.resourseManager = resourseManager;
        this.isOpen = false;
        this.items = new InventorySlot[numCols][numRows];
    }

    public List<InventorySlot> GenerateInventory(){ // Создает пустой инвентарь, используйте в начале игры при заходе в мир
        int cellNumber = 1;
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                Vector2 position = new Vector2(col * 70, row * 70);
                InventorySlot inventorySlot = new InventorySlot(resourseManager, position, 0, cellNumber);
                items[col][row] = inventorySlot;
                itemsList.add(inventorySlot);
                cellNumber++;
            }
        }
        return itemsList;
    }

    public void ChangeInventoryPlus(int ID){  // Добавит в инвентарь вещь при условии свободного места
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                if (items[col][row].getTipe() == 0){
                    items[col][row].setItemType(ID);
                }
            }
        }
    }
    public void ChangeInventoryMinus(int ID){  // Удалить из инвентаря предмет
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                if (items[col][row].getTipe() == ID){
                    items[col][row].setItemType(0);
                }
            }
        }
    }

    public void UpInventory(){
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                switch (state) {
                    case DOWN:
                        items[col][row].setVector(-1);
                        state = State.UP;
                        break;
                    case UP:
                        items[col][row].setVector(1);
                        state = State.DOWN;
                        break;
                }
            }
        }
    }

    public void dispose() {
    }
}
