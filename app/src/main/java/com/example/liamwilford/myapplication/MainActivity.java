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
    public void send (View view){
        //do something in response to button
        Intent intent = new Intent(this,LogInScreen.class);
        intent.putExtra (EXTRA_MESSAGE, "No account has been created, please create an admin account");
        startActivity(intent);
    }
}
