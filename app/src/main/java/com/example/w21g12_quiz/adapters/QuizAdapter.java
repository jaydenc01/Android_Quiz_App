package com.example.w21g12_quiz.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.w21g12_quiz.R;

import java.util.List;

public class QuizAdapter extends BaseAdapter {

    List<String[]> quizList;

    public QuizAdapter(List<String[]> quizList)
    {
        this.quizList = quizList;
    }

    @Override
    public int getCount() {
        return quizList.size();
    }

    @Override
    public Object getItem(int position) {
        return quizList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            convertView = layoutInflater.inflate(R.layout.layout_quizitem, parent, false);
        }

        TextView classnames = convertView.findViewById(R.id.textViewQuizClassHeader);
        TextView quizmark = convertView.findViewById(R.id.textViewQuizScoreHeader);
        TextView date = convertView.findViewById(R.id.textViewQuizDateHeader);
        classnames.setText(quizList.get(position)[0]);
        quizmark.setText(quizList.get(position)[1]);
        date.setText(quizList.get(position)[2]);

        TextView welcomeMessage = convertView.findViewById(R.id.textViewChooseClassWelcome);

        return convertView;
    }
}