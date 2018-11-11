package slng.fnord;

import android.accounts.Account;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Pattern;

import static slng.fnord.UserTypes.*;

public class RegisterActivity extends AppCompatActivity {
    private Button register2;
    String email;
    String username;
    String password;
    String accountType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //creating a spinner/dropdown list for the register screen
        final Spinner accountSpinner = (Spinner) findViewById(R.id.accountTypeSpinner);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(RegisterActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.accountTypes));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountSpinner.setAdapter(myAdapter);

        //making register button on register screen work/add stuff to account Accounts arraylists
        //it shall open the appropriate welcome screen, as well as add the account to the list
        register2 = (Button) findViewById(R.id.registerButton);
        register2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText emailText = (EditText) findViewById(R.id.registerEmail);
                email = emailText.getText().toString();

                EditText passwordText = (EditText) findViewById(R.id.registerPassword);
                password = passwordText.getText().toString();

                EditText usernameText = (EditText) findViewById(R.id.registerUsername);
                username = usernameText.getText().toString();

                accountType = accountSpinner.getSelectedItem().toString();
                Accounts acc = MainActivity.getAccounts();

                Toast toast = null;

                if (!Common.validateEmail(email)) {
                    toast = Toast.makeText(getApplicationContext(), "Email is invalid", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                if (!Common.validatePassword(password)) {
                    toast = Toast.makeText(getApplicationContext(), "Password is invalid", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                if (!Common.validateUser(username)) {
                    toast = Toast.makeText(getApplicationContext(), "Username is invalid", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                if (acc.existsAccount(email)) {
                    toast = Toast.makeText(getApplicationContext(), "An account with this email already exists", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                UserTypes type = null;

                if (accountType.equals("HomeOwner")) {
                    type = HOMEOWNER;
                } else if (accountType.equals("ServiceProvider")) {
                    type = SERVICEPROVIDER;
                } else if (accountType.equals("Administrator")) {
                    if (!acc.existsAdmin()) {
                        type = ADMIN;
                    }
                    else {
                        toast = Toast.makeText(getApplicationContext(), "An administrator account already exists", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }
                }


                acc.makeUser(email, username, password, type);
                SignInActivity.currentUser = acc.getUser(email);
                toast = Toast.makeText(getApplicationContext(), "New account has been made", Toast.LENGTH_SHORT);
                toast.show();
                openUserActivity(type);
            }
        });
    }

    //opens welcome screen for the user
    public void openUserActivity(UserTypes type) {
        Intent intent = null;
        switch (type) {
            case ADMIN:
                intent = new Intent(this, WelcomeAdministrator.class);
                break;
            case HOMEOWNER:
                intent = new Intent(this, WelcomeHomeOwner.class);
                break;
            case SERVICEPROVIDER:
                intent = new Intent(this, WelcomeServiceProvider.class);
                break;
        }
        startActivity(intent);
    }


}
