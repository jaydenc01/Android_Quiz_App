package com.example.w21g12_quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class QuizActivity extends AppCompatActivity {

    public static final String HSCORE="score";
    private static final long  TIMER_IN_MILLISS=15000;
    TextView textViewQuestion;
    TextView textViewScore;
    TextView textViewQuestionCount;
    TextView textViewTimer;
    RadioGroup radGroup;
    RadioButton radOption1;
    RadioButton radOption2;
    RadioButton radOption3;
    RadioButton radOption4;
    Button btnConfirm;
    private ColorStateList textColorDefaultRadioBtn;
    private ColorStateList textColorDefaultTimer;
    private CountDownTimer countDownTimer;
    private long timeLeft;
    private List<Question> questionsList;
    private int currentQuestionCount;
    private int totalQuestions;
    private Question currentQuestion;
    Date currentTime;
    List<String[]> marksDataArrayList = new ArrayList<>();

    private int score;
    private boolean answered;

    DBHelper dbHelper;
    private long backPressedTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE); //hides the quiz questions when in app switcher.
        textViewQuestion=findViewById(R.id.textViewQuestion);
        textViewScore=findViewById(R.id.textViewScore);
        textViewQuestionCount=findViewById(R.id.textViewNoQues);
        textViewTimer=findViewById(R.id.textViewTimer);
        radGroup=findViewById(R.id.radGroupQuestion);
        radOption1=findViewById(R.id.radBtnOption1);
        radOption2=findViewById(R.id.radBtnOption2);
        radOption3=findViewById(R.id.radBtnOption3);
        radOption4=findViewById(R.id.radBtnOption4);
        btnConfirm=findViewById(R.id.btnConfirmNext);

        textColorDefaultRadioBtn=radOption1.getTextColors();
        textColorDefaultTimer=textViewTimer.getTextColors();


       // DBHelper dbHelper=new DBHelper(this);
        dbHelper=new DBHelper(this);
        String subject = getIntent().getExtras().getString("SUBJECT");
        questionsList=dbHelper.browseQuestions(subject);
        totalQuestions=questionsList.size();
        Collections.shuffle(questionsList);
        showNextQuestion();
        btnConfirm.setOnClickListener((View v)->{

            if(!answered){
                if(radOption1.isChecked()||radOption2.isChecked()||radOption3.isChecked()||radOption4.isChecked()){
                    checkAnswer();
                }else{
                    Toast.makeText(this, "Select the answer", Toast.LENGTH_SHORT).show();
                }
            }else{
                showNextQuestion();
            }
        });
    }
    private void showNextQuestion(){

        String subject = getIntent().getExtras().getString("SUBJECT");
        String user = getIntent().getExtras().getString("USERNAME");
        dbHelper=new DBHelper(this);

        radOption1.setTextColor(textColorDefaultRadioBtn);
        radOption2.setTextColor(textColorDefaultRadioBtn);
        radOption3.setTextColor(textColorDefaultRadioBtn);
        radOption4.setTextColor(textColorDefaultRadioBtn);
        radGroup.clearCheck();
        try {
            if (currentQuestionCount < totalQuestions) {
                currentQuestion = questionsList.get(currentQuestionCount);
                textViewQuestion.setText(currentQuestion.getQuestion());
                radOption1.setText(currentQuestion.getOption1());
                radOption2.setText(currentQuestion.getOption2());
                radOption3.setText(currentQuestion.getOption3());
                radOption4.setText(currentQuestion.getOption4());
                currentQuestionCount++;
                textViewQuestionCount.setText("Question: "+currentQuestionCount +"/" +totalQuestions);
                answered=false;
                btnConfirm.setText("Confirm");
                timeLeft=TIMER_IN_MILLISS;
                startTimer();
            }else{
                java.util.Date date = new java.util.Date(System.currentTimeMillis());
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
                String currentDateandTime = df.format(date);
                marksDataArrayList = dbHelper.browseGrades(user);

                boolean found = false;
                for(int i = 0; i < marksDataArrayList.size(); i++){
                  if(marksDataArrayList.get(i)[3].equals("1") && marksDataArrayList.get(i)[1].equals(subject)){
                      found = true;
                      break;
                  }else{
                      found = false;
                  }
                }

                if(found){
                    dbHelper.updateNewGrade(user, subject, totalQuestions - 1, score);
                }else{
                    dbHelper.updateNewGrade(user, subject, totalQuestions, score);
                }

                dbHelper.addRecentQuiz(user, subject, score, currentDateandTime); //adds to recent quiz database.

                finishQuiz();
            }
        }
        catch(Exception ex){
            Log.d("QUIZ APP","Error in selecting question "+ex.getMessage());

        }
    }

    private void startTimer(){
        countDownTimer=new CountDownTimer(timeLeft,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
             timeLeft=millisUntilFinished;
             updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeLeft=0;
                updateCountDownText();
                checkAnswer();
            }
        }.start();
    }
    private void  updateCountDownText(){
        int minutes=(int)(timeLeft/1000)/60;
        int seconds=(int)(timeLeft/1000)%60;
        String timeFormatter=String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        textViewTimer.setText(timeFormatter);
        if(timeLeft<10000){
            textViewTimer.setTextColor(Color.RED);
        }
        else{
            textViewTimer.setTextColor(textColorDefaultTimer);
        }
    }
    private void checkAnswer(){
        answered=true;
       countDownTimer.cancel();
        RadioButton radSelected=findViewById(radGroup.getCheckedRadioButtonId());
        int answerNo=radGroup.indexOfChild(radSelected)+1;
        if(answerNo==currentQuestion.getAnswerNo()){
            score++;
            textViewScore.setText("Score:"+score);
        }
        showResult();

    }
    private void showResult(){
        radOption1.setTextColor(Color.RED);
        radOption2.setTextColor(Color.RED);
        radOption3.setTextColor(Color.RED);
        radOption4.setTextColor(Color.RED);
        try {
            switch (currentQuestion.getAnswerNo()) {
                case 1:
                    radOption1.setTextColor(Color.GREEN);
                    textViewQuestion.setText("Answer 1 is Correct");
                    break;
                case 2:
                    radOption2.setTextColor(Color.GREEN);
                    textViewQuestion.setText("Answer 2 is Correct");
                    break;
                case 3:
                    radOption3.setTextColor(Color.GREEN);
                    textViewQuestion.setText("Answer 3 is Correct");
                    break;
                case 4:
                    radOption4.setTextColor(Color.GREEN);
                    textViewQuestion.setText("Answer 4 is Correct");
                    break;
            }
            if (currentQuestionCount < totalQuestions) {
                btnConfirm.setText("Next");
            } else {
                btnConfirm.setText("Finish");
            }
        }catch(Exception ex){
            Log.d("QUIZ APP","Error");
        }

    }
    private void finishQuiz(){

      //  finish();
      Intent resultIntent=new Intent();
      resultIntent.putExtra(HSCORE,score);
      setResult(RESULT_OK,resultIntent);

        finish();
    }

    @Override
    public void onBackPressed() {
        if(backPressedTime +2000>System.currentTimeMillis()){
            String subject = getIntent().getExtras().getString("SUBJECT");
            String user = getIntent().getExtras().getString("USERNAME");
            java.util.Date date = new java.util.Date(System.currentTimeMillis());
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            String currentDateandTime = df.format(date);
            dbHelper.updateNewGrade(user, subject, totalQuestions - 1, score);
            dbHelper.addRecentQuiz(user, subject + "\n(DID NOT FINISH)", score, currentDateandTime);
            finishQuiz();
        }else{
            Toast.makeText(this, "Press Back again to finish Quiz", Toast.LENGTH_SHORT).show();
        }
        backPressedTime=System.currentTimeMillis();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(countDownTimer!=null){
            countDownTimer.cancel();
        }
    }
}