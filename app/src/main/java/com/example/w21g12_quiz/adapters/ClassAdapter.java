package com.example.w21g12_quiz.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



import com.example.w21g12_quiz.R;

import java.util.List;

public class ClassAdapter extends BaseAdapter {

    List<String[]> classList;

    public ClassAdapter(List<String[]> classList)

    {
        this.classList = classList;
    }

    @Override
    public int getCount()
    {
        return classList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return classList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        if(convertView == null)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            convertView = layoutInflater.inflate(R.layout.layout_classitem, parent, false);
        }

        TextView classnames = convertView.findViewById(R.id.textViewQuizClassHeader);
        classnames.setText(classList.get(position)[1]);

        TextView welcomeMessage = convertView.findViewById(R.id.textViewChooseClassWelcome);

        return convertView;
    }
}