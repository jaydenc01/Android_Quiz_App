package com.example.w21g12_quiz;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class QuizStartingActivity extends AppCompatActivity{
    final String TAG = "Quiz App....!";
    private int REQUEST_CODE=1;
   // String SHARED_PREF="sharedpreference";
   //  String HIGHSCORE="highscore";
      TextView textViewHighScore;
      TextView textViewHiUser;
  //  int highscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_starting);
        textViewHighScore=findViewById(R.id.textViewScore);
        textViewHiUser=findViewById(R.id.textViewHiUser);
        String subject = getIntent().getExtras().getString("SUBJECT");
        String user = getIntent().getExtras().getString("USERNAME");
    //   loadHighScore();
        TextView textViewUserDetail = findViewById(R.id.textViewUserDetail);
        try {

            String outputStr = "Welcome to " + subject + " Quiz";
            textViewHiUser.setText("Hello!! " + user);
            textViewUserDetail.setText(outputStr);
            textViewUserDetail.setGravity(Gravity.CENTER);
        } catch (Exception ex) {
            Log.d(TAG, ex.getMessage());
        }

        Button btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener((View v) ->{
            Intent intent = new Intent(this, QuizActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("SUBJECT", subject );
            bundle.putString("USERNAME", user);
            intent.putExtras(bundle);
            startActivityForResult(intent,REQUEST_CODE);

            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE){
            if(resultCode==RESULT_OK){
                int score=data.getIntExtra(QuizActivity.HSCORE,0);

                textViewHighScore.setText("Score:"+ score);
            }
        }
    }
   /* private void loadHighScore(){
        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREF,MODE_PRIVATE);
        highscore=sharedPreferences.getInt(HIGHSCORE,0);
        textViewHighScore.setText(" Score:"+highscore);
    }
    private void updateHighScore (int newHighScore) {
        highscore=newHighScore;
        textViewHighScore.setText("High Score:"+highscore);
        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREF,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt(HIGHSCORE,highscore);
        editor.commit();
    }*/

}