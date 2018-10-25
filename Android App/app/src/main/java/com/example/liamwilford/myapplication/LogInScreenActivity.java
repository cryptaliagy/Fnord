package com.example.liamwilford.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.regex.Pattern;

public class LogInScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_screen);
    }

    /**
     * Validate the username in Sign Up activity
     * Currently checks for empty and spaces
     *
     * @return the validity of the username (true if valid, false if invalid)
     */
    protected boolean validateUsername(){
        String pattern = "[^\s]*";
        TextView usernameView = (TextView) findViewById(R.id.logInUserNameEditText);
        String username = (String) usernameView.getText();

        return (Pattern.matches(pattern, username) && !username.equals(""));
    }

    /**
     * Valid the password in Sign Up activity
     * Currently checks for empty and spaces
     *
     * @return the validity of the username (true if valid, false if invalid)
     */
    protected boolean validatePassword(){
        String pattern = "[^\s]*";
        TextView passwordView = (TextView) findViewById(R.id.editText2);
        String password = (String) passwordView.getText();

        return (Pattern.matches(pattern, password) && !password.equals(""));
    }
    // TODO: Check if username actually exists in db
    // TODO: Check if password matches with password in db
}
