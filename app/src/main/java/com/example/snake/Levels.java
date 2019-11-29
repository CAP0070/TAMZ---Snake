package com.example.snake;

public class Levels {
    private static final int WALL = 1;
    private static final int EMPTY = 0;
    private static final int DIMENSION = 10;

    private static int [] level_1 = {WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL,
                             WALL, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, WALL,
                             WALL, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, WALL,
                             WALL, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, WALL,
                             WALL, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, WALL,
                             WALL, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, WALL,
                             WALL, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, WALL,
                             WALL, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, WALL,
                             WALL, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, WALL,
                             WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL,};
    private static int [] level_2;

    public static int getDIMENSION() {
        return DIMENSION;
    }

    public static int[] getLevel_1() {
        return level_1;
    }

    public static int[] getLevel_2() {
        return level_2;
    }
}
