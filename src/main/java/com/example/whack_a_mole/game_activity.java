package com.example.whack_a_mole;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class game_activity extends AppCompatActivity implements View.OnClickListener{

    private boolean hasMole[] = {false,false,false,false,false,false};
    private Button [] holes = new Button[6];
    private int playerScore = 0;
    private int previousHole = 0;

    private int bestScore;
    private SharedPreferences preferences;

    private final long START_TIME = 30000;

    private CountDownTimer countDownTimer;
    private TextView timer;
    private TextView score;
    private boolean timerRun = true;
    private long timeOut = 0;
    private int buttnNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_activity);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        choose_hole();
        score = (TextView) findViewById(R.id.score);

        for (int i =0; i < hasMole.length; ++i){
            String holeID = "hole_"+(i+1);
            int resourceID= getResources().getIdentifier(holeID, "id", getPackageName());
            holes[i] = (Button) findViewById(resourceID);
            holes[i].setOnClickListener(this);
        }

        timer = findViewById(R.id.countdown);
        start_timer();
    }

    private void start_timer(){
        countDownTimer = new CountDownTimer(START_TIME, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeOut = millisUntilFinished;
                timer.setText("0:"+millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {
                timerRun = false;
                stop_timer();
            }
        }.start();
    }

    private void stop_timer(){
        preferences = getApplicationContext().getSharedPreferences("MaxScore", MODE_PRIVATE);
        bestScore = preferences.getInt("maxScore", bestScore);
        SharedPreferences.Editor editor = preferences.edit();
        Log.i("score", ""+bestScore);
        if(playerScore > bestScore) {
            bestScore = playerScore;
            editor.putInt("maxScore", bestScore);
            editor.commit();
        }
        Log.i("RESULT", " "+ playerScore);
        Intent intent = new Intent(this, Result.class);
        intent.putExtra("yourScore", String.valueOf(playerScore));
        intent.putExtra("best", String.valueOf(bestScore));
        startActivity(intent);
        finish();
    }

    private void choose_hole(){
        buttnNum = new Random().nextInt(6);
        while (previousHole == buttnNum) {
            buttnNum = new Random().nextInt(6);
        }
        previousHole = buttnNum;
        hasMole[buttnNum] = true;
        String holeID = "hole_"+(buttnNum+1);
        int resourceID= getResources().getIdentifier(holeID, "id", getPackageName());
        View v = findViewById(resourceID);
        setMole(v);
    }

    @Override
    public void onClick(View v) {
        Log.i("test", "Clicked!");
        int position = 0;
        String holeID = "hole_"+(position+1);
        int resourceID= getResources().getIdentifier(holeID, "id", getPackageName());
        for(int j = 0; j < hasMole.length; ++j)
        {
            if(hasMole[j] == true){
                position = j;
                holeID = "hole_"+(position+1);
                resourceID= getResources().getIdentifier(holeID, "id", getPackageName());
            }
        }
        if(v.getId() == resourceID){
            try {
                v.setBackgroundResource(R.drawable.hole);
                hasMole[position] = false;
                //choose_hole();
                playerScore++;
                score.setText(" "+playerScore);
            }
            catch (Exception e){
                Log.e("ERROR!", " "+e);
            }
        }
    }

    private void setMole(View view){
        view.setBackgroundResource(R.drawable.mole);
        CountDownTimer moleExist = new CountDownTimer(500, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                view.setBackgroundResource(R.drawable.hole);
                hasMole[buttnNum] = false;
                choose_hole();
            }
        }.start();
    }

    @Override
    public void onBackPressed() {

    }
}