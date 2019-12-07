package com.example.snake;

public class Levels {
    private static final int EMPTY = 0;
    private static final int WIDTH = 12;
    private static final int HEIGHT = 24;

    private static final int GENERICBRICK = 0;
    private static final int TOPLEFTBRICK = 1;
    private static final int TOPRIGHTBRICK = 2;
    private static final int TOPGENERICBRICK = 3;
    private static final int BOTTOMLEFTBRICK = 4;
    private static final int BOTTOMRIGHTBRICK = 5;
    private static final int BOTTOMGENERICBRICK= 6;
    private static final int LEFTBRICK = 7;
    private static final int RIGHTBRICK = 8;

    private static final int HEADDOWNWARDS = 9;
    private static final int HEADUPWARDS = 10;
    private static final int HEADLEFTWARDS = 11;
    private static final int HEADRIGHTWARDS = 12;
    private static final int SNAKEBODY = 13;

    private static final int LEMON = 14;
    private static final int MIDGETAPPLE = 15;
    private static final int ORANGE = 16;
    private static final int STONE = 17;

    private static int [] level_1 = {TOPLEFTBRICK, LEFTBRICK, LEFTBRICK, LEFTBRICK, LEFTBRICK, LEFTBRICK, LEFTBRICK, LEFTBRICK, LEFTBRICK, LEFTBRICK, LEFTBRICK, BOTTOMLEFTBRICK,
                                     TOPGENERICBRICK, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, BOTTOMGENERICBRICK,
                                     TOPGENERICBRICK, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, BOTTOMGENERICBRICK,
                                     TOPGENERICBRICK, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, BOTTOMGENERICBRICK,
                                     TOPGENERICBRICK, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, BOTTOMGENERICBRICK,
                                     TOPGENERICBRICK, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, BOTTOMGENERICBRICK,
                                     TOPGENERICBRICK, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, BOTTOMGENERICBRICK,
                                     TOPGENERICBRICK, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, BOTTOMGENERICBRICK,
                                     TOPGENERICBRICK, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, BOTTOMGENERICBRICK,
                                     TOPGENERICBRICK, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, BOTTOMGENERICBRICK,
                                     TOPGENERICBRICK, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, BOTTOMGENERICBRICK,
                                     TOPGENERICBRICK, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, BOTTOMGENERICBRICK,
                                     TOPGENERICBRICK, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, BOTTOMGENERICBRICK,
                                     TOPGENERICBRICK, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, BOTTOMGENERICBRICK,
                                     TOPGENERICBRICK, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, BOTTOMGENERICBRICK,
                                     TOPGENERICBRICK, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, BOTTOMGENERICBRICK,
                                     TOPGENERICBRICK, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, BOTTOMGENERICBRICK,
                                     TOPGENERICBRICK, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, BOTTOMGENERICBRICK,
                                     TOPGENERICBRICK, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, BOTTOMGENERICBRICK,
                                     TOPGENERICBRICK, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, BOTTOMGENERICBRICK,
                                     TOPGENERICBRICK, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, BOTTOMGENERICBRICK,
                                     TOPGENERICBRICK, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, BOTTOMGENERICBRICK,
                                     TOPGENERICBRICK, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, BOTTOMGENERICBRICK,
                                     TOPRIGHTBRICK, RIGHTBRICK, RIGHTBRICK, RIGHTBRICK, RIGHTBRICK, RIGHTBRICK, RIGHTBRICK, RIGHTBRICK, RIGHTBRICK, RIGHTBRICK, RIGHTBRICK, BOTTOMRIGHTBRICK,};
    private static int [] level_2;

    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public static int[] getLevel_1() {
        return level_1;
    }

    public static int[] getLevel_2() {
        return level_2;
    }
}
