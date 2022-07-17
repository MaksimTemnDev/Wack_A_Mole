package com.example.whack_a_mole;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        int i = 0;
        SharedPreferences settings = getApplicationContext().getSharedPreferences("MaxScore", MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        i = settings.getInt("maxScore", i);
        Log.i("msg", " "+i);
        if(i < 1) {
            editor.putInt("maxScore", 0);
            editor.commit();
        }
        btnGame = (Button) findViewById(R.id.button_play);
    }

    public void startGame(View view){
        if(view.getId() == R.id.button_play){
            Intent intent = new Intent(this, game_activity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}