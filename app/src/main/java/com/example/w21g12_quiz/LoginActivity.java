package com.example.w21g12_quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity{
    TextInputEditText username,password;
    Button btnlogin;
    DBHelper DB;
    SignInButton googleSignInBtn;
    GoogleSignInClient googleSignInClient;
    int SIGN_IN=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username=findViewById(R.id.signInUserName);
        password=findViewById(R.id.signInPasswrd);
        btnlogin=findViewById(R.id.SignInBtn);
        googleSignInBtn = findViewById(R.id.googleSign_in);

        DB=new DBHelper(this);
        readQuestionData();

        GoogleSignInOptions googleSignIn = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignIn);

        googleSignInBtn.setOnClickListener((View view) ->{
            switch (view.getId()) {
                case R.id.googleSign_in:
                    signIn();
                    break;
            }
        });

        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        if(sharedPreferences.contains("USERNAME")){
            String user=sharedPreferences.getString("USERNAME","");
            username.setText(user);
        }
        btnlogin.setOnClickListener((View v)-> {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("USERNAME",username.getText().toString());
        editor.commit();

            String user=username.getText().toString();
            String pass=password.getText().toString();
            if(user.equals("")||pass.equals(""))
                Toast.makeText(this, "Please fill all input fields", Toast.LENGTH_SHORT).show();
            else {
                Boolean checkuserpass = DB.checkusernamepassword(user, pass);
                if (checkuserpass == true) {
                    Toast.makeText(this, "User Sign In sucessfully", Toast.LENGTH_SHORT).show();
                    Intent myIntent = new Intent(this, ChooseClassActivity.class);
                    myIntent.putExtra("USERNAME", user);
                    startActivity(myIntent);
                    finish();
                } else {
                    Toast.makeText(this, "Invalid User name and password", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }


    private void readQuestionData() {
        InputStream is = getResources().openRawResource(R.raw.question);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );
        String line="";
        if(DB.dataExist()) {
            try {
                while ((line = reader.readLine()) != null) {

                    Log.d("Quiz app", "Just Created :" + line);
                    String[] token = line.split(",");

                    DB.addQuestion(token[0], token[1], token[2], token[3], token[4], Integer.parseInt(token[5]),token[6]);

                }
            } catch (IOException e) {
                Log.wtf("Quiz APP", "Error reading data file on line " + line, e);
                e.printStackTrace();
            }
        }
    }

    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, SIGN_IN);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            AccountManager am = AccountManager.get(this);
            Account[] accounts = am.getAccountsByType("com.google");
            String user = acct.getDisplayName();
            for(int i = 0; i<accounts.length; i++)
            {
                if(Arrays.asList(accounts).contains(user) ) {
                    Intent intent = new Intent(LoginActivity.this,ChooseClassActivity.class);
                    intent.putExtra("USERNAME", user);
                    startActivity(intent);
                    finish(); //java programming, software engineering, python coding, html and css
                }
                else {
                    DB.updateGrade(user, "Java Programming", 0, 1);
                    DB.updateGrade(user, "Software Engineering", 0, 1);
                    DB.updateGrade(user, "Python", 0, 1);
                    DB.updateGrade(user, "HTML & CSS", 0, 1);

                    Intent intent = new Intent(LoginActivity.this, ChooseClassActivity.class);
                    intent.putExtra("USERNAME", user);
                    startActivity(intent);
                    finish();

                }


            }

        } catch (ApiException e) {

            Log.w("Error" ,"signInResult:failed code=" + e.getStatusCode());

        }
    }
}