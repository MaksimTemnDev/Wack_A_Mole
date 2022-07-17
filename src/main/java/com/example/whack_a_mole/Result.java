package com.example.whack_a_mole;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Result extends AppCompatActivity{

    private Button toMain;
    private Button toGame;
    private TextView score;
    private TextView best;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Intent intent = getIntent();
        toMain = (Button) findViewById(R.id.button_menu);
        toGame = (Button) findViewById(R.id.button_play_again);
        score = (TextView) findViewById(R.id.scoreCount);
        best = (TextView) findViewById(R.id.best_view);
        String res = intent.getStringExtra("yourScore");
        String bestt = intent.getStringExtra("best");
        Log.i("Test", res);
        score.setText(res);
        best.setText("Best score is: "+ bestt);
        toMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Result.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        toGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Result.this, game_activity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}