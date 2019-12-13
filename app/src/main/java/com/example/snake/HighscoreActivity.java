package com.example.snake;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HighscoreActivity extends Activity {
    public static final String SHARED_PREFERENCES = "sharedPrefs";
    public static final String HIGHSCORE = "highscore";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        setListeners();
        TextView text = (TextView) findViewById(R.id.highscoreText2);
        text.setText(String.valueOf(loadHighscoreFromPreferences()));
    }

    public int loadHighscoreFromPreferences(){
        SharedPreferences sharedPreferences = this.getSharedPreferences(SnakeActivity.SHARED_PREFERENCES, MODE_PRIVATE);
        return sharedPreferences.getInt(SnakeActivity.HIGHSCORE, 0);
    }

    public void setListeners(){
        Button highscoreBackButton = (Button) findViewById(R.id.highscoreBackButton);
        highscoreBackButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(HighscoreActivity.this, MainMenuActivity.class);
                startActivity(intent);
            }
        });
    }
}
