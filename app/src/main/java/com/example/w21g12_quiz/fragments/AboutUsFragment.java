package com.example.w21g12_quiz.fragments;

import android.app.AlertDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.w21g12_quiz.DBHelper;
import com.example.w21g12_quiz.R;

import java.util.ArrayList;
import java.util.List;

public class AboutUsFragment extends Fragment {


    EditText editTextReview;
    Button btnSubmit;
    RatingBar ratingBar;
    float rateValue;
    String ratings;
    int length=0;
    DBHelper DB;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        String user = getArguments().getString("USERNAME");

        editTextReview = view.findViewById(R.id.editTextTextReview);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        ratingBar = view.findViewById(R.id.ratingBarRate);
        DB = new DBHelper(getActivity());

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rateValue = ratingBar.getRating();
                if(rateValue<=1 && rateValue>=0)
                    ratings = "Worst!! " + rateValue + "/5";
                else if(rateValue<=2 && rateValue>1)
                    ratings = "Bad! " + rateValue + "/5";
                else if(rateValue<=3 && rateValue>2)
                    ratings = "Ok! " + rateValue + "/5";
                else if(rateValue<=4 && rateValue>3)
                    ratings = "Good! " + rateValue + "/5";
                else if(rateValue<=5 && rateValue>4)
                    ratings = "Excellent!! " + rateValue + "/5";

                length = ratings.length();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Text = ratings+" \n"+ editTextReview.getText()+" \nBY: "+ user;
                DB.insertReview(Text);
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(getActivity());
                dlgAlert.setMessage(Text);
                dlgAlert.setTitle("Thank You for Rating us!");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();

                editTextReview.setText("");
                ratingBar.setRating(0);
            }
        });

        return view;
    }
}
