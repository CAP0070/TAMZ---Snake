package com.example.snake;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static android.content.ContentValues.TAG;

public class SnakeEngine extends SurfaceView implements Runnable {

    public enum Directions {
        UP,
        RIGHT,
        DOWN,
        LEFT
    }

    private Directions direction = Directions.RIGHT;

    private Thread thread = null;
    private Context context;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private Paint paint;

    private SoundPool soundPool;
    private int food_eaten = -1;
    private int snake_crash = -1;

    private int blockSize;
    private final int BLOCK_SIZE = 40;
    private int score;
    private ArrayList<Point> snakeBody = new ArrayList<>();
    private int numBlocksHigh;

    private int foodX;
    private int foodY;

    private long nextFrameTime;
    private final long FPS = 10;
    private final long MILLIS_PER_SECOND = 1000;

    private volatile boolean isPlaying;

    public SnakeEngine(Context context, Point size) {
        super(context);
        this.context = context;

        blockSize = size.x / BLOCK_SIZE;
        numBlocksHigh = size.y / blockSize;

        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        try {
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor;

            descriptor = assetManager.openFd("get_mouse_sound.ogg");
            food_eaten = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("death_sound.ogg");
            snake_crash = soundPool.load(descriptor, 0);

        } catch (IOException e) {

        }

        surfaceHolder = getHolder();
        paint = new Paint();


        newGame();
        this.setOnTouchListener(new SnakeSwipeListener(getContext()) {
            @Override
            public void onSwipeTop() {
                if (direction != Directions.DOWN) {
                    direction = Directions.UP;
                }
            }

            @Override
            public void onSwipeRight() {
                if (direction != Directions.LEFT) {
                    direction = Directions.RIGHT;
                }
            }

            @Override
            public void onSwipeLeft() {
                if (direction != Directions.RIGHT) {
                    direction = Directions.LEFT;
                }
            }

            @Override
            public void onSwipeBottom() {
                if (direction != Directions.UP) {
                    direction = Directions.DOWN;
                }
            }

        });
    }

    public void newGame() {
        snakeBody.clear();
        snakeBody.add(new Point(BLOCK_SIZE / 2, numBlocksHigh / 2));

        spawnFood();

        score = 0;

        nextFrameTime = System.currentTimeMillis();
    }

    public void spawnFood() {
        Random random = new Random();
        foodX = random.nextInt(BLOCK_SIZE - 1) + 1;
        foodY = random.nextInt(numBlocksHigh - 1) + 1;
    }

    private void foodEaten() {
        snakeBody.add(new Point());
        spawnFood();
        score = score + 1;
        soundPool.play(food_eaten, 1, 1, 0, 0, 1);
    }

    private void moveSnake() {
        for (Point p : snakeBody)
            Log.d(TAG, "moveSnake: " + p.x);

        for (int i = snakeBody.size() - 1; i > 0; i--) {
            snakeBody.get(i).x = snakeBody.get(i - 1).x;
            snakeBody.get(i).y = snakeBody.get(i - 1).y;
        }

        switch (direction) {
            case UP:
                snakeBody.get(0).y--;
                break;

            case RIGHT:
                snakeBody.get(0).x++;
                break;

            case DOWN:
                snakeBody.get(0).y++;
                break;

            case LEFT:
                snakeBody.get(0).x--;
                break;
        }
    }

    private boolean detectDeath() {
        boolean dead = false;

        if (snakeBody.get(0).x == -1) dead = true;
        if (snakeBody.get(0).x >= BLOCK_SIZE) dead = true;
        if (snakeBody.get(0).y == -1) dead = true;
        if (snakeBody.get(0).y == numBlocksHigh) dead = true;

        for (int i = snakeBody.size() - 1; i > 0; i--) {
            if ((i > 4) && (snakeBody.get(0).x == snakeBody.get(i).x) && (snakeBody.get(0).y == snakeBody.get(i).y)) {
                dead = true;
            }
        }

        return dead;
    }

    public void update() {
        if (snakeBody.get(0).x == foodX && snakeBody.get(0).y == foodY) {
            foodEaten();
        }

        moveSnake();

        if (detectDeath()) {
            soundPool.play(snake_crash, 1, 1, 0, 0, 1);
            newGame();
        }
    }

    public boolean updateRequired() {
        if (nextFrameTime <= System.currentTimeMillis()) {
            nextFrameTime = System.currentTimeMillis() + MILLIS_PER_SECOND / FPS;
            return true;
        }
        return false;
    }


    public void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.argb(255, 26, 128, 182));
            paint.setColor(Color.argb(255, 255, 255, 255));

            paint.setTextSize(90);
            canvas.drawText("Score:" + score, 10, 70, paint);

            for (int i = 0; i < snakeBody.size(); i++) {
                canvas.drawRect(snakeBody.get(i).x * blockSize, snakeBody.get(i).y * blockSize,
                        (snakeBody.get(i).x * blockSize) + blockSize,
                        (snakeBody.get(i).y * blockSize) + blockSize, paint);
            }

            paint.setColor(Color.argb(255, 255, 0, 0));
            canvas.drawRect(foodX * blockSize,
                    (foodY * blockSize),
                    (foodX * blockSize) + blockSize,
                    (foodY * blockSize) + blockSize,
                    paint);

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void run() {
        while (isPlaying) {
            if (updateRequired()) {
                update();
                draw();
            }
        }
    }

    public void pause() {
        isPlaying = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            // Error
        }
    }

    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }
}

