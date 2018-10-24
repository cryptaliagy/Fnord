package com.uottawa.mylore.fnordprojectseg2105;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private EditText txtName, txtUsername, txtEmail;
    private EditText txtAddress, txtPassword, txtReEnterPassword;
    private Button btnRegister;
    private TextView signInLink;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setupUIViews();

        signInLink.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent signInIntent = new Intent(RegisterActivity.this, MainActivity.class);
                RegisterActivity.this.startActivity(signInIntent);
            }
        });
        firebaseAuth = FirebaseAuth.getInstance();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    //register to database
                    String user_email = txtEmail.getText().toString().trim();
                    String user_password = txtPassword.getText().toString().trim();
                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                            } else {
                                Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void setupUIViews() {
        txtName = (EditText) findViewById(R.id.txtName);
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtAddress = (EditText) findViewById(R.id.txtAddress);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtReEnterPassword = (EditText) findViewById(R.id.txtReEnterPassword);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        signInLink = (TextView) findViewById(R.id.tvLinkSignIn);

    }

    private Boolean validate() {
        Boolean result = false;
        String name = txtName.getText().toString();
        String password = txtPassword.getText().toString();
        String email = txtEmail.getText().toString();

        if (name.isEmpty() || password.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Please enter all info", Toast.LENGTH_SHORT).show();
        } else {
            result = true;
        }
        return result;
    }
}
