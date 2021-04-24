package com.example.w21g12_quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.w21g12_quiz.fragments.ClassFragment;
import com.example.w21g12_quiz.fragments.ContactUsFragment;
import com.example.w21g12_quiz.fragments.GradesFragment;
import com.example.w21g12_quiz.fragments.AboutUsFragment;
import com.example.w21g12_quiz.fragments.LogOutFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ChooseClassActivity extends AppCompatActivity {
    final String TAG = "Quiz App....!";

    Bundle bundle = new Bundle();
    ClassFragment fragment = new ClassFragment();
    GradesFragment gradesFragment = new GradesFragment();
    AboutUsFragment aboutFragment = new AboutUsFragment();
    int startingPosition = 0;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classbar);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        bottomNavigationView.getMenu().findItem(bottomNavigationView.getSelectedItemId());

            String username = getIntent().getStringExtra("USERNAME");
            bundle.putString("USERNAME", username);
            fragment.setArguments(bundle);
            gradesFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    int newPosition = 0;

                    switch (item.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = fragment;
                            newPosition = 1;
                            break;
                        case R.id.nav_performance:
                            selectedFragment = gradesFragment;
                            String username = getIntent().getStringExtra("USERNAME");
                            bundle.putString("USERNAME", username);
                            gradesFragment.setArguments(bundle);
                            newPosition = 2;
                            break;
                        case R.id.nav_aboutus:
                            selectedFragment = aboutFragment;
                            String name = getIntent().getStringExtra("USERNAME");
                            bundle.putString("USERNAME", name);
                            aboutFragment.setArguments(bundle);
                            newPosition = 3;
                            break;
                        case R.id.nav_contactus:
                            selectedFragment = new ContactUsFragment();
                            newPosition = 4;
                            break;
                        case R.id.nav_logout:
                            selectedFragment = new LogOutFragment();
                            newPosition = 5;
                            break;

                    }

                    return loadFragment(selectedFragment, newPosition);
                }
            };

    private boolean loadFragment(Fragment fragment, int newPosition) { //this method takes the position of the bottom nav bar, and compares it to the fragment position to figure out which way to fade in from.
        if(fragment != null) {
            if(startingPosition > newPosition) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                transaction.replace(R.id.fragment_container, fragment);
                transaction.commit();
            }
            if(startingPosition < newPosition) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
                transaction.replace(R.id.fragment_container, fragment);
                transaction.commit();
            }
            startingPosition = newPosition;
            return true;
        }

        return false;
    }


    @Override
    public void onBackPressed(){
        AlertDialog dialog = new AlertDialog.Builder(this)
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
                AlertDialog dialog = new AlertDialog.Builder(ChooseClassActivity.this)
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
                        ChooseClassActivity.this.finish();
                    }
                }.start();

            }
        });
    }

}