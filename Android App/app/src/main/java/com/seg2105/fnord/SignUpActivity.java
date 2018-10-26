package com.seg2105.fnord;

import android.accounts.Account;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    private User.AccountType account = null;
    private FirebaseAuth mAuth;
    private boolean hasAdmin = FirebaseDatabase.getInstance().getReference("admin").child("created").equals(true);
    private DatabaseReference userDatabase;
    private static final String TAG = "EmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        userDatabase = FirebaseDatabase.getInstance().getReference("users");
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
            case R.id.homeOwnerRadioButton:
                if (checked){
                    account = User.AccountType.HOMEOWNER;
                    break;
                }
            case R.id.adminAccountButton:
                if (checked){
                    account = User.AccountType.ADMIN;
                    break;
                }
            case R.id.serviceProviderButton:
                if (checked){
                    account = User.AccountType.SERVICEPROVIDER;
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

    public void makeAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    FirebaseUser user;

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                        }
                        else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    public void signUp(View view){
        // validate inputs
        if (validateUsername() && validatePassword()){
            EditText passwordView = (EditText) findViewById(R.id.editText2);
            String password = (String) passwordView.getText().toString(); // password

            EditText usernameView = (EditText) findViewById(R.id.logInUserNameEditText);
            String username = (String) usernameView.getText().toString(); // username

            EditText emailView = (EditText) findViewById(R.id.logInEmail);
            String email = (String) emailView.getText().toString();

            if (validatePassword() && validateUsername()) {
                if (account.equals(User.AccountType.ADMIN)) {
                    adminWelcome(view);

                } else if (account.equals(User.AccountType.SERVICEPROVIDER)) {
                    serviceProviderWelcome(view);
                } else {
                    homeOwnerWelcome(view);
                }
            }
        }
    }
}
