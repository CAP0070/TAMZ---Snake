package com.example.snake;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HighscoreActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        setListeners();
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
