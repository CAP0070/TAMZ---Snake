package com.example.snake.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.snake.R;

public class HighscoreActivity extends Activity {
    public static final String SHARED_PREFERENCES = "sharedPrefs";
    public static final String HIGHSCORE = "highscore";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        setListeners();
        TextView text = findViewById(R.id.highscoreText2);
        text.setText(String.valueOf(loadHighscoreFromPreferences()));
    }

    public int loadHighscoreFromPreferences(){
        SharedPreferences sharedPreferences = this.getSharedPreferences(SnakeActivity.SHARED_PREFERENCES, MODE_PRIVATE);
        return sharedPreferences.getInt(SnakeActivity.HIGHSCORE, 0);
    }

    public void setListeners(){
        Button highscoreBackButton = findViewById(R.id.highscoreBackButton);
        highscoreBackButton.setOnClickListener(v -> {
            Intent intent = new Intent(HighscoreActivity.this, MainMenuActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
