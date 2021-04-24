package com.example.w21g12_quiz.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.w21g12_quiz.DBHelper;
import com.example.w21g12_quiz.MainActivity;
import com.example.w21g12_quiz.QuizStartingActivity;
import com.example.w21g12_quiz.R;
import com.example.w21g12_quiz.RecentQuizzesActivity;
import com.example.w21g12_quiz.adapters.ClassAdapter;

import java.util.List;

public class ClassFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_classes, container, false);
        ListView listViewClasses = (ListView) view.findViewById(R.id.listViewChooseClass);
        DBHelper DB = new DBHelper(getActivity());

        TextView welcomeMessage = (TextView) view.findViewById(R.id.textViewChooseClassWelcome);
        String usernameS = getArguments().getString("USERNAME");
        welcomeMessage.setText("You are logged in as " + usernameS);

        List<String[]> grades = DB.browseGrades(usernameS);
        ClassAdapter classAdapter = new ClassAdapter(grades);
        listViewClasses.setAdapter(classAdapter);
        classAdapter.notifyDataSetChanged();

        listViewClasses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(getActivity(), QuizStartingActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("SUBJECT", DB.browseGrades(usernameS).get(position)[1]);
                bundle.putString("USERNAME", usernameS);

                myIntent.putExtras(bundle);

                startActivity(myIntent);
            }
        });

        return view;
    }

    public void LogOut(){
        if(getActivity()!= null) {
            AlertDialog dialog = new AlertDialog.Builder(getActivity())
                    .setTitle("Log Out?")
                    .setMessage("Are you sure you want to log out?")
                    .setPositiveButton("Yes", null)
                    .setNegativeButton("No", null)
                    .show();
            Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    AlertDialog dialog = new AlertDialog.Builder(getActivity())
                            .setTitle("Logging out...")
                            .setMessage("Thank you for using quiz app! Logging out... ")
                            .show();

                    CountDownTimer countDownTimer = new CountDownTimer(1500, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {

                        }

                        @Override
                        public void onFinish() {
                            Intent logOut = new Intent(getActivity(), MainActivity.class);
                            startActivity(logOut);
                        }
                    }.start();

                }
            });
        }
    }




}