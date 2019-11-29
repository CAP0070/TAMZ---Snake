package com.example.snake;

public class Levels {
    private static final int WALL = 1;
    private static final int EMPTY = 0;

    private int [] level_1 = {WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL,
                             WALL, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, WALL,
                             WALL, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, WALL,
                             WALL, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, WALL,
                             WALL, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, WALL,
                             WALL, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, WALL,
                             WALL, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, WALL,
                             WALL, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, WALL,
                             WALL, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, WALL,
                             WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL,};
    private int [] level_2;

    public int[] getLevel_1() {
        return level_1;
    }

    public void setLevel_1(int[] level_1) {
        this.level_1 = level_1;
    }

    public int[] getLevel_2() {
        return level_2;
    }

    public void setLevel_2(int[] level_2) {
        this.level_2 = level_2;
    }


}
