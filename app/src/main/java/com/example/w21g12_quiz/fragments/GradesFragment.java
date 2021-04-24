package com.example.w21g12_quiz.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.w21g12_quiz.R;
import com.example.w21g12_quiz.RecentQuizzesActivity;

public class GradesFragment extends Fragment {

    BarChartFragment barChartFragment;
    PieChartFragment pieChartFragment;
    HBarChartFragment HBarChartFragment;
    Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grades, container, false);

        ImageView info = view.findViewById(R.id.imageViewInfo);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "If there is no data shown on the graph, then you have not done any quizzes.", Toast.LENGTH_LONG).show();
            }
        });

        bundle = new Bundle();
        barChartFragment = new BarChartFragment();
        pieChartFragment = new PieChartFragment();
        HBarChartFragment = new HBarChartFragment();


        String usernameS = getArguments().getString("USERNAME");
        bundle.putString("USERNAME", usernameS);
        barChartFragment.setArguments(bundle);
        HBarChartFragment.setArguments(bundle);
        pieChartFragment.setArguments(bundle);

        Spinner spinner = view.findViewById(R.id.spinnerChooseChart);

        ImageView recentImg = view.findViewById(R.id.imageViewRecentQuizzes);

        recentImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RecentQuizzesActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("USERNAME", usernameS);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        selectedFragment(barChartFragment);
                        break;
                    case 1:
                        selectedFragment(HBarChartFragment);
                        break;
                    case 2:
                        selectedFragment(pieChartFragment);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        return view;
    }

    private void selectedFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentCharts, fragment);
        fragmentTransaction.commit();
    }

}