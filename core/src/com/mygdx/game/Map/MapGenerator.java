package com.mygdx.game.Map;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Map.Chunk;
import com.mygdx.game.ResourseManager;

import java.util.ArrayList;
import java.util.List;

public class MapGenerator {
    private ResourseManager resourseManager;
    private Chunk[][] chunks;
    private int numRows;
    private int numCols;
    private long seed;
    public MapGenerator(ResourseManager resourseManager, int numRows, long seed) {
        this.resourseManager = resourseManager;
        this.numRows = numRows;
        this.numCols = numRows;
        this.chunks = new Chunk[numRows][numCols];
        this.seed = seed;
    }
    public List<Chunk> generateMap() {
        // Создаем остров...
        List<Chunk> chunkList = new ArrayList<>();
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                Vector2 position = new Vector2(col * Chunk.size.x, row * Chunk.size.y);
                Chunk chunk = new Chunk(resourseManager, position, 1, col, row);
                chunks[col][row] = chunk;
                chunkList.add(chunk);
            }
        }
        // Добавляем моря...
        ChunkIteratorhasAllFourNeighbors(2);
        // Создаем заливы...
        ChunkIteratorRandomCheckingForBeingNear(1,2, 3, 10, 1);
        // Создаем озера...
        ChunkIteratorRandom(3, 5000);
        // Cуществует шанс того что большинство заливов появится рядом друг с другом
        // Поэтому создаем их их еще раз в больших промежутках
        ChunkIteratorRandomCheckingForBeingNear(1,2, 3, 7, 1);
        // Увеличиваем заливы и озёра, без этого пункта они будут размером в 1 чанк
        ChunkIteratorRandomCheckingForBeingNear(1,3,3,8, 20);
        // Еще раз увеличиваем всю воду на карте, и даем пограничным териториям тип 4 это важно для генерации пляжей
        ChunkIteratorRandomCheckingForBeingNear(1,3,4,1, 1);
        // Очищаем маленькие одиночные островки
        ChunkIteratorRandomCheckingForBeingNearAll(1, 4, 4, 1, 1);
        ChunkIteratorRandomCheckingForBeingNear(1, 2, 4, 1, 1);
        // Засыпаем пляжи песком
        ChunkIteratorRandomCheckingForBeingNear(1, 4, 5, 1, 1);
        ChunkIteratorRandomCheckingForBeingNear(1, 5, 5, 3, 1);
        // Создаем кристальный лес
        SetBiome( 6, 1);
        ChunkIteratorRandomCheckingForBeingNear(1, 6, 6, 5, RandInt(8, 12));
        ChunkIteratorRandomCheckingForBeingNearAll(1,6,6,1,1);
        //Создаем каньоны
        SetBiome( 7, RandInt(3, 5));
        ChunkIteratorRandomCheckingForBeingNear(1, 7, 7, 6, RandInt(10, 15));
        ChunkIteratorRandomCheckingForBeingNearAll(1,7,7,1,1);
        //Соединяем типы
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                Chunk chunk = chunks[col][row];
                if (chunk.getTipe() == 3 || chunk.getTipe() == 4) {
                    chunk.setBiomeType(2);
                }
            }
        }
        //Удаление биомов в песке и воде
        ChunkIteratorRandomCheckingForBeingNearAll(6,2,2,1,1);
        ChunkIteratorRandomCheckingForBeingNearAll(7,2,2,1,1);
        ChunkIteratorRandomCheckingForBeingNearAll(6,5,5,1,1);
        ChunkIteratorRandomCheckingForBeingNearAll(7,5,5,1,1);
        // Сглаживание...
        ChunkIteratorSmoothing(1, 5, 8,9,10,11);
        ChunkIteratorSmoothing(2, 5, 12,13,14,15);
        return chunkList;
    }

    private void SetBiome(int biome, int repetitions) {
        for (int a = 0; a < repetitions; a++){
            getChunk(RandInt(20, 100), RandInt(20, 100)).setBiomeType(biome);
        }
    }
    private Chunk getChunk(int x, int y) { // Получает чанк из заданной колонны и строчки
        if (x >= 0 && x < numCols && y >= 0 && y < numRows) {
            return chunks[x][y];
        } else {
            return null;
        }
    }
    private void ChunkIteratorhasAllFourNeighbors(int biome){ // Переберает все чанки и если у чанка нет всех 4 соседей устанавливает ему переданный биом
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                Chunk chunk = chunks[col][row];
                if (hasAllFourNeighbors(col, row) == false) {
                    chunk.setBiomeType(biome);
                }
            }
        }
    }
    private void ChunkIteratorRandomCheckingForBeingNear(int TargetChunk, int biome, int biome2, int rand, int repetitions){ // Переберает все чанки переделенного биома и если чанк граничит с выбранным биомом выдает ему тип biome2 c шансом 1 к rand
        for (int a = 0; a <= repetitions; a++){
            for (int row = 0; row < numRows; row++) {
                for (int col = 0; col < numCols; col++) {
                    Chunk chunk = chunks[col][row];
                    if (chunk.getTipe() == TargetChunk && CheckingForBeingNear(col, row, biome)) {
                        if (RandInt(1, rand) == 1){
                            chunk.setBiomeType(biome2);
                        }
                    }
                }
            }
        }
    }
    private void ChunkIteratorSmoothing(int TargetChunk, int biome, int first, int second, int third, int second2){
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                Chunk chunk = chunks[col][row];
                if (chunk.getTipe() == TargetChunk && CheckingForBeingNear(col, row, biome)) {
                    Smoothing(col, row, biome, first, second, third, second2);
                }
            }
        }
    }
    private void ChunkIteratorRandomCheckingForBeingNearAll(int TargetChunk, int biome, int biome2, int rand, int repetitions){ // Делает тоже что и метод выше но работает строго от 4 одинаковых соседей чанка
        for (int a = 0; a <= repetitions; a++){
            for (int row = 0; row < numRows; row++) {
                for (int col = 0; col < numCols; col++) {
                    Chunk chunk = chunks[col][row];
                    if (chunk.getTipe() == TargetChunk && CheckingForBeingNearAll(col, row, biome)) {
                        if (RandInt(1, rand) == 1){
                            chunk.setBiomeType(biome2);
                        }
                    }
                }
            }
        }
    }
    private void ChunkIteratorRandom(int biome, int rand){ // Переберает все чанки и дает ему выбранный biome с шансом 1 к rand
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                if (RandInt(1, rand) == 1){
                    Chunk chunk = chunks[col][row];
                    chunk.setBiomeType(biome);
                }
            }
        }
    }

    private boolean hasAllFourNeighbors(int x, int y) { // true если чанк имеет 4 соседа
        Chunk leftNeighbor = getChunk(x - 1, y);
        Chunk rightNeighbor = getChunk(x + 1, y);
        Chunk topNeighbor = getChunk(x, y + 1);
        Chunk bottomNeighbor = getChunk(x, y - 1);
        return leftNeighbor != null && rightNeighbor != null && topNeighbor != null && bottomNeighbor != null;
    }

    private boolean CheckingForBeingNearAll(int x, int y, int biome) { // true если чанк граничит с выбранным биомом по всем четырем сторонам
        Chunk leftNeighbor = getChunk(x - 1, y);
        Chunk rightNeighbor = getChunk(x + 1, y);
        Chunk topNeighbor = getChunk(x, y + 1);
        Chunk bottomNeighbor = getChunk(x, y - 1);
        if (leftNeighbor != null && rightNeighbor != null && topNeighbor != null && bottomNeighbor != null) {
            return leftNeighbor.getTipe() == biome && rightNeighbor.getTipe() == biome && topNeighbor.getTipe() == biome && bottomNeighbor.getTipe() == biome;
        }
        return false;
    }

    private boolean CheckingForBeingNear(int x, int y, int biome) { // true если чанк граничит с выбранным биомом
        Chunk leftNeighbor = getChunk(x - 1, y);
        Chunk rightNeighbor = getChunk(x + 1, y);
        Chunk topNeighbor = getChunk(x, y + 1);
        Chunk bottomNeighbor = getChunk(x, y - 1);
        if (leftNeighbor != null && rightNeighbor != null && topNeighbor != null && bottomNeighbor != null) {
            return leftNeighbor.getTipe() == biome || rightNeighbor.getTipe() == biome || topNeighbor.getTipe() == biome || bottomNeighbor.getTipe() == biome;
        }
        return false;
    }

    private void Smoothing(int x, int y, int biome, int first, int second, int third, int second2) { // Сглаживание границ биома
        int z = 0;
        Chunk leftNeighbor = getChunk(x - 1, y);
        Chunk rightNeighbor = getChunk(x + 1, y);
        Chunk topNeighbor = getChunk(x, y + 1);
        Chunk bottomNeighbor = getChunk(x, y - 1);

        if (leftNeighbor != null && rightNeighbor != null && topNeighbor != null && bottomNeighbor != null) {
            if (leftNeighbor.getTipe() == biome){
                z = z + 1;
            }
            if (rightNeighbor.getTipe() == biome){
                z = z + 1;
            }
            if (topNeighbor.getTipe() == biome){
                z = z + 1;
            }
            if (bottomNeighbor.getTipe() == biome){
                z = z + 1;
            }
            if (z == 1){
                getChunk(x, y).setBiomeType(first);
                if (leftNeighbor.getTipe() == biome){
                    getChunk(x, y).setRotation(90);
                }
                if (rightNeighbor.getTipe() == biome){
                    getChunk(x, y).setRotation(270);
                }
                if (topNeighbor.getTipe() == biome){
                    getChunk(x, y).setRotation(0);
                }
                if (bottomNeighbor.getTipe() == biome){
                    getChunk(x, y).setRotation(180);
                }
            }
            if (z == 2){
                getChunk(x, y).setBiomeType(second);
                if (topNeighbor.getTipe() == biome && rightNeighbor.getTipe() == biome){
                    getChunk(x, y).setRotation(0);
                }
                if (rightNeighbor.getTipe() == biome && bottomNeighbor.getTipe() == biome){
                    getChunk(x, y).setRotation(270);
                }
                if (leftNeighbor.getTipe() == biome && bottomNeighbor.getTipe() == biome){
                    getChunk(x, y).setRotation(180);
                }
                if (leftNeighbor.getTipe() == biome && topNeighbor.getTipe() == biome){
                    getChunk(x, y).setRotation(90);
                }
                if (leftNeighbor.getTipe() == biome && rightNeighbor.getTipe() == biome){
                    getChunk(x, y).setBiomeType(second2);
                    getChunk(x, y).setRotation(90);
                }
                if (topNeighbor.getTipe() == biome && bottomNeighbor.getTipe() == biome){
                    getChunk(x, y).setBiomeType(second2);
                    getChunk(x, y).setRotation(0);
                }
            }
            if (z == 3){
                getChunk(x, y).setBiomeType(third);
                if (topNeighbor.getTipe() == biome && rightNeighbor.getTipe() == biome && bottomNeighbor.getTipe() == biome){
                    getChunk(x, y).setRotation(90);
                }
                if (leftNeighbor.getTipe() == biome && rightNeighbor.getTipe() == biome && bottomNeighbor.getTipe() == biome){
                    getChunk(x, y).setRotation(0);
                }
                if (leftNeighbor.getTipe() == biome && topNeighbor.getTipe() == biome && bottomNeighbor.getTipe() == biome){
                    getChunk(x, y).setRotation(270);
                }
                if (leftNeighbor.getTipe() == biome && topNeighbor.getTipe() == biome && rightNeighbor.getTipe() == biome){
                    getChunk(x, y).setRotation(180);
                }
            }
            if (z == 4){
                getChunk(x, y).setBiomeType(biome);
            }
        }
    }

    public int RandInt(int min, int max) {   // Генерация псевдослучайного числа в заданном диапазоне, при одинаковых значениях seed число будет одно и тоже
        seed = (seed * 1103515245 + 12345) & 0x7fffffff;
        return min + (int) (seed % (max - min + 1));
    }
}
