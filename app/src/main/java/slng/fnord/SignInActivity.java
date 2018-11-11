package slng.fnord;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class SignInActivity extends AppCompatActivity {
    private Button signIn2;
    String email;
    String password;
    public static User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        signIn2 = (Button) findViewById(R.id.signInButton);
        signIn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText emailText = (EditText) findViewById(R.id.signInEmail);
                EditText passwordText = (EditText) findViewById(R.id.signInPassword);

                Accounts acc = MainActivity.getAccounts();
                User user = null;

                password = passwordText.getText().toString();
                email = emailText.getText().toString();

                if (!Common.validateEmail(email)) {
                    Toast toastInvalidEmail = Toast.makeText(getApplicationContext(), "Email is invalid", Toast.LENGTH_SHORT);
                    toastInvalidEmail.show();
                    return;
                }

                if (!Common.validatePassword(password)) {
                    Toast toastInvalidPassword = Toast.makeText(getApplicationContext(), "Password is invalid", Toast.LENGTH_SHORT);
                    toastInvalidPassword.show();
                    return;
                }

                if (acc.existsAccount(email)) {
                    user = acc.getUser(email);
                    if (user.checkPassword(password)) {
                        currentUser = user;
                        openUserActivity(user.getType());
                    } else {
                        Toast toastWrongPassword = Toast.makeText(getApplicationContext(), "Incorrect password", Toast.LENGTH_SHORT);
                        toastWrongPassword.show();
                    }
                } else {
                    Toast toastNoSuchAccount = Toast.makeText(getApplicationContext(),
                            "No account with that email exists", Toast.LENGTH_SHORT);
                    toastNoSuchAccount.show();
                }
            }
        });
    }

    public void openUserActivity(UserTypes types) {
        Intent intent = null;
        switch (types) {
            case HOMEOWNER:
                intent = new Intent(this, WelcomeHomeOwner.class);
                break;
            case SERVICEPROVIDER:
                intent = new Intent(this, WelcomeServiceProvider.class);
                break;
            case ADMIN:
                intent = new Intent(this, WelcomeAdministrator.class);
                break;
        }
        startActivity(intent);
    }
}
