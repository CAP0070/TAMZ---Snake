package com.example.snake;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

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
        Button levelOneButton = (Button) findViewById(R.id.levelOneButton);
        levelOneButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(LEVEL, 1);
                editor.apply();
            }
        });

        Button levelTwoButton = (Button) findViewById(R.id.levelTwoButton);
        levelTwoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(LEVEL, 2);
                editor.apply();
            }
        });

        Button levelBackButton = (Button) findViewById(R.id.levelBackButton);
        levelBackButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LevelSelectionActivity.this, MainMenuActivity.class);
                startActivity(intent);
            }
        });
    }
}
