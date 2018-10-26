package com.seg2105.fnord;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

public class LogInScreenActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference userDatabase;
    private static final String TAG = "EmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        userDatabase = FirebaseDatabase.getInstance().getReference("users");
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


    protected void onClick(View view) {
        EditText emailText = (EditText) findViewById(R.id.logInUserNameEditText);
        String email = (String) emailText.getText().toString();

        EditText passwordText = (EditText) findViewById(R.id.logInPassWordEditText);
        String password = (String) passwordText.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete( Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LogInScreenActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        FirebaseUser user = mAuth.getCurrentUser();

        if (user == null) {
            return;
        }

        String id = user.getUid();
        User.AccountType type = User.AccountType.valueOf(userDatabase.child(id).child("type").toString());

        if (type.equals(User.AccountType.ADMIN)) {
            adminWelcome(view);
        } else if (type.equals(User.AccountType.SERVICEPROVIDER)) {
            serviceProviderWelcome(view);
        } else {
            homeOwnerWelcome(view);
        }
    }


}
