package com.example.w21g12_quiz.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.w21g12_quiz.DBHelper;
import com.example.w21g12_quiz.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class PieChartFragment extends Fragment {

    PieChart pieChart;
    List<String[]> marksDataArrayList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_piechart, container, false);
        DBHelper dbHelper = new DBHelper(getActivity());
        String username = getArguments().getString("USERNAME");
        pieChart = (PieChart) view.findViewById(R.id.pieChart);

        marksDataArrayList.clear();
        pieChart.clear();

        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5,5);
        pieChart.setDragDecelerationFrictionCoef(0.85f);
        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setTouchEnabled(true);
        pieChart.getLegend().setEnabled(false);
        pieChart.animateY(1000, Easing.EaseInOutCubic);


        marksDataArrayList = dbHelper.browseGrades(username);

        ArrayList<PieEntry> pieEntryArrayList = new ArrayList<>();

        //grabs the percent from the gradePercent method and puts in pie chart.
        pieEntryArrayList.add(new PieEntry((float) dbHelper.gradePercent(username, "HTML & CSS"), "HTML & CSS"));
        pieEntryArrayList.add(new PieEntry((float) dbHelper.gradePercent(username, "Java Programming"), "Java Programming"));
        pieEntryArrayList.add(new PieEntry((float) dbHelper.gradePercent(username, "Python"), "Python"));
        pieEntryArrayList.add(new PieEntry((float) dbHelper.gradePercent(username, "Software Engineering"), "Software Engineering"));

        PieDataSet dataSet = new PieDataSet(pieEntryArrayList, "Performance");
        dataSet.setSliceSpace(1f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData data = new PieData((dataSet));
        pieChart.invalidate();
        data.setValueTextSize(15f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);

        return view;
    }
}
