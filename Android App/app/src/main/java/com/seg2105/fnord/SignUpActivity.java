package com.example.liamwilford.myapplication;

import android.accounts.Account;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    private enum AccountType { HOMEOWNER, ADMIN, SERIVCEPROVIDER}
    private AccountType account = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    /**
     * Validate the username in Sign Up activity
     * Currently checks for empty and spaces
     *
     * @return the validity of the username (true if valid, false if invalid)
     */
    protected boolean validateUsername(){
        String pattern = "[^\\s]*";
        EditText usernameView = (EditText) findViewById(R.id.logInUserNameEditText);
        String username = (String) usernameView.getText().toString();

        return ( !username.equals("") && Pattern.matches(pattern, username) );
    }

    /**
     * Validate the password in Sign Up activity
     * Currently checks for empty and spaces
     *
     * @return the validity of the password (true if valid, false if invalid)
     */
    protected boolean validatePassword(){
        String pattern = "[^\\s]*";
        EditText passwordView = (EditText) findViewById(R.id.editText2);
        String password = (String) passwordView.getText().toString();

        return ( !password.equals("") && Pattern.matches(pattern, password));
    }
    //TODO: Check if username already exists in db

    /**
     * Validate email
     * Currently checks for empty and email of form [name]@[site].[ending]
     *
     * @return the validity of the email (true if valid, false if invalid)
     */
    protected boolean validateEmail(){
        //TODO: add email input field
        String pattern = "[^\\@\\s]+\\@[^\\.^\\s]+\\.[\\S]+";
        EditText emailView = (EditText) findViewById(R.id.editText2); //TODO: use email field
        String email = (String) emailView.getText().toString();

        return ( !email.equals("") && Pattern.matches(pattern, email) );
    }

    public void accountButton(View view){
        // android:onClick="accountButton"
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.homeOwnerAccountButton:
                if (checked){
                    account = AccountType.HOMEOWNER;
                    break;
                }
            case R.id.adminAccountButton:
                if (checked){
                    account = AccountType.ADMIN;
                    break;
                }
            case R.id.serviceProviderAccountButton:
                if (checked){
                    account = AccountType.SERIVCEPROVIDER;
                    break;
                }
        }

    }

    public void homeOwnerWelcome(View view){
        Intent intent = new Intent(this, WelcomeScreenHomeOwnerActivity.class);
        startActivity(intent);


    }
    public void adminWelcome (View view){
        Intent intent = new Intent(this, WelcomeScreenAdministratorActivity.class);
        startActivity(intent);
    }

    public void serviceProviderWelcome(View view){
        Intent intent = new Intent(this, WelcomeScreenServiceProviderActivity.class);
        startActivity(intent);
    }

    public void signUp(View view){
        // validate inputs
        if (validateUsername() && validatePassword()){
            if (account.equals(AccountType.HOMEOWNER)){
                homeOwnerWelcome(view);
            } else if (account.equals(AccountType.ADMIN)){
                adminWelcome(view);
            } else if (account.equals(AccountType.SERIVCEPROVIDER)){
                serviceProviderWelcome(view);
            }
        }
    }

}
