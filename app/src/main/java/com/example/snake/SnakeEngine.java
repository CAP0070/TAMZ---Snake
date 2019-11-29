package com.example.snake;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
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
    private Bitmap[] bmp;
    private int [] currLevel;

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
        init();

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

    public void init() {
        currLevel = Levels.getLevel_1();

        bmp = new Bitmap[20];
        bmp[0] = BitmapFactory.decodeResource(getResources(), R.drawable.genericbrick);
        bmp[1] = BitmapFactory.decodeResource(getResources(), R.drawable.topleftcornerbrick);
        bmp[2] = BitmapFactory.decodeResource(getResources(), R.drawable.toprightcornerbrick);
        bmp[3] = BitmapFactory.decodeResource(getResources(), R.drawable.topgenericbrick);
        bmp[4] = BitmapFactory.decodeResource(getResources(), R.drawable.bottomleftcornerbrick);
        bmp[5] = BitmapFactory.decodeResource(getResources(), R.drawable.bottomrightcornerbrick);
        bmp[6] = BitmapFactory.decodeResource(getResources(), R.drawable.bottomgenericbrick);
        bmp[7] = BitmapFactory.decodeResource(getResources(), R.drawable.leftgenericbrick);
        bmp[8] = BitmapFactory.decodeResource(getResources(), R.drawable.rightgenericbrick);

        bmp[9] = BitmapFactory.decodeResource(getResources(), R.drawable.headdownwards);
        bmp[10] = BitmapFactory.decodeResource(getResources(), R.drawable.headupwards);
        bmp[11] = BitmapFactory.decodeResource(getResources(), R.drawable.headleftwards);
        bmp[12] = BitmapFactory.decodeResource(getResources(), R.drawable.headrightwards);
        bmp[13] = BitmapFactory.decodeResource(getResources(), R.drawable.snakebody);

        bmp[14] = BitmapFactory.decodeResource(getResources(), R.drawable.lemon);
        bmp[15] = BitmapFactory.decodeResource(getResources(), R.drawable.midgetapple);
        bmp[16] = BitmapFactory.decodeResource(getResources(), R.drawable.orange);
        invalidate();
    }

    private static final int GENERICBRICK = 0;
    private static final int TOPLEFTBRICK = 1;
    private static final int TOPRIGHTBRICK = 2;
    private static final int TOPGENERICBRICK = 3;
    private static final int BOTTOMLEFTBRICK = 4;
    private static final int BOTTOMRIGHTBRICK = 5;
    private static final int BOTTOMGENERICBRICK= 6;
    private static final int LEFTGENERICBRICK = 7;
    private static final int RIGHTGENERICBRICK = 8;

    private static final int HEADDOWNWARDS = 9;
    private static final int HEADUPWARDS = 10;
    private static final int HEADLEFTWARDS = 11;
    private static final int HEADRIGHTWARDS = 12;
    private static final int SNAKEBODY = 13;

    private static final int LEMON = 14;
    private static final int MIDGETAPPLE = 15;
    private static final int ORANGE = 16;

    public void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.argb(255, 26, 128, 182));
            paint.setColor(Color.argb(255, 255, 255, 255));

            paint.setTextSize(90);
            canvas.drawText("Score:" + score, 10, 70, paint);

            for (int i = 0; i < Levels.getDIMENSION() * 2; i++){
                {
                    for (int j = 0; j < Levels.getDIMENSION(); j++){
                        canvas.drawBitmap(bmp[currLevel[i * 10 + j]], null,
                                new Rect(j * blockSize, i * blockSize, (j+1) * blockSize, (i+1) * blockSize), null);
                    }
                }
            }

            for (int i = 0; i < snakeBody.size(); i++) {
                canvas.drawRect(snakeBody.get(i).x * blockSize, snakeBody.get(i).y * blockSize,
                        (snakeBody.get(i).x * blockSize) + blockSize,
                        (snakeBody.get(i).y * blockSize) + blockSize, paint);
            }

            canvas.drawRect(foodX * blockSize,
                    (foodY * blockSize),
                    (foodX * blockSize) + blockSize,
                    (foodY * blockSize) + blockSize,
                    paint);

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
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

