package com.example.w21g12_quiz;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.w21g12_quiz.adapters.QuizAdapter;

import java.util.List;

public class RecentQuizzesActivity extends AppCompatActivity {

    ListView listView;
    DBHelper DB;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recentquiz);

        listView = findViewById(R.id.listViewRecentQuizzes);

        DB = new DBHelper(this);
        String username = getIntent().getStringExtra("USERNAME");

        List<String[]> quizzes = DB.browseRecentGrades(username);
        QuizAdapter quizAdapter = new QuizAdapter(quizzes);
        listView.setAdapter(quizAdapter);
        quizAdapter.notifyDataSetChanged();

        button = findViewById(R.id.buttonExit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}

