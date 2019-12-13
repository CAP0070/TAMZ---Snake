package com.example.snake.activities;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.snake.snake.SnakeEngine;

public class SnakeActivity extends Activity {
    SnakeEngine snakeEngine;
    public static final String SHARED_PREFERENCES = "sharedPrefs";
    public static final String HIGHSCORE = "highscore";

    private static SnakeActivity single_instance = null;
    public static SnakeActivity getInstance()
    {
        if (single_instance == null)
        {
            single_instance = new SnakeActivity();
        }
        return single_instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        single_instance = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        snakeEngine = new SnakeEngine(this, size);
        setContentView(snakeEngine);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},1);
            }
        }



    @Override
    public void onResume() {
        super.onResume();
        snakeEngine.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        snakeEngine.pause();
    }
}
