package com.example.w21g12_quiz.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.w21g12_quiz.DBHelper;
import com.example.w21g12_quiz.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class
BarChartFragment extends Fragment {

    BarChart barChart;
    ArrayList<BarEntry> barEntryArrayList;
    ArrayList<String> LabelNames;
    List<String[]> marksDataArrayList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_charts, container, false);
        barChart = view.findViewById(R.id.barChart);
        DBHelper dbHelper = new DBHelper(getActivity());
        String username = getArguments().getString("USERNAME");


        //this is to clear the old chart, to avoid duplicates.
        marksDataArrayList.clear();
        barChart.invalidate();
        barChart.clear();


        barEntryArrayList = new ArrayList<>();
        LabelNames = new ArrayList<>();
        marksDataArrayList = dbHelper.browseGrades(username);

        for(int i = 0; i < marksDataArrayList.size(); i++){
            String className = marksDataArrayList.get(i)[1]; //grabs the labels from the String array in database.
            LabelNames.add(className);
        }

        //grabs the percent from the gradePercent method and puts in bar graph.
        barEntryArrayList.add(new BarEntry(0, (float) dbHelper.gradePercent(username, "HTML & CSS")));
        barEntryArrayList.add(new BarEntry(1, (float) dbHelper.gradePercent(username, "Java Programming")));
        barEntryArrayList.add(new BarEntry(2, (float) dbHelper.gradePercent(username, "Python")));
        barEntryArrayList.add(new BarEntry(3, (float) dbHelper.gradePercent(username, "Software Engineering")));

        BarDataSet barDataSet = new BarDataSet(barEntryArrayList, "Performance");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextSize(12f);
        barChart.getDescription().setEnabled(false);
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barChart.setTouchEnabled(false);
        barChart.setPinchZoom(false);
        barChart.setDoubleTapToZoomEnabled(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(LabelNames));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(LabelNames.size());
        barChart.getLegend().setEnabled(false);
        barChart.setExtraBottomOffset(15);

        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setAxisMinimum(0);
        yAxis.setAxisMaximum(100);
        barChart.getAxisRight().setEnabled(false);


        barChart.animateY(500);
        barChart.invalidate();

        return view;
    }

}

