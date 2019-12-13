package com.example.snake.snake;

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

    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

}
