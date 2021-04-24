package com.example.w21g12_quiz.fragments;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import com.example.w21g12_quiz.R;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;


public class LogOutFragment extends Fragment {
    Button btnLogout;
    GoogleSignInClient mGoogleSignInClient;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        btnLogout = view.findViewById(R.id.btnLogout);
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
                        .setCancelable(false)
                        .show();

                CountDownTimer countDownTimer = new CountDownTimer(1500, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                       // Intent logOut = new Intent(getActivity(), MainActivity.class);
                       // startActivity(logOut);
                        getActivity().finish();
                    }
                }.start();

            }

        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(getActivity())
                        .setTitle("Log Out?")
                        .setMessage("Are you sure you want to log out?")
                        .setPositiveButton("Yes", null)
                        .setNegativeButton("No", null)
                        .show();

                dialog.dismiss();
                AlertDialog dialogue = new AlertDialog.Builder(getActivity())
                        .setTitle("Logging out...")
                        .setMessage("Thank you for using quiz app! Logging out... ")
                        .setCancelable(false)
                        .show();

                CountDownTimer countDownTimer = new CountDownTimer(1500, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        // Intent logOut = new Intent(getActivity(), MainActivity.class);
                        // startActivity(logOut);
                        getActivity().finish();
                    }
                }.start();
            }
        });

        return view;
    }


}
