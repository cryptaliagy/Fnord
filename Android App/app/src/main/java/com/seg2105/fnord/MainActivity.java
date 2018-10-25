package com.example.liamwilford.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.liamwilford.myapplication.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
        /** called when user taps log in button */
    public void loginScreen (View view){
        //do something in response to button
        Intent intent = new Intent(this,LogInScreenActivity.class);
        startActivity(intent);
    }
    public void signUpScreen (View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);

    }
}
