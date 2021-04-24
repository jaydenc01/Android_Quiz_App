package com.example.w21g12_quiz.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.w21g12_quiz.R;

public class ContactUsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contactus, container, false);
        Button buttonSendEmailA = view.findViewById(R.id.buttonSendEmailAman);
        Button buttonSendEmailR = view.findViewById(R.id.buttonSendEmailRishab);
        Button buttonSendEmailJ = view.findViewById(R.id.buttonSendEmailJayden);

        buttonSendEmailA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "kaura126@student.douglascollege.ca", null));
                startActivity(intent);
            }
        });

        buttonSendEmailR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "sharmar27@student.douglascollege.ca", null));
                startActivity(intent);
            }
        });

        buttonSendEmailJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "cangj@student.douglascollege.ca", null));
                startActivity(intent);
            }
        });


        return view;
    }
}