package com.seg2105.fnord;

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
        String pattern = "[^\\s]*";
        TextView usernameView = (TextView) findViewById(R.id.logInUserNameEditText);
        String username = (String) usernameView.getText();

        return (!username.equals("") && Pattern.matches(pattern, username));
    }

    /**
     * Validate the password in Sign Up activity
     * Currently checks for empty and spaces
     *
     * @return the validity of the password (true if valid, false if invalid)
     */
    protected boolean validatePassword(){
        String pattern = "[^\\s]*";
        TextView passwordView = (TextView) findViewById(R.id.editText2);
        String password = (String) passwordView.getText();

        return ( !password.equals("") && Pattern.matches(pattern, password) );
    }
    // TODO: Check if username actually exists in db
    // TODO: Check if password matches with password in db

    /**
     * Validate email
     * Currently checks for empty and email of form [name]@[site].[ending]
     *
     * @return the validity of the email (true if valid, false if invalid)
     */
    protected boolean validateEmail(){
        //TODO: add email input field
        String pattern = "[^\\@\\s]+\\@[^\\.^\\s]+\\.[\\S]+";
        TextView emailView = (TextView) findViewById(R.id.editText2); //TODO: use email field
        String email = (String) emailView.getText();

        return ( !email.equals("") && Pattern.matches(pattern, email) );
    }


}
