package com.example.w21g12_quiz;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class MainActivity extends AppCompatActivity {
    TextInputEditText username,password,repassword;
    Button register,signin;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_W21G12_Quiz);
        setContentView(R.layout.activity_main);
        // to show full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        username=findViewById(R.id.editTxtName);
        password=findViewById(R.id.editTxtPasswrd);
        repassword=findViewById(R.id.editTxtRetypePasswrd);
        register=findViewById(R.id.BtnRegister);
        signin=findViewById(R.id.BtnSignIn);

        DB=new DBHelper(this);



        register.setOnClickListener((View v)-> {
            String user = username.getText().toString();
            String pass = password.getText().toString();
            String repass = repassword.getText().toString();
            boolean val = validateUsername(user);
            if (user.equals("") || pass.equals("") || repass.equals("")) {
                Toast.makeText(MainActivity.this, "Please enter all input fields", Toast.LENGTH_SHORT).show();

            }


            else {
                if (val) {
                    if (validatePassword(pass)) {
                        if (pass.equals(repass)) {
                            Boolean checkuser = DB.checkusername((user));
                            if (checkuser == false) {
                                Boolean insert = DB.insertData(user, pass);
                                if (insert == true) {
                                    ;
                                    Toast.makeText(this, "Registered sucessfully..." +
                                            "", Toast.LENGTH_SHORT).show();

                                    //here to add new classes for new users, or else class list will be blank
                                    DB.updateGrade(user, "Java Programming", 0, 1);
                                    DB.updateGrade(user, "Software Engineering", 0, 1);
                                    DB.updateGrade(user, "Python", 0, 1);
                                    DB.updateGrade(user, "HTML & CSS", 0, 1);

                                    Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);
                                } else {

                                    Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show();
                                }
                            } else {

                                Toast.makeText(this, "User already exists", Toast.LENGTH_SHORT).show();
                            }
                        } else {

                            Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Password is too weak! \n Password must contain 1 special character, no space, atleast 6 digits and atleast 1 number.", Toast.LENGTH_SHORT).show();
                    }
                }
            }


        });
        signin.setOnClickListener((View v) ->{
            Intent myIntent=new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(myIntent);

        });
    }

    private Boolean validatePassword(String pass) {
        String val = pass;
        String passwordVal = "^" +
                "(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{6,}" +               //at least 6 characters
                "$";

        if (!val.matches(passwordVal)) {
            return false;
        } else {
            return true;
        }
    }

    private Boolean validateUsername(String user) {
        String val = user;
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (val.length() >= 15) {
            Toast.makeText(this, "Username too long", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!val.matches(noWhiteSpace)) {
            Toast.makeText(this, "White Spaces are not allowed", Toast.LENGTH_SHORT).show();

            return false;
        } else {
            return true;
        }
    }


}