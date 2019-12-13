package com.example.snake.snake;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.snake.R;
import com.example.snake.activities.SnakeActivity;
import com.example.snake.other.Geolocator;
import com.example.snake.activities.LevelSelectionActivity;
import com.example.snake.other.TwitterSender;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

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
    private Bitmap currHead;
    private int [] currLevel;
    private ObjectsHandler handler = new ObjectsHandler();
    private int time;
    Thread timeThread;
    Thread twitterThread;
    private static TwitterSender twitter = new TwitterSender();
    private static Geolocator geolocator = new Geolocator();

    private SoundPool soundPool;
    private int food_eaten = -1;
    private int food_spawn = -1;

    private int blockSize;
    private final int BLOCK_SIZE = 24;
    private int score;
    private ArrayList<Point> snakeBody = new ArrayList<>();
    private int numBlocksHigh;

    private int foodX;
    private int foodY;

    private long nextFrameTime;
    private long FPS = 7;
    private final long MILLIS_PER_SECOND = 1000;


    private volatile boolean isPlaying;
    private volatile boolean soundOn = true;
    SnakeEngineDatabase database;



    public SnakeEngine(Context context, Point size) {
        super(context);
        this.context = context;
        database = new SnakeEngineDatabase(context);

        blockSize = size.x / BLOCK_SIZE;
        numBlocksHigh = size.y / blockSize;
        init();
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        try {
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor;

            descriptor = assetManager.openFd("sounds/appleDeath.mp3");
            food_eaten = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("sounds/appleSpawn.mp3");
            food_spawn = soundPool.load(descriptor, 0);

        } catch (IOException e) {

        }

        surfaceHolder = getHolder();
        paint = new Paint();

        setListeners();
        setTimer();
        newGame();
        twitter.configureTwitter();
        geolocator.setGeo();
    }

    private void setTimer() {
        Runnable runnable = () -> {
            while (true){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                time++;
            }
        };
        timeThread = new Thread(runnable);
        timeThread.start();
    }


    private void setListeners() {
        this.setOnTouchListener(new SnakeSwipeListener(getContext()) {
            @Override
            public void clicked(MotionEvent event){
                if (event.getX() >= 19 * blockSize && event.getX() <= (19 * blockSize) + blockSize
                        && event.getY() >= 0 * blockSize && event.getY() <= (0 * blockSize) + blockSize - 5)
                    FPS++;
                else if (event.getX() >= 20 * blockSize + blockSize / 2 && event.getX() <= (20 * blockSize) + blockSize / 2 + blockSize
                        && event.getY() >= 0 * blockSize && event.getY() <= (0 * blockSize) + blockSize - 5 && FPS > 1) {
                    FPS--;
                } else if (event.getX() >= 22 * blockSize + blockSize / 2 && event.getX() <= (22 * blockSize) + blockSize / 2 + blockSize
                        && event.getY() >= 0 * blockSize && event.getY() <= (0 * blockSize) + blockSize - 5)
                    soundOn = !soundOn;
            }

            @Override
            public void onSwipeTop() {
                if (direction != Directions.DOWN) {
                    direction = Directions.UP;
                    currHead = BitmapFactory.decodeResource(getResources(), R.drawable.headupwards);
                }
            }

            @Override
            public void onSwipeRight() {
                if (direction != Directions.LEFT) {
                    direction = Directions.RIGHT;
                    currHead = BitmapFactory.decodeResource(getResources(), R.drawable.headrightwards);
                }
            }

            @Override
            public void onSwipeLeft() {
                if (direction != Directions.RIGHT) {
                    direction = Directions.LEFT;
                    currHead = BitmapFactory.decodeResource(getResources(), R.drawable.headleftwards);
                }
            }

            @Override
            public void onSwipeBottom() {
                if (direction != Directions.UP) {
                    direction = Directions.DOWN;
                    currHead = BitmapFactory.decodeResource(getResources(), R.drawable.headdownwards);
                }
            }
        });
    }

    public void newGame() {
        init();
        score = 0;
        extractDataFromDatabase();
        nextFrameTime = System.currentTimeMillis();
    }

    private void extractDataFromDatabase() {
        snakeBody.clear();

        Cursor entries = SnakeEngineDatabase.getEntries();
        entries.moveToNext();
        String coords = entries.getString(1);
        while (coords.contains("x")){
            int x = Integer.parseInt(coords.substring(0, coords.indexOf('x')));
            int y = Integer.parseInt(coords.substring(coords.indexOf('x')+1, coords.indexOf('y')));
            snakeBody.add(new Point(x, y));
            coords = coords.substring(coords.indexOf('y')+1);
            Log.d(TAG, "newGame: ahoj");
        }

        Log.d(TAG, "extractDataFromDatabase: " + entries.getString(0));
        time = Integer.parseInt(entries.getString(2));
        score = Integer.parseInt(entries.getString(3));
        switch (Integer.parseInt(entries.getString(4))){
            case 1: this.direction = Directions.RIGHT;
                currHead = BitmapFactory.decodeResource(getResources(), R.drawable.headrightwards);
                break;
            case 2: this.direction = Directions.LEFT;
                currHead = BitmapFactory.decodeResource(getResources(), R.drawable.headleftwards);
                break;
            case 3: this.direction = Directions.UP;
                currHead = BitmapFactory.decodeResource(getResources(), R.drawable.headupwards);
                break;
            case 4: this.direction = Directions.DOWN;
                currHead = BitmapFactory.decodeResource(getResources(), R.drawable.headdownwards);
                break;
        }
        String foodCoords = entries.getString(5);
        foodX = Integer.parseInt(foodCoords.substring(0, foodCoords.indexOf('x')));
        foodY = Integer.parseInt(foodCoords.substring(foodCoords.indexOf('x')+1, foodCoords.indexOf('y')));
        soundOn = Integer.parseInt(entries.getString(6)) == 1 ? true : false;
        FPS = Long.parseLong(entries.getString(7));
    }

    public int loadLevelFromSharedPreferences(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SnakeActivity.SHARED_PREFERENCES, MODE_PRIVATE);
        return sharedPreferences.getInt(LevelSelectionActivity.LEVEL, 1);
    }

    public void init() {
        currLevel = LevelLoader.loadLevel(this, loadLevelFromSharedPreferences(), 12, 24);
        handler.clearObstacles();
        for (int i = 0; i < Levels.getHEIGHT(); i++){
            {
                for (int j = 0; j < Levels.getWIDTH(); j++){
                    if (currLevel[i * Levels.getWIDTH() + j] == 0) continue;


                    else if (currLevel[i * Levels.getWIDTH() + j] >= 1 &&
                             currLevel[i * Levels.getWIDTH() + j] <= 8  ||
                             currLevel[i * Levels.getWIDTH() + j] == 17 ||
                             currLevel[i * Levels.getWIDTH() + j] == 18 ){
                        handler.addObstacle(new Obstacle(i * blockSize, j * blockSize));
                    }
                }
            }
        }

        bmp = new Bitmap[23];
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
        bmp[17] = BitmapFactory.decodeResource(getResources(), R.drawable.stone);
        bmp[18] = BitmapFactory.decodeResource(getResources(), R.drawable.genericbrick);

        bmp[19] = BitmapFactory.decodeResource(getResources(), R.drawable.plusicon);
        bmp[20] = BitmapFactory.decodeResource(getResources(), R.drawable.minusicon);

        bmp[21] = BitmapFactory.decodeResource(getResources(), R.drawable.soundon);
        bmp[22] = BitmapFactory.decodeResource(getResources(), R.drawable.soundoff);
    }

    public void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.argb(255, 0, 0, 0));



            for (int i = 0; i < Levels.getHEIGHT(); i++){
                {
                    for (int j = 1; j < Levels.getWIDTH(); j++){
                        if (currLevel[i * Levels.getWIDTH() + j] == 0) continue;
                        canvas.drawBitmap(bmp[currLevel[i * Levels.getWIDTH() + j]], null,
                                new Rect(i * blockSize, j * blockSize, (i+1) * blockSize, (j+1) * blockSize), null);
                    }
                }
            }

            for (int i = 0; i < snakeBody.size(); i++) {
                if (i == 0) canvas.drawBitmap(currHead, null,
                        new Rect(snakeBody.get(i).x * blockSize, snakeBody.get(i).y * blockSize,
                                (snakeBody.get(i).x * blockSize) + blockSize,
                                (snakeBody.get(i).y * blockSize) + blockSize), null);
                else canvas.drawBitmap(bmp[13], null,
                        new Rect(snakeBody.get(i).x * blockSize, snakeBody.get(i).y * blockSize,
                                (snakeBody.get(i).x * blockSize) + blockSize,
                                (snakeBody.get(i).y * blockSize) + blockSize), null);
            }

            canvas.drawBitmap(bmp[14], null,
                    new Rect(foodX * blockSize, foodY * blockSize,
                            (foodX * blockSize) + blockSize,
                            (foodY * blockSize) + blockSize), null);

            canvas.drawBitmap(bmp[19], null,
                    new Rect(19 * blockSize, 0 * blockSize,
                            (19 * blockSize) + blockSize,
                            (0 * blockSize) + blockSize-5), null);

            canvas.drawBitmap(bmp[20], null,
                    new Rect(20 * blockSize + blockSize/2, 0 * blockSize,
                            (20 * blockSize) + blockSize/2 + blockSize,
                            (0 * blockSize) + blockSize-5), null);

            canvas.drawBitmap(bmp[soundOn ? 21 : 22], null,
                    new Rect(22 * blockSize + blockSize/2, 0 * blockSize,
                            (22 * blockSize) + blockSize/2 + blockSize,
                            (0 * blockSize) + blockSize-5), null);

            paint.setColor(Color.argb(255, 255, 255, 255));
            paint.setTextSize(75);
            canvas.drawText("Score:" + score, 15, 55, paint);
            canvas.drawText("Time:" + time, 500, 55, paint);


            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void spawnFood() {
        Random random = new Random();
        foodX = random.nextInt(BLOCK_SIZE - 1) + 1;
        foodY = random.nextInt(numBlocksHigh - 1) + 1;
        for (Obstacle obstacle : handler.getHandler()){ //jidlo se nesmi spawnout na prekazce
            if (obstacle.getX()/blockSize == foodX && obstacle.getY()/blockSize == foodY){
                spawnFood();
            }
        }

        for (Point p : snakeBody){ //jidlo se nesmi spawnout na tele hada
            if (p.x == foodX && p.y == foodY){
                spawnFood();
            }
        }
        if (soundOn) soundPool.play(food_spawn, 1, 1, 0, 0, 1);
    }

    private void foodEaten() {
        snakeBody.add(new Point());
        spawnFood();
        score = score + 1;
        if (soundOn) soundPool.play(food_eaten, 1, 1, 0, 0, 1);
    }

    private void moveSnake() {
        for (int i = snakeBody.size() - 1; i > 0; i--) {
            snakeBody.get(i).x = snakeBody.get(i - 1).x;
            snakeBody.get(i).y = snakeBody.get(i - 1).y;
        }

        String aux = "";
        String directionAux = "";
        for (Point p : snakeBody){
            aux += p.x + "x" + p.y + "y";
        }
        if (direction == Directions.RIGHT){
            directionAux = "1";
        } else if (direction == Directions.LEFT) {
            directionAux = "2";
        } else if (direction == Directions.UP) {
            directionAux = "3";
        } else if (direction == Directions.DOWN) {
            directionAux = "4";
        }
        SnakeEngineDatabase.UpdateData(aux, String.valueOf(time), String.valueOf(score), directionAux,
                foodX + "x" + foodY + "y", soundOn ? "1" : "0", String.valueOf(FPS));

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
        for(Obstacle obstacle : handler.getHandler()){
            if (snakeBody.get(0).x == obstacle.getX()/blockSize &&
                snakeBody.get(0).y == obstacle.getY()/blockSize){
                dead = true;
                break;
            }
        }

        for (int i = snakeBody.size() - 1; i > 0; i--) {
            if ((i > 4) && (snakeBody.get(0).x == snakeBody.get(i).x) && (snakeBody.get(0).y == snakeBody.get(i).y)) {
                dead = true;
                break;
            }
        }

        if (dead){
            SnakeEngineDatabase.UpdateData("12x6y", "0", "0", "1" , "4x6y", soundOn ? "1" : "0", String.valueOf(FPS));
            checkHighscorePreferences();
            time = 0;
            sendTweet();
        }

        return dead;
    }

    private void sendTweet() {
        Runnable runnable = () -> {
            twitter.sendTweet(score, geolocator.city);
        };
        twitterThread = new Thread(runnable);
        twitterThread.start();
    }

    public void checkHighscorePreferences(){
        if (score > loadHighscoreFromPreferences()) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(SnakeActivity.SHARED_PREFERENCES, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(SnakeActivity.HIGHSCORE, score);
            editor.apply();
            Log.d(TAG, "checkHighscorePreferences: new: " + score);
        }
    }

    public int loadHighscoreFromPreferences(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SnakeActivity.SHARED_PREFERENCES, MODE_PRIVATE);
        return sharedPreferences.getInt(SnakeActivity.HIGHSCORE, 0);
    }

    public void update() {
        if (snakeBody.get(0).x == foodX && snakeBody.get(0).y == foodY) {
            foodEaten();
        }

        moveSnake();

        if (detectDeath()) {
            soundPool.play(food_spawn, 1, 1, 0, 0, 1);
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
        } catch (Exception e) {
            Log.d(TAG, "pause: " + e);
        }
    }

    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }
}

