package slng.fnord.Activities.Shared;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import slng.fnord.Database.DBHelper;
import slng.fnord.Managers.AccountManager;
import slng.fnord.Helpers.Common;
import slng.fnord.R;
import slng.fnord.Structures.User;
import slng.fnord.Helpers.Enums.UserTypes;

public class SignInActivity extends AppCompatActivity {
    private Button signIn2;
    String email;
    String password;
    public static User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        AccountManager manager = new AccountManager(new DBHelper());

        signIn2 = findViewById(R.id.signInButton);
        signIn2.setOnClickListener(view -> {
            EditText emailText = findViewById(R.id.signInEmail);
            EditText passwordText = findViewById(R.id.signInPassword);

            password = passwordText.getText().toString();
            email = emailText.getText().toString();

            if (!Common.validateEmail(email)) {
                Toast.makeText(getApplicationContext(), "Email is invalid", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!Common.validatePassword(password)) {
                Toast.makeText(getApplicationContext(), "Password is invalid", Toast.LENGTH_SHORT).show();
                return;
            }

            manager.authenticateUser(email, password, this::signInCallback, this::makeToast);

        });
    }

    public void signInCallback(User user) {
        if (user == null) { // Sign in invalid/No such user
            return;
        }

        SignInActivity.currentUser = user;

        openUserActivity(user.getType());
    }

    public void openUserActivity(UserTypes types) {
        Intent intent = null;
        switch (types) {
            case HOMEOWNER:
                intent = new Intent(this, slng.fnord.Activities.HomeOwner.Welcome.class);
                break;
            case SERVICEPROVIDER:
                intent = new Intent(this, slng.fnord.Activities.ServiceProvider.Welcome.class);
                break;
            case ADMIN:
                intent = new Intent(this, slng.fnord.Activities.Admin.Welcome.class);
                break;
        }
        startActivity(intent);
    }

    public void makeToast(String text) {
        Toast.makeText(getApplicationContext(),  text, Toast.LENGTH_SHORT).show();
    }
}
