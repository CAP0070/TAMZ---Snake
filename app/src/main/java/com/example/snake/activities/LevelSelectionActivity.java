package com.example.snake.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import com.example.snake.R;

public class LevelSelectionActivity extends Activity {
    public static final String SHARED_PREFERENCES = "sharedPrefs";
    public static final String LEVEL = "level";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);
        setListeners();
    }

    public void setListeners(){
        Button levelOneButton = findViewById(R.id.levelOneButton);
        levelOneButton.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(LEVEL, 1);
            editor.apply();
        });

        Button levelTwoButton = findViewById(R.id.levelTwoButton);
        levelTwoButton.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(LEVEL, 2);
            editor.apply();
        });

        Button levelBackButton = findViewById(R.id.levelBackButton);
        levelBackButton.setOnClickListener(v -> {
            Intent intent = new Intent(LevelSelectionActivity.this, MainMenuActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
